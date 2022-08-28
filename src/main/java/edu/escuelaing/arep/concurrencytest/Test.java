package edu.escuelaing.arep.concurrencytest;

import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws IOException {
        int nThreads = 5; // Alpha Vantage standard API call frequency is 5 calls per minute

        Date date = DateUtils.addDays(new Date(), -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<RequestThread> concurrencyConect = new ArrayList<>();
        for (int i = 0; i< nThreads; i++) {
            concurrencyConect.add(new RequestThread());
        }
        for (int i = 0; i< nThreads; i++) {
            concurrencyConect.get(i).threadStart();
            concurrencyConect.get(i).threadJoin();
        }
        int okRequest = 0;
        for (int i = 0; i< nThreads; i++) {
            if (concurrencyConect.get(i).getResponse().equals(sdf.format(date))) {
                okRequest++;
            }
        }
        if (okRequest == nThreads) {
            System.out.println("Respuesta correcta");
        }
        else System.out.println("Something went wrong, maybe api calls per minute reached");
    }
}
