<?php
  $host = "localhost";
  $user = "root";
  $password = "root";
  $schema = "project";

  // in the 6th argument, specify the path to mysql.sock. you will get an error like no such file or directory.
  $conn = new mysqli($host, $user, $password, $schema, 3306, "/private/tmp/mysql.sock");
 if(mysqli_connect_errno($conn)) {
    error_log("failed to connect to MySQL:" . mysqli_connect_error());
  } else {
     error_log( "Android has set up the connection with php file.");
  }
  // decoding the json array
  // research if the second argument for json_decode is needed.
  $post = json_decode(file_get_contents("php://input"),true);
  $email = $post['email'];
  $password = $post['password'];
  $sql = "SELECT * from user_mst where Email = '$email' and Password = '$password'";
  $result = mysqli_query($conn, $sql);
  $row = mysqli_fetch_array($result);
  if($row) {
    $response = array();
    $response['status'] = "OK";
    $response['email'] = $row['email'];
    $response['password'] =$row['password'];
    error_log('Email: ' + $row['email']);
    echo json_encode($response);
  } else {
    // research if something should be sent to android like null object
    error_log("No data found!");
  }
  mysqli_close($conn);
 ?>
