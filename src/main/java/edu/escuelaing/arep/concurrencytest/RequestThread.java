package edu.escuelaing.arep.concurrencytest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestThread implements Runnable {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String URL = "https://tranquil-fortress-94732.herokuapp.com/stock?name=IBM&provider=0&time=INTRADAY";
    HttpURLConnection connection;
    Thread thread;
    String response;

    public RequestThread() throws IOException {
        thread = new Thread(this, "my runnable thread");
        URL obj = new URL(URL);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

    }

    public void threadJoin() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void threadStart() {
        thread.start();
    }

    public String getResponse() {
        return response;
    }

    @Override
    public void run() {
        int responseCode = 0;
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inputLine = null;
            StringBuffer response = new StringBuffer();

            while (true) {
                try {
                    if (!((inputLine = in.readLine()) != null)) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.append(inputLine);
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.response = response.substring(162, 172);
            } catch (Exception e) {
                System.out.println("Api calls per minute reached");
            }

        }

    }
}
