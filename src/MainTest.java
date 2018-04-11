import java.time.Instant;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RateDAO dao = DAOUtility.getRateDAO();
		
		Rate testRate = dao.getRate("USD", "GBP");
		System.out.println("Conversion Rate from USD to JPY on " + testRate.getInsertTime() +
				": " + testRate.getRate());
		
		//Converter converter = new Converter();
		//System.out.println(converter.convert("USD", "GBP"));

	}

}
