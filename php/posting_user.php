<?php
  $dsn = 'mysql:dbname=project;host=localhost;charset=utf8';

try{
  $pdo = new PDO($dsn, 'root','root',[
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
      PDO::ATTR_EMULATE_PREPARES => false,
    ]);
  } catch(PDOException $e) {
    exit('Failed to connect to db.'.$e->getMessage());
  }

  $post = json_decode(file_get_contents("php://input"),true);
  $userId = $post['userId'];
  $email = $post['email'];
  $password = $post['password'];
  $hash = password_hash($password, PASSWORD_DEFAULT);

  $st = $pdo->prepare('INSERT INTO user_mst (user_id,email,password) VALUES(:user_id,:email,:password)');
  $st->bindParam(':user_id',$userId);
  $st->bindParam(':email',$email);
  $st->bindParam(':password',$hash);
  $st->execute();

  $row = $st->fetch(PDO::FETCH_ASSOC);
   $response = array();
   $response['type'] = "regist";
   $response['status'] = "OK";
   echo json_encode($response);

?>
