<?php
require "conn.php";

$response=array();

$sql="select id,specialization,full_name from doctor";
$result=mysqli_query($conn,$sql);

if($result){
	while($row=mysqli_fetch_array($result)) {
		
		$id=$row['id'];
		$sql1="select * from time_table where doctor_id like $id";
		$result1=mysqli_query($conn,$sql1);
		
		if($result1){
			while($row1=mysqli_fetch_array($result1)) {
				//$day = $row1["day"];
				//echo $day;
				array_push($response,array("Specialization"=>$row['specialization'],"Doctor"=>$row['full_name'],"Day"=>$row1["day"],"Time"=>$row1["time"]));
			}
		}	
	}
}

echo json_encode($response);
?>