import java.sql.*;

public class jdbc_courses_student {
	public static void main (String[] args) throws SQLException {
		String Username = "smj038";
		String mysqlPassword = "21FMwarriors";
		
		jdbc_db myDB = new jdbc_db();
		myDB.connect(Username, mysqlPassword);
		myDB.initDatabase();
		
		StringBuilder builder = new StringBuilder();
		String query1 = "SELECT * FROM COURSE;";
		builder.append("<br> Lists of COURSES: " + myDB.query(query1) + "<br>");
		
		String studentID = "";
		studentID = args[0];
		
		String query2 = "SELECT * FROM COURSE WHERE STUDENTID = " + studentID + ";";
		
		builder.append("<br><br><br> Courses for STUDENTID: " + studentID + myDB.query(query2));
		System.out.println(builder.toString());
		
		myDB.disConnect();
	}
}