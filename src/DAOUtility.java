
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Class used to create a single instance of a database connection

public class DAOUtility {
	
	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "";
	private static final String URL = "jdbc:postgresql://localhost:5432/CurrencyConverter"; //PostgreSQL has to be running to connect to database
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	public static RateDAO getRateDAO () {
		return new RateDAOImpl ();
	}

}
