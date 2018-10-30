

import java.io.*;
import java.net.URL;
import java.util.Scanner;


import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONObject;



public class Requests {
	// HTTP POST request
	public static String sendPost(String url, JSONObject  urlParametersJson) throws Exception
	{
		
		 
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestProperty ("accesstoken", reeadAccessToken());
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        	con.setRequestProperty("Accept", "application/json");
      		con.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParametersJson.toString());
		wr.flush();
		wr.close();
		//int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParametersJson);
		//System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		in.close();
		
		//print result
		//System.out.println(response.toString());
        return response.toString();
	}

	public static  String reeadAccessToken()
	{
		String filename = "token.txt";
		Scanner inputFile = null;
		String accessToken="";

		try {
			inputFile = new Scanner(new File(filename));

		}catch(FileNotFoundException e) {
			System.out.println("FAILURE cannot open file: " + filename + " for input" +
					" EXIT ON FAILURE TO OPEN FILE.");
			System.exit(0);
		}

		if(inputFile.hasNext())
		{
			accessToken = inputFile.next();
		}
		inputFile.close();
		return accessToken;
	}
	

}
