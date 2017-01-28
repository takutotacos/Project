<?php
  try {
    $pdo = new PDO('mysql:host=localhost;dbname=project;charset=utf8', 'root', 'root',[
      PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
      PDO::ATTR_EMULATE_PREPARES => false,
    ]);
  } catch(PDOException $e) {
    exit('Failed to connect to db.' .$e->getMessage());
  }

  // decoding the json array
  // research if the second argument for json_decode is needed.
  $post = json_decode(file_get_contents("php://input"),true);
  $email = $post['email'];
  $password = $post['password'];
  $stmt=$pdo->prepare("SELECT * from user_mst where email = ?");
  $stmt->execute([$email]);
  $count=$stmt->rowCount();
  if($count == 1) {
    error_log("ONE Data found!");
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if(password_verify($password, $row['password'])) {
      error_log("The password matched");
      $response = array();
      $response['status'] = "OK";
      $response['userId'] = $row['user_id'];
      echo json_encode($response);
    } else {
      error_log("The password did not match");
    }
  } else {
    error_log("No data found!");
  }
 ?>
