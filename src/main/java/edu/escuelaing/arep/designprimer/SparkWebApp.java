package edu.escuelaing.arep.designprimer;
import edu.escuelaing.arep.concurrencytest.RequestThread;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

public class SparkWebApp {

    private static HashMap cache = new HashMap();

    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");

        Cleaner cleaner = new Cleaner(cache);

        cleaner.start();

        get("/stock", (req,res) -> {

            String name = req.queryParams("name");
            String time = req.queryParams("time");
            int provider = Integer.parseInt(req.queryParams("provider"));

            String name1 = name + provider;
            String name2 = name1 + time;

            if (cache.containsKey(name1)) {
                System.out.println("CACHE");
                return  cache.get(name1);
            }
            else if (cache.containsKey(name2)) {
                System.out.println("CACHE");
                return  cache.get(name2);
            }
            else {
                String resp = Stocks.getStock(name, time, provider);
                if (provider == 1){
                     cache.put(name1, resp);
                }
                else if (provider == 0) cache.put(name2, resp);
                return resp;
            }
        });
    }

    /**-
     *
     * @return default port if heroku-port isn't set
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;

    }
}