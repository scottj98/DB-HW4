import java.sql.*;
import java.util.Scanner;

public class jdbc_example {

    // The instance variables for the class
    private static Connection connection;
    private static Statement statement;
	private static int aID;
	private static int cID;
	private static int pID;
	private static int grabTheID;
    // The constructor for the class
    public jdbc_example() {
        connection = null;
        statement = null;
    }

    // The main program", that tests the methods
    public static void main(String[] args) throws SQLException {
        String Username = "smj038";              // Change to your own username
        String mysqlPassword = "21FMwarriors";    // Change to your own mysql Password

        jdbc_example test = new jdbc_example();
        test.connect(Username, mysqlPassword);
        //test.initDatabase(Username, mysqlPassword, Username);
        statement = connection.createStatement();

	int number = 1;
	while (number != 0){
        	System.out.println("Main Menu");

        	System.out.println("1. Find all agents and clients in a given city");
        	System.out.println("2. Add a new client, then purchase an available policy from a particular agent");
        	System.out.println("3. List all policies sold by a particular agent");
        	System.out.println("4. Cancel a policy");
        	System.out.println("5. Add a new agent to a city");
        	System.out.println("6. Quit");

        	Scanner input = new Scanner(System.in);

        	System.out.println("Enter a number from the Main Menu");
        	int choice = input.nextInt();

        	switch(choice){
        	case 1:
                	Scanner input1 = new Scanner(System.in);
                	System.out.println("Enter a city");
                	String city1 = input1.nextLine();

			test.operation1(city1); // thinking this looks better in a function

                	break;
            	case 2:
			Scanner input2 = new Scanner(System.in);

			System.out.println("Enter your name: "); // getting user's name
			String name = input2.nextLine();

			System.out.println("Enter your city: "); // getting user's city
			String city2 = input2.nextLine();

			System.out.println("Enter your zip code: "); // getting user's zip code
			int zipCode = input2.nextInt();

			System.out.println("\n          --- POLICIES ---");
			System.out.println("DENTAL | LIFE | HOME | HEALTH | VEHICLE"); // lists policies for user
			System.out.println("What kind of policy do you want to purchase? ");
			String purchasePolicy = input2.nextLine(); // gets policy that user wants
			purchasePolicy = purchasePolicy.toUpperCase(); // changes to upper case to fit format

			test.operation2(name, city2, zipCode);

                	break;
            	case 3:
					Scanner input3 = new Scanner(System.in);

					System.out.println("Enter agent's name: ");
					String agentName = input3.nextLine();
					agentName = agentName.toUpperCase();

					System.out.println("Enter agent's city: ");
					String agentCity = input3.nextLine();
					agentCity = agentCity.toUpperCase();

					test.operation3(agentName, agentCity);

                break;

            	case 4: 
                	test.operation4a();

			Scanner input4 = new Scanner(System.in);

			System.out.println("Enter the purchase id that you wish to cancel");
			int id = input4.nextInt();

			test.operation4b(id);

                	break;

        	case 5:
			try{
				Scanner input5 = new Scanner(System.in);

				String agent_id = "SELECT MAX(A_ID) AS A_ID FROM AGENTS";
				Statement state = connection.createStatement();
				ResultSet res5a = state.executeQuery(agent_id);
				if(res5a.next()){
					aID = res5a.getInt("A_ID");
					aID++;
				}
				//System.out.println("Enter an Agent ID");
				//int a_id = input5.nextInt();
				//String a_name = input5.nextLine();
				System.out.println("Enter an Agent Name");
				String a_name = input5.nextLine();
				
				System.out.println("Enter a zip code");
				int a_zip = input5.nextInt();
				String city = input5.nextLine();
				System.out.println("Enter a city");
				city = input5.nextLine();
			
				String adds = aID + ",'"+a_name+"',"+"'"+city+"',"+a_zip;
			
				test.insert("AGENTS", adds);
				test.operation5(city);
			}
			catch(SQLException err) {
				System.out.println(err.getMessage());
			}

                	break;

                case 6:
                        number = 0;
                	test.disConnect();
                	break;

            	default:
                	System.out.println("Number doesnt exist"); //we can have it quit?
        		test.disConnect();
   		}
	}

    }
    public void operation1(String c_city) {
	String query1 = "SELECT * FROM CLIENTS WHERE C_CITY = '" + c_city + "';"; //depends on how you named your tables
        String query1a = "SELECT * FROM AGENTS WHERE A_CITY = '" + c_city + "';";

        query(query1);
        query(query1a);
    }

   public void operation2(String name, String city, int zip) {
        try {

            Scanner input2b = new Scanner(System.in);

            String clientID = "SELECT MAX(C_ID) AS C_ID FROM CLIENTS";
            Statement st2a = connection.createStatement();
            ResultSet rs2a = st2a.executeQuery(clientID);
            if (rs2a.next()) {
                cID = rs2a.getInt("C_ID");
                cID++;
            }
            st2a.close();

            String op2Insert = cID + ",'"+ name + "'," + "'" + city + "'," + "'" + zip + "'";
            insert("CLIENTS", op2Insert); // not sure if correct format

            String check = "SELECT * FROM CLIENTS";
            query(check);

            System.out.println("\n          --- POLICIES ---");
            System.out.println("DENTAL | LIFE | HOME | HEALTH | VEHICLE"); // lists policies for user
            System.out.println("What kind of policy do you want to purchase? ");
            String purchasePolicy = input2b.nextLine(); // gets policy that user wants
            purchasePolicy = purchasePolicy.toUpperCase(); // changes to upper case to fit format

            String checkPolicy = "SELECT * FROM POLICY WHERE TYPE = '" + purchasePolicy + "'";

            ResultSet rs2b = statement.executeQuery(checkPolicy);

            if (rs2b.next()) {
                String query2 = "SELECT * FROM AGENTS, POLICY " +
                                "WHERE AGENTS.A_CITY = '" + city + "' AND POLICY.TYPE = '" + purchasePolicy + "'";
                query(query2);

                String grabID = "SELECT C_ID FROM CLIENTS WHERE C_NAME = '" + name + "'";
                Statement st2b = connection.createStatement();
                ResultSet rs2c = st2b.executeQuery(grabID);
                if (rs2c.next()) {
                    grabTheID = rs2c.getInt("C_ID");
                }
                //query(grabID);

                System.out.println("Enter agents's ID to buy policy from: ");
                int agentID = input2b.nextInt();

                System.out.println("Enter a policy ID shown: ");
                int pChoice = input2b.nextInt();

                System.out.println("Enter the amount you want: ");
                Double amount = input2b.nextDouble();

                input2b.nextLine();

                System.out.println("Enter the current date (yyyy-mm-dd): ");
                String date = input2b.nextLine();

                String grabPurchase = "SELECT MAX(PURCHASE_ID) AS PURCHASE_ID FROM POLICIES_SOLD";
                Statement st2c = connection.createStatement();
                ResultSet rs2d = st2c.executeQuery(grabPurchase);
                if (rs2d.next()) {
                    pID = rs2d.getInt("PURCHASE_ID");
                    pID++;
                }
                st2c.close();

                String op2String = pID + ", " + agentID + ", " + grabTheID + ", " + pChoice + ",'" + date + "'," + amount;

                insert("POLICIES_SOLD", op2String);

                String check2 = "SELECT * FROM POLICIES_SOLD";
                query(check2);
            }
            else
                System.out.println("Policy not found!");
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
    }
	
    public void operation3(String aName, String aCity) {

		try {
            String checkNameCity = "SELECT A_NAME, A_CITY FROM AGENTS WHERE A_NAME = '" + aName + "' AND A_CITY = '" + aCity + "'";
            Statement st3 = connection.createStatement();
            ResultSet rs3 = statement.executeQuery(checkNameCity);

            if (rs3.next()) {
                String policies = "SELECT TYPE FROM POLICY " +
                                  "WHERE POLICY_ID IN (SELECT POLICY_ID FROM POLICIES_SOLD " +
                                  "WHERE AGENT_ID IN (SELECT A_ID FROM AGENTS WHERE A_NAME = '" + aName + "'))";
                query(policies);

                String query3 = "SELECT NAME, TYPE, COMMISSION_PERCENTAGE FROM POLICY " +
                                "WHERE POLICY_ID IN (SELECT POLICY_ID FROM POLICIES_SOLD " +
                                "WHERE AGENT_ID IN (SELECT A_ID FROM AGENTS WHERE A_NAME = '" + aName + "'))";
                query(query3);
            }
            else
                System.out.println("Agent not found!");

            //st3.close();
		} catch (SQLException err) {
            System.out.println(err.getMessage());
		}
	}
	
    public void operation4a(){
	String query4a = "SELECT * FROM POLICIES_SOLD;";

	query(query4a);
    }

    public void operation4b(int pur_id) {
	try{
		int row = statement.executeUpdate(deleteById(pur_id));
		System.out.println(row);
	}
	catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } 
	catch (Exception e) {
            e.printStackTrace();
        }
	
	String query4b = "SELECT * FROM POLICIES_SOLD;";
	query(query4b);
	
    }
	
    public static String deleteById(int p_id){
	return "DELETE FROM POLICIES_SOLD WHERE PURCHASE_ID = " + p_id + ";";
    }
	
    public void operation5(String city){
    	String query5 = "SELECT * FROM AGENTS WHERE A_CITY = '" + city + "';";
	
	query(query5);
	

    }

    // Connect to the database
    public void connect(String Username, String mysqlPassword) throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + Username + "?" +
                    "user=" + Username + "&password=" + mysqlPassword);
            //connection = DriverManager.getConnection("jdbc:mysql://localhost/" + Username +
             //       "?user=" + Username + "&password=" + mysqlPassword);
        }
        catch (Exception e) {
            throw e;
        }
    }

    // Disconnect from the database
    public void disConnect() throws SQLException {
        connection.close();
        statement.close();
    }

    // Execute an SQL query passed in as a String parameter
    // and print the resulting relation
    public void query(String q) {
        try {
            ResultSet resultSet = statement.executeQuery(q);
            System.out.println("\n---------------------------------");
            System.out.println("Query: \n" + q + "\n\nResult: ");
            print(resultSet);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
	

    // Print the results of a query with attribute names on the first line
    // Followed by the tuples, one per line
    public void print(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumns = metaData.getColumnCount();

        printHeader(metaData, numColumns);
        printRecords(resultSet, numColumns);
    }

    // Print the attribute names
    public void printHeader(ResultSetMetaData metaData, int numColumns) throws SQLException {
        for (int i = 1; i <= numColumns; i++) {
            if (i > 1)
                System.out.print(",  ");
            System.out.print(metaData.getColumnName(i));
        }
        System.out.println();
    }

    // Print the attribute values for all tuples in the result
    public void printRecords(ResultSet resultSet, int numColumns) throws SQLException {
        String columnValue;
        while (resultSet.next()) {
            for (int i = 1; i <= numColumns; i++) {
                if (i > 1)
                    System.out.print(",  ");
                columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }

    // Insert into any table, any values from data passed in as String parameters
    public void insert(String table, String values) {
        String queryInsert = "INSERT into " + table + " values ("+ values +")" ;
        try {
            statement.executeUpdate(queryInsert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Remove all records and fill them with values for testing
    // Assumes that the tables are already created
    /*public void initDatabase(String Username, String Password, String SchemaName) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DELETE from CLIENTS");
        statement.executeUpdate("DELETE from AGENTS");
        statement.executeUpdate("DELETE from POLICY");
        statement.executeUpdate("DELETE from POLICIES_SOLD");
		insert("CLIENTS", "101, 'CHRIS', 'DALLAS', 43214");
        insert("CLIENTS", "102, 'OLIVIA', 'BOSTON', 83125");
		insert("CLIENTS", "103, 'ETHAN', 'FAYETTEVILLE', 72701");
		insert("CLIENTS", "104, 'DANIEL', 'NEWYORK', 53421");
		insert("CLIENTS", "105, 'TAYLOR', 'ROGERS', 78291");
		insert("CLIENTS", "106, 'CLAIRE', 'PHOENIX', 85011");
        insert("AGENTS", "201, 'ANDREW', 'DALLAS', 43214");
		insert("AGENTS", "202, 'PHILIP', 'PHOENIX', 85011");
		insert("AGENTS", "203, 'JERRY', 'BOSTON', 83125");
		insert("AGENTS", "204, 'BRYAN', 'ROGERS', 78291");
		insert("AGENTS", "205, 'TOMMY', 'DALLAS', 43214");
		insert("AGENTS", "206, 'BRYANT', 'FAYETTEVILLE', 72701");
		insert("AGENTS", "207, 'SMITH', 'ROGERS', 78291");
        insert("POLICY", "301, 'CIGNAHEALTH', 'DENTAL', 5");
		insert("POLICY", "302, 'GOLD', 'LIFE', 8");
		insert("POLICY", "303, 'WELLCARE', 'HOME', 10");
		insert("POLICY", "304, 'UNITEDHEALTH', 'HEALTH', 7");
		insert("POLICY", "305, 'UNITEDCAR', 'VEHICLE', 9");
		insert("POLICIES_SOLD", "401, 204, 106, 303, STR_TO_DATE('2020-01-02', '%Y-%m-%d'), 2000.00");
		insert("POLICIES_SOLD", "402, 201, 105, 305, STR_TO_DATE('2019-08-11', '%Y-%m-%d'), 1500.00");
		insert("POLICIES_SOLD", "403, 203, 106, 301, STR_TO_DATE('2019-09-08', '%Y-%m-%d'), 3000.00");
		insert("POLICIES_SOLD", "404, 207, 101, 305, STR_TO_DATE('2019-06-21', '%Y-%m-%d'), 1500.00");
		insert("POLICIES_SOLD", "405, 203, 104, 302, STR_TO_DATE('2019-11-14', '%Y-%m-%d'), 4500.00");
		insert("POLICIES_SOLD", "406, 207, 105, 305, STR_TO_DATE('2019-12-25', '%Y-%m-%d'), 1500.00");
		insert("POLICIES_SOLD", "407, 205, 103, 304, STR_TO_DATE('2020-10-15', '%Y-%m-%d'), 5000.00");
		insert("POLICIES_SOLD", "408, 204, 103, 304, STR_TO_DATE('2020-02-15', '%Y-%m-%d'), 5000.00");
		insert("POLICIES_SOLD", "409, 203, 103, 304, STR_TO_DATE('2020-01-10', '%Y-%m-%d'), 5000.00");
		insert("POLICIES_SOLD", "410, 202, 103, 303, STR_TO_DATE('2020-01-30', '%Y-%m-%d'), 2000.00");
    }*/
}
