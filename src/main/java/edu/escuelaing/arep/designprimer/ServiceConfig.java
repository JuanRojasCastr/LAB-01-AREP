package edu.escuelaing.arep.designprimer;

import java.util.ArrayList;
import java.util.List;

public class ServiceConfig {

    ArrayList<String> services = new ArrayList<>();

    public ServiceConfig() {
        services.add("https://www.alphavantage.co/query?function=TIME_SERIES_$TIME&symbol=$NAME&interval=60min&apikey=WBHE8DSX66S276FV");
        services.add("https://api.polygon.io/v1/open-close/$NAME/$DATE?adjusted=true&apiKey=riXIxc3wC6VFz6p8PmUyZqRVXa9RQv9b");
    }
    public String getService(int num) {
        return services.get(num);
    }
}
