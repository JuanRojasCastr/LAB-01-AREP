package edu.escuelaing.arep.designprimer;
import static spark.Spark.*;

public class SparkWebApp {

    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        get("/hello", (req, res) -> {
            String resp = req.queryParams("name");
            return "Hello " + resp;
        });
        get("/stock", (req,res) -> {
            String name = req.queryParams("name");
            return Stocks.getStock(name);
        });
    }

    /**
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