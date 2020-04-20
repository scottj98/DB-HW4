import java.sql.*;

public class jdbc_courses_student {
	public static void main (String[] args) throws SQLException {
		String Username = "smj038";
		String mysqlPassword = "21FMwarriors";
		
		jdbc_db myDB = new jdbc_db();
		myDB.connect(Username, mysqlPassword);
		myDB.initDatabase();
		
		StringBuilder builder = new StringBuilder();
		
		String studentID = "000";
		studentID = args[0];
		
		String query1 = "SELECT * FROM COURSE " +
						"WHERE DEPTCODE IN " +
						"(SELECT DEPTCODE FROM ENROLLMENT WHERE STUDENTID = " + studentID + ");";
		
		builder.append("<br><br><br> Courses for STUDENT ID (" + studentID + "):" + myDB.query(query1) + "<br>");
		System.out.println(builder.toString());
		
		myDB.disConnect();
	}
}
