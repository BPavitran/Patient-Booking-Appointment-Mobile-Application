<?php
require "conn.php";
$id=$_GET["id"];

$mysql_qry="select * from patient where id like '$id'";
$result=mysqli_query($conn,$mysql_qry);
$response=array();
if(mysqli_num_rows($result)>0){
	$row=mysqli_fetch_row($result);
	$name=$row[2];
	$surname=$row[3];
	$full_name=$row[4];
	$age=$row[5];
	$gender=$row[6];
	$blood=$row[7];
	$NIC=$row[8];
	$address=$row[9];
	$city=$row[10];
	$email=$row[11];
	$phone=$row[14];
	$username=$row[13];
	array_push($response,array("name"=>$name,"surname"=>$surname,"full_name"=>$full_name,"age"=>$age,"gender"=>$gender,"blood"=>$blood,"NIC"=>$NIC,"address"=>$address,"city"=>$city,"email"=>$email,"phone"=>$phone,"username"=>$username));
	echo json_encode($response);
}
?>