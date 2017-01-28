<?php
$dsn = 'mysql:dbname=project;host=localhost;charset=utf8';
try{
	
	$pdo = new PDO($dsn, "root","root",[
	    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
	      PDO::ATTR_EMULATE_PREPARES => false,]);
}catch(PDOException $e){
	exit('Failed to connect to db.' .$e->getMessage());
}

$post = json_decode(file_get_contents("php://input"),true);
$userId = $post['userId'];

$st = $pdo->prepare('SELECT * FROM user_mst where user_id = :userId');
$st->bindParam(':userId', $userId, PDO::PARAM_STR); 
$st->execute();
$count =$st->rowCount();
if($count < 1 or is_null($count)){
	$row = $st->fetch(PDO::FETCH_ASSOC);
    $response = array();
    $response['type'] = "search";
    $response['status'] = "OK";
    echo json_encode($response);
}else{
	error_log("data found!");
	$response = array();
    $response['type'] = "search";
    $response['status'] = "NG";
    echo json_encode($response);
}    
?>

