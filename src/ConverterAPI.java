
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class ConverterAPI {

	// API Provider URL
	private static final String API_PROVIDER = "http://api.fixer.io/";

	public double convert(String fromCurrencyCode, String toCurrencyCode) {

			CurrencyConversionResponse response = getResponse(API_PROVIDER + "/latest?base=" + fromCurrencyCode);

			if (response != null) {

				String rate = response.getRates().get(toCurrencyCode);

				double conversionRate = Double.valueOf((rate != null) ? rate : "0.0");

				return conversionRate;
			}

		return 0.0;
	}

	// Method to get the response from API
	private CurrencyConversionResponse getResponse(String strUrl) {

		CurrencyConversionResponse response = null;

		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer();

		if (strUrl == null || strUrl.isEmpty()) {

			System.out.println("Application Error");
			return null;
		}

		URL url;
		try {
			url = new URL(strUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			InputStream stream = connection.getInputStream();

			int data = stream.read();

			while (data != -1) {

				sb.append((char) data);

				data = stream.read();
			}

			stream.close();

			response = gson.fromJson(sb.toString(), CurrencyConversionResponse.class);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		}

		return response;
	}
}
