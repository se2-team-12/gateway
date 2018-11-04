
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import java.util.Calendar;

public class MainEventLoop {

    public static void main(String[] args)
    {


        //register GatewayId and get the token back
        File file = new File("gatewayID.txt");
        if (file.length() == 0) {

            Scanner reader = new Scanner(System.in);
            System.out.println("Enter The GatewayId: ");
            String gwId=reader.nextLine();

            JSONObject urlParametersJson = new JSONObject();
            urlParametersJson.put("GatewayId",gwId);

            //System.out.println("your json is : "+urlParametersJson);
            String url = "https://team12.dev.softwareengineeringii.com/api/gateway/newGateway";
            String response;
            try {
                response = Requests.sendPost(url, urlParametersJson);
                System.out.println("response"+response);
                writeToFileGwIdAndToken(gwId,response);
            } catch (Exception e) {

                e.printStackTrace();
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
                System.out.println(urlParametersJson.toString());
                String url = "https://team12.dev.softwareengineeringii.com/api/gateway/heartbeat/"+ReadPython.reeadGatewayControllerID();

                try
                {
                    String response = Requests.sendPost(url, urlParametersJson);
                    System.out.println("response"+response);

                    // ON DEMAND DIAGNOSTIC
                    onDemandDiagnostics (response);

                    // DALIY DIAGNOSTIC
                    dailyDiagnostics (response);

                    // check if there is daily diagnostics I need to run
                    checkForDailyDiagnostics ();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
    }


    public static  void  writeToFileGwIdAndToken(String gwId, String response)
    {
        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            JSONObject jsonObjectForToken = (JSONObject) jsonObject.get("Gateway");
//			5bde7e392c7ac54bbdf5bbaa



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
            //String message = (String)jsonObject.get("message");
            //System.out.println("newGateway"+message);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static  void onDemandDiagnostics ( String response)
    {
        try
        {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            JSONArray jsonArray = (JSONArray) jsonObject.get("odd");
            if(jsonArray!=null)
            {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject onDemandType=(JSONObject)jsonArray.get(i);
                    String odd= (String)onDemandType.get("ODD");

                    if("cpuCount.py".equals(odd)||"memory.py".equals(odd)||"cpuBattery.py".equals(odd))
                    {
                        //System.out.println("odd  :: "+odd);
                        JSONObject urlParametersJson = null;

                        urlParametersJson= ReadPython.readPython(odd);
                        System.out.println(urlParametersJson.toString());
                        String url="https://team12.dev.softwareengineeringii.com/api/gateway/diagnostic/test";
                        response = Requests.sendPost(url, urlParametersJson);
                        System.out.println(Requests.sendPost(url, urlParametersJson));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static  void dailyDiagnostics ( String response)
    {
        try
        {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            JSONArray jsonArrayForDaliy = (JSONArray) jsonObject.get("ddd");
            if(jsonArrayForDaliy!=null)
            {
                File dailyDiagnosticsFile = new File("dailyDiagnostics.txt");
                FileWriter fileWriter = new FileWriter(dailyDiagnosticsFile);
                for (int i = 0; i < jsonArrayForDaliy.size(); i++) {
                    JSONObject dailyType=(JSONObject)jsonArrayForDaliy.get(i);
                    String dd= (String)dailyType.get("DDD");
                    fileWriter.write(dd + "\n");
                }
                fileWriter.close();

                JSONObject dailyHour=(JSONObject)jsonObject.get("dailyHour");
                JSONObject dailyMin=(JSONObject)jsonObject.get("dailyMin");
                JSONObject dailySecond=(JSONObject)jsonObject.get("dailySecond");

                File dailyTimeFile = new File("dailyTime.txt");
                fileWriter = new FileWriter(dailyTimeFile);
                fileWriter.write(dailyHour + "\n");
                fileWriter.write(dailyMin + "\n");
                fileWriter.write(dailySecond + "\n");
                fileWriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static  void checkForDailyDiagnostics ( )
    {
        String filename = "dailyTime.txt";
        File dailyDiagnosticsFile = new File("dailyDiagnostics.txt");
        File dailyTimeFile = new File(filename);
        if (dailyTimeFile.length() != 0 && dailyDiagnosticsFile.length()!= 0 )
        {


            Scanner inputFile = null;
            int hour=0;
            int min=0;
            int sec=0;

            //read the value from the file
            try {
                inputFile = new Scanner(new File(filename));

            }catch(FileNotFoundException e) {
                System.out.println("FAILURE cannot open file: " + filename + " for input" +
                        " EXIT ON FAILURE TO OPEN FILE.");
                System.exit(0);
            }

            if(inputFile.hasNext())
            {
                hour = inputFile.nextInt();
            }
            if(inputFile.hasNext())
            {
                min = inputFile.nextInt();
            }
            if(inputFile.hasNext())
            {
                sec = inputFile.nextInt();
            }
            inputFile.close();


            Calendar cal = Calendar.getInstance();
            System.out.println("The time is "+cal.get(Calendar.HOUR_OF_DAY)+":"
                    +cal.get(Calendar.MINUTE)+":"
                    +cal.get(Calendar.SECOND));

            if(cal.get(Calendar.HOUR_OF_DAY) == hour && cal.get(Calendar.MINUTE) == min && cal.get(Calendar.SECOND) == sec )
            {
                //read the value from the file

                filename ="dailyDiagnostics.txt";
                try {
                    inputFile = new Scanner(new File(filename));

                }catch(FileNotFoundException e) {
                    System.out.println("FAILURE cannot open file: " + filename + " for input" +
                            " EXIT ON FAILURE TO OPEN FILE.");
                    System.exit(0);
                }

                JSONObject urlParametersJson = new JSONObject();
                String response="";
                String url="";
                try
                {
                    while(inputFile.hasNextLine())
                    {
                        String dd= inputFile.nextLine().trim();

                        if("cpuCount.py".equals(dd)||"memory.py".equals(dd)||"cpuBattery.py".equals(dd))
                        {
                            System.out.println("dd  :: "+dd);
                            urlParametersJson= ReadPython.readPython(dd);
                            System.out.println(urlParametersJson.toString());
                            url="https://team12.dev.softwareengineeringii.com/api/gateway/dailyDiagnostic/test";
                            response = Requests.sendPost(url, urlParametersJson);
                            System.out.println(response);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        }
    }


}
