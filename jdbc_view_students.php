<html>
<body>
<h3> *** Database of Students *** </h3>

<form action = "hello.php" method = "POST">
	Click to go to home page<br>
	<input name = "submit" type = "submit" value = "Main Menu">
</form>

</body>
</html>

<?php
    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar jdbc_view_students ';
	
    // remove dangerous characters from command to protect web server
    $command = escapeshellcmd($command);
    echo "<p>command: $command <p>";
 
    // run jdbc_courses_student.exe
    system($command);   
?>