import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class HttpConnection {

	private final String USER_AGENT = "Mozilla/5.0";
	
	/**
	 * Get the current national average gas price
	 * @param type : 	type of gas to check
	 * @return 			a float representing the price
	 * @throws Exception - not really
	 */
	public float getFuelPrice(String type){
		// send the http request to get the XML data
		String response = this.sendGet(false);
		String result = "";
		// parse the returned XML data
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try{
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(response)));
			document.getDocumentElement().normalize();
			// get the root node of the XML document
			Node node = document.getElementsByTagName("fuelPrices").item(0);
			Element element = (Element)node;
			// get the desired price by looking for the input type
			result = element.getElementsByTagName(type).item(0).getTextContent();
		}catch(Exception e){
			e.printStackTrace();
		}
		// return the float value of the result
		return Float.parseFloat(result);
	}
	
	/**
	 * Get the current national average gas price
	 * @param type : 	type of gas to check
	 * @param verbose : flag to print each step of the process
	 * @return			a float representing the price
	 * @throws Exception - not really
	 */
	public float getFuelPrice(String type, boolean verbose){
		// send the http request to get the XML data
		String response = this.sendGet(verbose);
		String result = "";
		// parse the returned XML data
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try{
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(response)));
			document.getDocumentElement().normalize();
			// get the root node of the XML document
			Node node = document.getElementsByTagName("fuelPrices").item(0);
			Element element = (Element)node;
			// get the desired price by looking for the input type
			result = element.getElementsByTagName(type).item(0).getTextContent();
			if(verbose){
				System.out.println(type + " : " + result);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		// return the float value of the result
		return Float.parseFloat(result);
	}
	
	private String sendGet(boolean verbose){
		String url = "http://www.fueleconomy.gov/ws/rest/fuelprices";
		StringBuffer response = new StringBuffer();
		try{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection)obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			if(verbose){
				System.out.println("Sending 'GET' request to URL");
				System.out.println("Response: " + responseCode);
			}
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			
			while((inputLine = in.readLine()) != null){
				response.append(inputLine);
			}
			in.close();
		}catch(Exception e){
			
		}
		
		return response.toString();
	}
	
}
