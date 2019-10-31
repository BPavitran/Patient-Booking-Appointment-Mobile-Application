<?php	
	require "conn.php";
	$name=$_POST["name"];
	$surname=$_POST["surname"];
	$full_name=$_POST["full_name"];
	$email=$_POST["email"];
	$username=$_POST["username"];
	$password=$_POST["password"];
	
	$sql="select * from patient where username like '$username';";
	
	$result=mysqli_query($conn,$sql);
	$response=array();
	
	if(mysqli_num_rows($result)>0){
		$code="reg_failed";
		$message="User already exist...";
		array_push($response,array("code"=>$code,"message"=>$message));
		echo json_encode($response);
	}
	else{
		$sql="insert into patient (name,surname,full_name,email,username,password) values ('$name','$surname','$full_name','$email','$username','$password')";
		$result=mysqli_query($conn,$sql);
		$response=array();
		$code="reg_success";
		$message="Thank you for register with us. Now you can login...";
		array_push($response,array("code"=>$code,"message"=>$message));
		echo json_encode($response);
	}
	
	mysqli_close($conn);
		
		?>