<?php
require "conn.php";
$id=$_GET["id"];

$mysql_qry="select specification from patient where id like '$id'";
$result=mysqli_query($conn,$mysql_qry);
if(mysqli_num_rows($result)>0){
	//echo "Patient Login success";
	$row=mysqli_fetch_row($result);
	$specification=$row[0];
	echo json_encode(array("Specification"=>$specification));
}

?>