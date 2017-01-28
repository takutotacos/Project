<?php
  $now = date('Y-m-d H:i:s', time());
  $is_active = 1;
  try {
    $pdo = new PDO('mysql:host=localhost;dbname=project;charset=utf8', 'root', 'root',[
      PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
      PDO::ATTR_EMULATE_PREPARES => false,
    ]);
  } catch(PDOException $e) {
    exit('Failed to connect to db.' .$e->getMessage());
  }

  $post = json_decode(file_get_contents("php://input"),true);
  $img_info = $post['imgInfo'];
  $user_id = $post['userId'];
  $comment = $post['comment'];
  $latitude = $post['post'];
  $longitude = $post['longitude'];
  $st = $pdo->prepare('INSERT INTO posting_trn (img_info,user_id,comment,latitude,
  	longitude,create_date,update_date,active_flg)
    VALUES(:img_info,:user_id,:comment,:latitude,:longitude,:create_date,:update_date,:active_flg)');
  $st->bindParam(':img_info',$img_info);
  $st->bindParam(':user_id',$user_id);
  $st->bindParam(':comment',$comment);
  $st->bindParam(':latitude',$latitude);
  $st->bindParam(':longitude',$longitude);
  $st->bindParam(':create_date',$now);
  $st->bindParam(':update_date',$now);
  $st->bindParam(':active_flg',$is_active);
  $st->execute();
?>
