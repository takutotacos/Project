<?php
  try {
    $pdo = new PDO('mysql:host=localhost;dbname=project;charset=utf8', 'root', 'root',[
      PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
      PDO::ATTR_EMULATE_PREPARES => false,
    ]);
  } catch(PDOException $e) {
    exit('Failed to connect to db.' .$e->getMessage());
  }
  $stmt=$pdo->query('SELECT * FROM POSTING_TRN');
  $stmt->execute();
  $response = array();
  while($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
    error_log("There are some data found");
    $response[] =array(
      'user_id'=>$row['user_id'],
      'create_date'=>$row['create_date'],
      'latitude'=>$row['latitude'],
      'longitude'=>$row['longitude'],
      'comment'=>$row['comment'],
      'img_info'=>base64_encode($row['img_info'])
    );
  }
  header('Content-type:image/jpg');
  error_log("The data volume are ". count($response));
  $json_response = json_encode($response);
  echo $json_response;
?>
