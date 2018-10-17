package http_requests;
 

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MainEventLoop {

	public static void main(String[] args) 
	{
	   
		Runnable runnable = new Runnable() {
	    		public void run() {
	    			JSONObject urlParametersJson = null;
	    			try
	    			{
	    				urlParametersJson=http_requests.ReadPython.readPython();
	    			} catch (IOException | ParseException e1)
	    			{
	    				e1.printStackTrace();
	    			}
	    			System.out.println(urlParametersJson.toString());
	    			//String url = "https://balsumae.create.stedwards.edu/cosc3326/insertGateway.php";
	    			String url = "https://team12.softwareengineeringii.com/api/gateway";
	    			try {
						System.out.println(http_requests.Requests.sendPost(url, urlParametersJson));
						String response = http_requests.Requests.sendPost(url, urlParametersJson);
						//(1) we create an instance of JSONParser
						//(2)we create a JSONObject by parsing the FileReader of our .json file.
						//This JSONObject contains a collection of key-value pairs,
						//from which we can get every value of the json file. 
						JSONParser jsonParser = new JSONParser();
						JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
						// get a boolean from the JSON object
						/*boolean onDemand = (boolean) jsonObject.get("onDemand");
						 *String  typeOfDemand = jsonObject.get("typeOfDemand");
						 *
						System.out.println("The onDemand is: " + onDemand);
						
						if(onDemand)
						{
							
						}
						
						String  status= (String) jsonObject.get("Status");
						if(status.equals("OK"))
						{
							System.out.println("status is OK ");
						}*/
						
					} catch (Exception e) {
						e.printStackTrace();
					}
	        }
	    };
	    
	    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);	
	}
	
	
	

}
