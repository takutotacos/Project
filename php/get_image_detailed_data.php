<?php
  try {
    $pdo = new PDO('mysql:host=localhost;dbname=project;charset=utf8', 'root', 'root',[
      PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
      PDO::ATTR_EMULATE_PREPARES => false,
    ]);
  } catch(PDOException $e) {
    exit('Failed to connect to db.' .$e->getMessage());
  }
    $post = json_decode(file_get_contents("php://input"),true);
    $create_date = $post['createDate'];
    $user_id = $post['userId'];
    $stmt=$pdo->prepare("SELECT * from posting_trn where create_date = ? and user_id = ?");
    $stmt->execute([$create_date, $user_id]);
    $response = array();
    $count=$stmt->rowCount();
    if($count > 0) {
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      error_log("Success: The image obtained");
      $response['status'] = "OK";
      $response['userId'] = $row['user_id'];
      $response['createDate'] = $row['create_date'];
      $response['comment'] = $row['comment'];
      $response['imgInfo'] = base64_encode($row['img_info']);
    } else {
      error_log("Failure: The image NOT FOUND");
      $response['status'] = "NG";
  }
  echo json_encode($response);
?>
