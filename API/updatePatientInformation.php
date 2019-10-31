<?php	
	require "conn.php";
	$id=$_POST["id"];
	$age=$_POST["age"];
	$address=$_POST["address"];
	$city=$_POST["city"];
	$email=$_POST["email"];
	$phone=$_POST["phone"];
	
		$sql = "update patient set age='$age', address='$address', city='$city', email='$email', phone='$phone' where id='$id'";
		$result=mysqli_query($conn,$sql);
		$response=array();
		$code="update_success";
		$message="Information successfully updated";
		array_push($response,array("code"=>$code,"message"=>$message));
		echo json_encode($response);
	
	mysqli_close($conn);
		
		?>