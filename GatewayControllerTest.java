package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import gatewayController.ReadPython;

public class GatewayControllerTest {

	@Test
	public void test() {
		System.out.println("\n------ Testing Gateway Controller... ------");
		
		
		JSONObject urlParametersJson = null;
		try
		{
			urlParametersJson= ReadPython.readPython();
		} catch (IOException | ParseException e1)
		{
			e1.printStackTrace();
		}
		System.out.println(urlParametersJson.toString());
		String url = "https://team12.softwareengineeringii.com/api/gateway/heartbeat/"+gatewayController.ReadPython.reeadGatewayControllerID();

		try {
			String response = gatewayController.Requests.sendPost(url, urlParametersJson);
			System.out.println("response"+response);
			//(1) we create an instance of JSONParser
			//(2)we create a JSONObject by parsing the FileReader of our .json file.
			//This JSONObject contains a collection of key-value pairs,
			//from which we can get every value of the json file. 
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
			String message = (String)jsonObject.get("message");
			System.out.println("\n------ Testing heartbeat... ------");
			assertEquals(message, "Success");
			//assertEquals(gateway_Controller.ReadPython.readPython(), "Error"); 
			String onDemand = "cpuCount.py";
			if("cpuCount.py".equals(onDemand)||"memory.py".equals(onDemand)||"cpuBattery.py".equals(onDemand))
			{
				urlParametersJson= ReadPython.readPython(onDemand);
				System.out.println(urlParametersJson.toString());
				url="https://team12.softwareengineeringii.com/api/gateway/diagnostic/test";
				response = gatewayController.Requests.sendPost(url, urlParametersJson);
				System.out.println(gatewayController.Requests.sendPost(url, urlParametersJson));
				jsonObject = (JSONObject) jsonParser.parse(response);
				message = (String)jsonObject.get("message");
				System.out.println("\n------ Testing on-demand request... ------");
				assertEquals(message, "POST Success");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		 
	}

}
