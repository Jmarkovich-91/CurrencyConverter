import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RateDAOImpl implements RateDAO {
	
	Connection connection = null;	// Our connection to the database
	PreparedStatement stmt = null;	// Use prepared statements to help protect against SQL injection

	@Override
	public Rate getRate (String startCurrency, String endCurrency) {
		Rate rate = null;
		
		try {
			connection = DAOUtility.getConnection(); // Get our database connection from the manager
			String sql = "SELECT * FROM \"Exchange_Rates\" WHERE \"Starting_Currency\" = ? AND \"Ending_Currency\" = ?"; // Our SQL query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			
			stmt.setString(1, startCurrency); //Set first parameter
			stmt.setString(2, endCurrency); //Set second parameter
			
			ResultSet rs = stmt.executeQuery (); //Execute query
			
			if (rs.next()) {
				rate = new Rate ();
				rate.setStartCurrency(rs.getString("Starting_Currency"));
				rate.setEndCurrency(rs.getString("Ending_Currency"));
				rate.setRate(rs.getDouble("Exchange_Rate"));
				rate.setInsertTime(rs.getTimestamp("Insert_Dt_Time").toLocalDateTime());
			}
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime temp = LocalDateTime.from(rate.getInsertTime());
			long days = temp.until(now, ChronoUnit.DAYS); //See how many days it has been since rate was updated
			if (days > 0) {
				ConverterAPI converter = new ConverterAPI(); //Get a new rate if older than 1 day
				Rate newRate = new Rate (startCurrency, endCurrency, converter.convert(startCurrency, endCurrency), LocalDateTime.now());
				updateRate(newRate);
				return newRate;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources ();
		}
		return rate;
	}
	
	@Override
	public boolean addRate (Rate rate) {
		
		try {
			connection = DAOUtility.getConnection(); // Get our database connection from the manager
			String sql = "INSERT INTO \"Exchange_Rates\" VALUES (?, ?, ?, ?)"; // Our SQL query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			
			stmt.setString(1, rate.getStartCurrency());
			stmt.setString(2, rate.getEndCurrency());
			stmt.setDouble(3, rate.getRate());
			stmt.setTimestamp(4, Timestamp.valueOf(rate.getInsertTime()));
			
			// If we were able to add our rate to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources ();
		}
	}
	
	@Override
	public boolean updateRate (Rate rate) {
		
		try {
			connection = DAOUtility.getConnection(); // Get our database connection from the manager
			String sql = "UPDATE \"Exchange_Rates\" SET \"Exchange_Rate\" = ?, \"Insert_Dt_Time\" = ? WHERE \"Starting_Currency\" = ? AND \"Ending_Currency\" = ?"; // Our SQL query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			
			stmt.setDouble(1, rate.getRate());
			stmt.setTimestamp(2, Timestamp.valueOf(rate.getInsertTime()));
			stmt.setString(3, rate.getStartCurrency());
			stmt.setString(4, rate.getEndCurrency());
			
			// If we were able to update our rate, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources ();
		}
	}
	
	@Override
	public boolean deleteRate (Rate rate) {
		
		try {
			connection = DAOUtility.getConnection(); // Get our database connection from the manager
			String sql = "DELETE \"Exchange_Rates\" WHERE \"Starting_Currency\" = ? AND \"Ending_Currency\" = ?"; // Our SQL query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			
			stmt.setString(1, rate.getStartCurrency());
			stmt.setString(2, rate.getEndCurrency());
			
			// If we were able to delete our rate, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources ();
		}
	}
	
	
	// Close all resources to prevent memory leaks.
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}
