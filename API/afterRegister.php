<?php
require "conn.php";
$username=$_GET["username"];

$mysql_qry="select id from patient where username like '$username'";
$result=mysqli_query($conn,$mysql_qry);
$response=array();
if(mysqli_num_rows($result)>0){
	//echo "Patient Login success";
	$row=mysqli_fetch_row($result);
	$id=$row[0];
	$code="login_success";
	array_push($response,array("code"=>$code,"username"=>$username,"id"=>$id));
	echo json_encode($response);
}
	else{
		//echo "Login not success";
		$code="login_failed";
		$message="User not found...Please try again...";
		array_push($response,array("code"=>$code,"message"=>$message));
		echo json_encode($response);
	}
?>