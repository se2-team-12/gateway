
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class ReadPython {
	@SuppressWarnings("unchecked")
	public static JSONObject readPython() throws IOException, ParseException
	{

		JSONObject json = new JSONObject();
		json.put("GatewayId",readGatewayControllerID());
		Process pDiagnostics = Runtime.getRuntime().exec("python timestamp.py");
		BufferedReader inDiagnostics = new BufferedReader(new InputStreamReader(pDiagnostics.getInputStream()));
		json.put("TimeStamp",inDiagnostics.readLine());
		json.put("Token",Requests.readAccessToken());


		return json;
	}


	public static  String readGatewayControllerID()
	{
		String filename = "gatewayID.txt";
		Scanner inputFile = null;
		String gatewayID="";

		try {
			inputFile = new Scanner(new File(filename));

		}catch(FileNotFoundException e) {
			System.out.println("FAILURE cannot open file: " + filename + " for input" +
					" EXIT ON FAILURE TO OPEN FILE.");
			System.exit(0);
		}

		if(inputFile.hasNext())
		{
			gatewayID = inputFile.next();
		}
		inputFile.close();
		return gatewayID;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject readPython(String nameOfDiagnostics) throws IOException, ParseException
	{
		JSONObject json = new JSONObject();
		json.put("GatewayId",readGatewayControllerID());
		Process pDiagnostics = Runtime.getRuntime().exec("python "+nameOfDiagnostics);
		BufferedReader inDiagnostics = new BufferedReader(new InputStreamReader(pDiagnostics.getInputStream()));
		json.put("Type",inDiagnostics.readLine());
		json.put("Result",inDiagnostics.readLine());

		Process heartbeat = Runtime.getRuntime().exec("python timestamp.py");
		BufferedReader timeStamp = new BufferedReader(new InputStreamReader(heartbeat.getInputStream()));
		json.put("TimeStamp",timeStamp.readLine());
		json.put("Token",Requests.readAccessToken());

		return json;
	}

}
