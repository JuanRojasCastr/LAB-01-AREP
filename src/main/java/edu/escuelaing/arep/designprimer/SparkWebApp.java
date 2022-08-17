package edu.escuelaing.arep.designprimer;
import static spark.Spark.*;

public class SparkWebApp {

    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        get("/stock", (req,res) -> {
            String name = req.queryParams("name");
            String time = req.queryParams("time");
            int provider = Integer.parseInt(req.queryParams("provider"));
            return Stocks.getStock(name, time, provider);
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