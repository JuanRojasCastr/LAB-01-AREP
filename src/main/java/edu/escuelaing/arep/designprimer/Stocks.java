package edu.escuelaing.arep.designprimer;

import org.apache.commons.lang3.time.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stocks {

    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     *
     * @param name name of the equity to search
     * @param provider number of the service provider
     * @param time temporal resolution of the equity
     * @return returns historical intraday stocks
     * @throws IOException
     */
    public static String getStock(String name, String time, int provider) throws IOException {


        ServiceConfig config = new ServiceConfig();
        String GET_URL = config.getService(provider);

        String tempURL = GET_URL.replace("$NAME", name);

        if (provider == 1) {
            Date date = DateUtils.addDays(new Date(), -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(sdf.format(date));
            tempURL = tempURL.replace("$DATE", sdf.format(date));
        }
        else if (provider == 0) {
            tempURL = tempURL.replace("$TIME", time);
        }

        URL obj = new URL(tempURL);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            System.out.println("GET request not worked: " + con.toString());
        }
        System.out.println("GET DONE");

        return "Wrong name";
    }

}
