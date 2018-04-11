import java.util.Map;
import java.util.TreeMap;

public class CurrencyConversionResponse {

	private String base;

	private Map<String, String> rates = new TreeMap<String, String>();

	public Map<String, String> getRates() {
		return rates;
	}

	public void setRates(Map<String, String> rates) {
		this.rates = rates;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

}
