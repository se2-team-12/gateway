


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
		json.put("GatewayId",reeadGatewayControllerID());	
		Process pDiagnostics = Runtime.getRuntime().exec("python /Users/batoolalsmael/Desktop/SW#2/gateway/diagnostics.py");
		BufferedReader inDiagnostics = new BufferedReader(new InputStreamReader(pDiagnostics.getInputStream()));
		json.put("TimeStamp",inDiagnostics.readLine());
		
		
		return json;
	}
	
	
	public static  String reeadGatewayControllerID()
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
	public static JSONObject readPython(String nameOfDemand) throws IOException, ParseException 
	{
		JSONObject json = new JSONObject();
		json.put("GatewayId",reeadGatewayControllerID());
		Process pDiagnostics = Runtime.getRuntime().exec("python /Users/batoolalsmael/Desktop/SW#2/gateway/"+nameOfDemand);
		BufferedReader inDiagnostics = new BufferedReader(new InputStreamReader(pDiagnostics.getInputStream()));
		json.put("Type",inDiagnostics.readLine());
		json.put("Result",inDiagnostics.readLine());
		json.put("IsClear","true");
		
		
		return json;
	}

}
