package edu.escuelaing.arep.designprimer;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Cleaner extends Thread{

    private HashMap cache;

    Cleaner (HashMap cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.plusMinutes(5);

        while (true) {
            now = LocalDateTime.now();
            if (now.isAfter(then)) {
                System.out.println("Cleaning cache");
                cache.clear();
                now = LocalDateTime.now();
                then = now.plusMinutes(5);
            }
        }
    }
}
