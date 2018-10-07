<?php 
	
	$data = $_POST['data'];
	if(empty($data))
	{
		echo "Not recieved in ajax!<br>";
	}
	else
	{
		$myfile = fopen("data.txt", "w");
		fwrite($myfile, $data);
		@fclose($myFile);
		$command = escapeshellcmd("python summary.py data.txt");
		$output = shell_exec($command);
		echo $output;
		// $myfile1 = fopen("summary.txt", "w");
		// fwrite($myfile1, $output);
		// @fclose($myFile1);
	}
	

 ?>