import java.sql.*;

public class jdbc_view_students {
    public static void main (String[] args) throws SQLException {
        String Username = "smj038";
        String mysqlPassword = "21FMwarriors";

        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();

        StringBuilder builder = new StringBuilder();
        String query1 = "SELECT * FROM STUDENT;";
        builder.append("<br>" + myDB.query(query1) + "<br>");
        System.out.println(builder.toString());
		
		myDB.disConnect();
    }
}
