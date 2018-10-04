package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ReadPython {
	public static void main(String args[])
	{
		
		try{
			String urlParameters ="";
			Process p = Runtime.getRuntime().exec("python /Users/batoolalsmael/Desktop/SW#2/gateway/diagnostics.py");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String ret = in.readLine();
			String temp="";
			int count=0;
			while(ret != null)
			{
				
				//System.out.println(""+ ret);
				if(count%2 == 0 & count != 0)
				{
					temp+="&";
				}
				if(count%2 != 0 )
				{
					temp+="=";
				}
				temp+=ret;
				ret = in.readLine();
			
				count++;
			}
			
			urlParameters= temp;
			//System.out.println(""+ urlParameters);
			http_requests.Requests.sendPost(urlParameters);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
