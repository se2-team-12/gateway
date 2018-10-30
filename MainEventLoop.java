

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
	    				urlParametersJson=ReadPython.readPython();
	    			} catch (IOException | ParseException e1)
	    			{
	    				e1.printStackTrace();
	    			}
	    			System.out.println(urlParametersJson.toString());
	    			String url = "https://team12.softwareengineeringii.com/api/gateway/heartbeat/"+ReadPython.reeadGatewayControllerID();
	  
	    			try {
						String response = Requests.sendPost(url, urlParametersJson);
						System.out.println("response"+response);
						//(1) we create an instance of JSONParser
						//(2)we create a JSONObject by parsing the FileReader of our .json file.
						//This JSONObject contains a collection of key-value pairs,
						//from which we can get every value of the json file. 
						
						JSONParser jsonParser = new JSONParser();
						JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
						String onDemand = (String)jsonObject.get("Type");
						if("cpuCount.py".equals(onDemand)||"memory.py".equals(onDemand)||"cpuBattery.py".equals(onDemand))
						{
							urlParametersJson=ReadPython.readPython(onDemand);
							System.out.println(urlParametersJson.toString());
							url="https://team12.softwareengineeringii.com/api/gateway/diagnostic/test";
							response = Requests.sendPost(url, urlParametersJson);
							System.out.println(Requests.sendPost(url, urlParametersJson));

							
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
	        }
	    };
	    
	    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);	
	}

}
