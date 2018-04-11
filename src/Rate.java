
import java.time.LocalDateTime;

public class Rate {
	
	private String startCurrency;
	private String endCurrency;
	private double rate;
	private LocalDateTime insertTime; //Date added to the database
	
	public Rate (String startCurrency, String endCurrency, double rate, LocalDateTime insertTime) {
		this.startCurrency = startCurrency;
		this.endCurrency = endCurrency;
		this.rate = rate;
		this.insertTime = insertTime;
	}
	
	//Default constructor
	public Rate () {
		this.startCurrency = null;
		this.endCurrency = null;
		this.rate = 0;
		this.insertTime = LocalDateTime.now();
	}
	
	//Getter and setter methods
	public String getStartCurrency () {
		return this.startCurrency;
	}
	
	public void setStartCurrency (String startCurrency) {
		this.startCurrency = startCurrency;
	}
	
	public String getEndCurrency () {
		return this.endCurrency;
	}
	
	public void setEndCurrency (String endCurrency) {
		this.endCurrency = endCurrency;
	}
	
	public double getRate () {
		return this.rate;
	}
	
	public void setRate (double rate) {
		this.rate = rate;
	}
	
	public LocalDateTime getInsertTime () {
		return this.insertTime;
	}
	
	public void setInsertTime (LocalDateTime insertTime) {
		this.insertTime = insertTime;
	}

}
