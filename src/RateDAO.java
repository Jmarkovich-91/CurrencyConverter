
public interface RateDAO {
	
	//Interface for the Data Access Object to handle database queries for Rates.
	public Rate getRate (String startCurrency, String endCurrency);
	public boolean addRate (Rate rate);
	public boolean updateRate (Rate rate);
	public boolean deleteRate (Rate rate);

}
