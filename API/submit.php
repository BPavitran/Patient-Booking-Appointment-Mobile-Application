<?php
	require "conn.php";
	$patient_id=$_POST["patient_id"];
	$doctor_name=$_POST["doctor_name"];
	$date=$_POST["date"];
	
	$response=array();

	$sql="select id from doctor where full_name='$doctor_name'";
	$result=mysqli_query($conn,$sql);
	
	if($result){
		while($row=mysqli_fetch_array($result)) {
             $doctor_id=$row["id"];
			 $done="No";
			 
			$sql1="insert into patient_doctor (date,done,patient_id,doctor_id) values ('$date','$done','$patient_id','$doctor_id')";
			$result1=mysqli_query($conn,$sql1);
			$response=array();
			$message="Appointment successfully registered";
			array_push($response,array("message"=>$message));
			echo json_encode($response);
		}
			 
	}
?>