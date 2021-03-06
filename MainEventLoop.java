import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import java.util.Calendar;

public class MainEventLoop {

    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args)
    {
        JSONObject jsonObjectForToken=null;
        String gwId="";
        File file = new File("gatewayID.txt");
        while(jsonObjectForToken==null && file.length() == 0 )
        {
            //register GatewayId and get the token back

            if (file.length() == 0) {

                Scanner reader = new Scanner(System.in);
                System.out.println("Enter your Gateway ID: ");
                gwId=reader.nextLine();

                JSONObject urlParametersJson = new JSONObject();
                urlParametersJson.put("GatewayId",gwId);
                String url = "https://team12.staging.softwareengineeringii.com/api/gateway/newGateway";
                String response;
                try {
                    response = Requests.sendPost(url, urlParametersJson);
                    System.out.println("response for initilizing gw id  "+response);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
                    jsonObjectForToken = (JSONObject) jsonObject.get("Gateway");
                    if(jsonObjectForToken!=null)
                    {
                        writeToFileGwIdAndToken(gwId,jsonObjectForToken);
                    }
                    if(jsonObjectForToken==null)
                    {
                        System.out.println("--Error-- Gateway is not found!! ");
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }


        
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
                String url = "https://team12.staging.softwareengineeringii.com/api/gateway/heartbeat/"+ReadPython.readGatewayControllerID();

                try
                {
                    //System.out.println(urlParametersJson.toString());
                    String response = Requests.sendPost(url, urlParametersJson);
                    System.out.println("response for heartbeat"+response.toString());
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(response);

                    // ON DEMAND DIAGNOSTIC
                    onDemandDiagnostics (jsonObject);

                    // DALIY DIAGNOSTIC
                    dailyDiagnostics (jsonObject);

                    // check if there is daily diagnostics I need to run
                    checkForDailyDiagnostics ();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public static  void  writeToFileGwIdAndToken(String gwId, JSONObject jsonObjectForToken)
    {
        try {
            //5bf08ff7a19877292859d0c3
            // write gwId to file
            try {
                File gwIdFile = new File("gatewayID.txt");
                FileWriter fileWriter = new FileWriter(gwIdFile);
                fileWriter.write(gwId);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String token = (String)jsonObjectForToken.get("token");
            try {
                File tokenFile = new File("token.txt");
                FileWriter fileWriter = new FileWriter(tokenFile);
                fileWriter.write(token);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void onDemandDiagnostics ( JSONObject jsonObject)
    {
        try
        {
            JSONArray jsonArray = (JSONArray) jsonObject.get("odd");
            //System.out.println("jsonArrayForOndemand "+jsonArray);
            if(jsonArray.size()!=0)
            {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject onDemandType=(JSONObject)jsonArray.get(i);
                    String odd= (String)onDemandType.get("ODD");

                    if("cpuCount.py".equals(odd)||"freeMem.py".equals(odd)||"battery.py".equals(odd)||"availableMem.py".equals(odd)||"osTest.py".equals(odd))
                    {
                        JSONObject urlParametersJson = null;
                        urlParametersJson= ReadPython.readPython(odd);
                        //System.out.println(urlParametersJson.toString());
                        String url="https://team12.staging.softwareengineeringii.com/api/gateway/diagnostic/test";
                        String response = Requests.sendPost(url, urlParametersJson);
                        System.out.println("response for onDemand "+response.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File dailyDiagnosticsFile = new File("dailyDiagnostics.txt");
    private static File dDTFile = new File("DDT.txt");
    public static  void dailyDiagnostics ( JSONObject jsonObject)
    {
        try
        {
            JSONArray jsonArrayForDaliy = (JSONArray) jsonObject.get("ddd");
            //System.out.println("jsonArrayForDaliy "+jsonArrayForDaliy);
            try {
                if(jsonArrayForDaliy.size()!=0)
                {
                    FileWriter fileWriter = new FileWriter(dailyDiagnosticsFile.getAbsoluteFile(),true);
                    for (int i = 0; i < jsonArrayForDaliy.size(); i++) {
                        JSONObject dailyType=(JSONObject)jsonArrayForDaliy.get(i);
                        String dd= (String)dailyType.get("DDD");
                        fileWriter.write(dd + "\n");

                        String dailyHour= (String)dailyType.get("dailyHour");
                        String dailyMin= (String)dailyType.get("dailyMin");
                        String dailySecond= (String)dailyType.get("dailySecond");

                        
                        FileWriter fileWriterddt = new FileWriter(dDTFile.getAbsoluteFile(),true);
                        fileWriterddt.write((dailyHour)+"\n");
                        fileWriterddt.write((dailyMin)+"\n");
                        fileWriterddt.write((dailySecond)+"\n");
                        fileWriterddt.flush();
                        fileWriterddt.close();
                    }
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void checkForDailyDiagnostics ( ) throws IOException
    {
        String filename = "DDT.txt";
        Scanner inputFile = null;
        int hour=0;
        int min=0;
        int sec=0;
        int count = 0;
        //read the value from the file
        try 
        {
            inputFile = new Scanner(new File(filename));
        }catch(FileNotFoundException e)
        {
            System.out.println("FAILURE cannot open file: " + filename + " for input" +
                    " EXIT ON FAILURE TO OPEN FILE.");
            System.exit(0);
        }
        
        FileInputStream fstream = new FileInputStream("dailyDiagnostics.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;

	      while ((strLine = br.readLine()) != null)   {

	        //System.out.println (strLine);
	        if (dDTFile.length() != 0 && dailyDiagnosticsFile.length()!= 0 )
	        {
	        		filename = "DDT.txt";
	            if(inputFile.hasNextInt())
	            {
	                hour = inputFile.nextInt();
	            }
	            if(inputFile.hasNextInt())
	            {
	                min = inputFile.nextInt();
	            }
	            if(inputFile.hasNextInt())
	            {
	                sec = inputFile.nextInt();
	            }
	            //inputFile.close();
	            count++;
	        
	            Calendar cal = Calendar.getInstance();
	            if(cal.get(Calendar.HOUR_OF_DAY) == hour
	            		&& cal.get(Calendar.MINUTE) == min
	            		&& cal.get(Calendar.SECOND) == sec )
	            {
	            		String dd = Files.readAllLines(Paths.get("dailyDiagnostics.txt")).get(count-1);
	                JSONObject urlParametersJson = new JSONObject();
	                String response="";
	                String url="";
	                try
	                {
	                		if("cpuCount.py".equals(dd)||"freeMem.py".equals(dd)||"battery.py".equals(dd)||"availableMem.py".equals(dd) ||"osTest.py".equals(dd))
	                        {
	                            //System.out.println("dd  in check daily :: "+dd);
	                            urlParametersJson= ReadPython.readPython(dd);
	                            //System.out.println(urlParametersJson.toString());
	                            url="https://team12.staging.softwareengineeringii.com/api/gateway/dailyDiagnostic/test";
	                            response = Requests.sendPost(url, urlParametersJson);
	                            System.out.println("response for daily "+response.toString());
	                        }
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }



	        }
	      }
	      br.close();

    }


}
