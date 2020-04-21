package package3;

/**
 * Interface contianing the credentials necessary to connect and use a SQL database. It
 * contains variables for the URL, the password, the username, the name of the database,
 * and driver information.
 * @author Vaibhav Kapoor, Thomas Pan, and Matthew Wells
 *
 */
public interface DBCredentials {
	

	static final String USER = "root";
	static final String PASS = "ENSF409W20Final_Project";
	static final String URL = "jdbc:mysql://localhost:3306/mydb";
	static final String DRIVER = "con.mysql.cj.jdbc.Driver";
	static final String DBNAME = "mydb";
	
	
}
