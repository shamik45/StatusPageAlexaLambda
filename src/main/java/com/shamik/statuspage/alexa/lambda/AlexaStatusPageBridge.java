package com.shamik.statuspage.alexa.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by shamik.shah on 2/22/18.
 */
public class AlexaStatusPageBridge {

    static final Logger logger = LogManager.getLogger(AlexaStatusPageBridge.class);

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {

        String kioskHostname = System.getenv("STATUS_PAGE_HOSTNAME");

        logger.debug("invoked request");

        String str;

        StringBuffer response = new StringBuffer();
        StringBuffer uresponse = new StringBuffer();
        StringBuffer buf = new StringBuffer();
        try{

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            while ((str = br.readLine()) != null) {
                buf.append(str);
            }


        } catch (Exception e){
            logger.debug(e.getMessage());
        }

        logger.debug("the input payload is " + buf.toString() );


        URL url = null;
        try {

            /*URL userUrl = new URL("http://" + kioskHostname + ":8090/setUser?user=2hamik");

            HttpURLConnection ucon = (HttpURLConnection) userUrl.openConnection();

            // optional default is GET
            ucon.setRequestMethod("GET");

            //add request header
            ucon.setRequestProperty("User-Agent", "StatusBoardLambda v1.0");

            int uresponseCode = ucon.getResponseCode();
            logger.debug("\nSending 'GET' request to URL : " + userUrl);
            logger.debug("Response Code : " + uresponseCode);


            BufferedReader uin = new BufferedReader(
                    new InputStreamReader(ucon.getInputStream()));
            String uinputLine;


            while ((uinputLine = uin.readLine()) != null)
            {
                uresponse.append(uinputLine);
            }
            uin.close();




            logger.debug("response from setUser =" + uinputLine);

            */


            url = new URL("http://" + kioskHostname + ":8090/setUserAndSlateMode?user=2hamik&mode=true");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "StatusBoardLambda v1.0");

            int responseCode = con.getResponseCode();
            logger.debug("\nSending 'GET' request to URL : " + url);
            logger.debug("Response Code : " + responseCode);


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();

            logger.debug("the response payload is " + response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
