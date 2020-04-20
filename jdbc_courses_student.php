<html>
<body>
<h3>*** Enter information to view the student's current courses ***</h3>

<form action = "jdbc_courses_student.php" method = "POST">
	STUDENT ID: <input type = "text" name = "studentID"><br>
	<input name = "submit" type = "submit">
</form>
<br><br>

<form action = "hello.php" method = "POST">
	Click to go to home page<br>
	<input name = "submit" type = "submit" value = "Main Menu">
</form>

</body>
</html>

<?php
if (isset($_POST['submit'])) 
{
    // replace ' ' with '\ ' in the strings so they are treated as single command line args
    $studentID = escapeshellarg($_POST[studentID]);

    $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar jdbc_courses_student ' . $studentID;
	
    // remove dangerous characters from command to protect web server
    $command = escapeshellcmd($command);
    echo "<p>command: $command <p>";
 
    // run jdbc_courses_student.exe
    system($command);           
}
?>
