package readPythonCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ReadPython {
	public static void readPython()
	{
		
		try{
			String urlParameters ="";
			//reading GWId file
			String temp="";
			Process pGwId = Runtime.getRuntime().exec("python /Users/batoolalsmael/Desktop/SW#2/gateway/gwId.py");
			BufferedReader inGateway = new BufferedReader(new InputStreamReader(pGwId.getInputStream()));
			
			temp+= inGateway.readLine();
			temp+="=";
			temp+= inGateway.readLine();
			
			//reading diagnostics file 
			Process pDiagnostics = Runtime.getRuntime().exec("python /Users/batoolalsmael/Desktop/SW#2/gateway/diagnostics.py");
			BufferedReader inDiagnostics = new BufferedReader(new InputStreamReader(pDiagnostics.getInputStream()));
			
			String reDiagnosticst = inDiagnostics.readLine();
			
			int count=0;
			while(reDiagnosticst != null)
			{
				if(count%2 == 0)
				{
					temp+="&";
				}
				if(count%2 != 0 )
				{
					temp+="=";
				}
				temp+=reDiagnosticst;
				reDiagnosticst = inDiagnostics.readLine();
			
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
