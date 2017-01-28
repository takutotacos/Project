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
  $search_key = $_GET['searchKey'];
  $search_value = $_GET['searchValue'];
  $stmt = $pdo -> prepare('SELECT count(*) FROM USER_MST WHERE ' .$search_key .' = ?');
  $stmt->execute([$search_value]);
  $count=$stmt->fetchColumn();
  error_log("The number of data are: " .$count);
  echo $count;
?>
