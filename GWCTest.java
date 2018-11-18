import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;



public class GWCTest {

        @Test
        public void test() {
            System.out.println("\n------ Testing Gateway Controller... ------");



            System.out.println("\n------ Testing Initializing Gateway ------");
            JSONObject jsonObjectForToken=null;
            File file = new File("gatewayID.txt");
            JSONObject jsonObject=null;
            String response="";
            while(jsonObjectForToken==null && file.length() == 0 )
            {
                //register GatewayId and get the token back

                if (file.length() == 0) {

                    Scanner reader = new Scanner(System.in);
                    System.out.println("Enter your Gateway ID: ");
                    String gwId=reader.nextLine();

                    JSONObject urlParametersJson = new JSONObject();
                    //String gwId="5bf08ff7a19877292859d0c3";
                    urlParametersJson.put("GatewayId",gwId);

                    //System.out.println("your json is : "+urlParametersJson);
                    String url = "https://team12.dev.softwareengineeringii.com/api/gateway/newGateway";

                    try {
                        response = Requests.sendPost(url, urlParametersJson);
                        //System.out.println("response"+response);
                        JSONParser jsonParser = new JSONParser();
                        jsonObject = (JSONObject) jsonParser.parse(response);
                        jsonObjectForToken = (JSONObject) jsonObject.get("Gateway");
                        if(jsonObjectForToken!=null)
                        {
                            MainEventLoop.writeToFileGwIdAndToken(gwId,jsonObjectForToken);
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
            assertFalse(jsonObjectForToken.equals(null));
            System.out.println(" GW  is not null");
            String message= (String)jsonObject.get("message");
            assertEquals(message, "Gateway initialized successfully");
            System.out.println("response for initializing GW "+response);



            JSONObject urlParametersJson = null;
            try
            {
                urlParametersJson=ReadPython.readPython();
            } catch (IOException | ParseException e1)
            {
                e1.printStackTrace();
            }
            //System.out.println(urlParametersJson.toString());
            String url = "https://team12.dev.softwareengineeringii.com/api/gateway/heartbeat/"+ReadPython.reeadGatewayControllerID();

            try
            {
                response = Requests.sendPost(url, urlParametersJson);
                JSONParser jsonParser = new JSONParser();
                jsonObject = (JSONObject) jsonParser.parse(response);
                message = (String)jsonObject.get("message");
                System.out.println("\n------ Testing heartbeat ------");
                JSONObject postedHeartbeat = (JSONObject) jsonObject.get("postedHeartbeat");
                assertFalse(postedHeartbeat.equals(null));
                System.out.println(" postedHeartbeat  is not null");
                System.out.println("response for heartbeat"+response.toString());
                assertEquals(message, "Success");

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



        public static  void  writeToFileGwIdAndToken(String gwId, JSONObject jsonObjectForToken)
        {
            try {
                //5bde7e392c7ac54bbdf5bbaa
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
                System.out.println("\n------ Testing On Demand Diagnostic ------");
                JSONArray jsonArray = (JSONArray) jsonObject.get("odd");
                System.out.println("jsonArrayForOndemand "+jsonArray);
                if(jsonArray.size()!=0)
                {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject onDemandType=(JSONObject)jsonArray.get(i);
                        String odd= (String)onDemandType.get("ODD");

                        if("cpuCount.py".equals(odd)||"freeMem.py".equals(odd)||"battery.py".equals(odd)||"availableMem.py".equals(odd) ||"osTest.py".equals(odd))
                        {
                            //System.out.println("odd  :: "+odd);
                            JSONObject urlParametersJson = null;

                            urlParametersJson= ReadPython.readPython(odd);
                            System.out.println(urlParametersJson.toString());
                            String url="https://team12.dev.softwareengineeringii.com/api/gateway/diagnostic/test";
                            String response = Requests.sendPost(url, urlParametersJson);

                            JSONParser jsonParser = new JSONParser();
                            jsonObject = (JSONObject) jsonParser.parse(response);
                            String message = (String)jsonObject.get("message");

                            assertEquals(message, "POST Success");
                            System.out.println("response for onDemand "+response.toString());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public static  void dailyDiagnostics ( JSONObject jsonObject)
        {
            System.out.println("\n------ Testing Daily Diagnostic ------");
            try
            {
                JSONArray jsonArrayForDaliy = (JSONArray) jsonObject.get("ddd");
                System.out.println("jsonArrayForDaliy "+jsonArrayForDaliy);
                try {
                    if(jsonArrayForDaliy.size()!=0)
                    {

                        File dailyDiagnosticsFile = new File("dailyDiagnostics.txt");
                        //File dailyTimeFile = new File("DDT.txt");
                        FileWriter fileWriter = new FileWriter(dailyDiagnosticsFile);
                        for (int i = 0; i < jsonArrayForDaliy.size(); i++) {
                            JSONObject dailyType=(JSONObject)jsonArrayForDaliy.get(i);
                            String dd= (String)dailyType.get("DDD");
                            //System.out.println("daily "+dd);
                            fileWriter.write(dd + "\n");

                            String dailyHour= (String)dailyType.get("dailyHour");
                            String dailyMin= (String)dailyType.get("dailyMin");
                            String dailySecond= (String)dailyType.get("dailySecond");

                            File dDTFile = new File("DDT.txt");
                            FileWriter fileWriterddt = new FileWriter(dDTFile);
                            // System.out.println(" time "+dailyHour+":"+dailyMin);
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

        public static  void checkForDailyDiagnostics ( )
        {


            String filename = "DDT.txt";
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

                if(inputFile.hasNextInt())
                {
                    hour = inputFile.nextInt();
                    //System.out.println("H "+hour);
                }
                if(inputFile.hasNextInt())
                {
                    min = inputFile.nextInt();
                    //System.out.println("M "+min);
                }
                if(inputFile.hasNextInt())
                {
                    sec = inputFile.nextInt();
                    //System.out.println("S "+sec);
                }
                inputFile.close();


                Calendar cal = Calendar.getInstance();

                // && cal.get(Calendar.SECOND) == sec
                if(cal.get(Calendar.HOUR_OF_DAY) == hour && cal.get(Calendar.MINUTE) == min )
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

                            if("cpuCount.py".equals(dd)||"freeMem.py".equals(dd)||"battery.py".equals(dd)||"availableMem.py".equals(dd) ||"osTest.py".equals(dd))
                            {
                                //System.out.println("dd  :: "+dd);
                                urlParametersJson= ReadPython.readPython(dd);
                                System.out.println(urlParametersJson.toString());
                                url="https://team12.dev.softwareengineeringii.com/api/gateway/dailyDiagnostic/test";
                                response = Requests.sendPost(url, urlParametersJson);


                                JSONParser jsonParser = new JSONParser();
                                JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
                                String message = (String)jsonObject.get("message");

                                assertEquals(message, "POST Success");
                                System.out.println("response for daily "+response.toString());

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }
        }

}
