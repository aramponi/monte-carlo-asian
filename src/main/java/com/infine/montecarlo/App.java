package com.infine.montecarlo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Boulay, Nicolas Lecrique
 */
public class App {
    
    private static final int NB_OPTIONS = 2000;
    

    public static void main(String[] args) {
        AsianOption[] options = GetOptionsSet();

        System.out.println("Starting princing");
        long startTime = System.nanoTime();
        AsianPricer.Price(options, 0.2, 200, 0.07, 500);
        long time = System.nanoTime() - startTime;

//        for (AsianOption opt : options) {
//            System.out.printf("strike:%f,start:%d,expiry:%d,Prix:%f\n", opt.getStrike(), opt.getStartConstatation(), opt.getExpiry(), opt.getComputedPrice());
//        }
        System.out.printf("elapsed time for %d options : %.3f seconds\n", NB_OPTIONS, time / 1000000000d);

    }

    private static AsianOption[] GetOptionsSet() {
        System.out.println("Generating options...");
        List<AsianOption> options = new ArrayList<AsianOption>(NB_OPTIONS);
        for (int iStartDate = 0; iStartDate < 10; iStartDate++) {
            for (int iExpiry = 0; iExpiry < 10; iExpiry++) {
                for (int iStrike = 0; iStrike < 20; iStrike++) {
                    int startConstatation = iStartDate * 100;
                    int expiry = startConstatation + iExpiry * 100;
                    double strike = 100 + iStrike * 10;
                    options.add(new AsianCall(startConstatation, expiry, strike));
                    options.add(new AsianPut(startConstatation, expiry, strike));
                }
            }
        }
        System.out.println("Generating options... done");
        return options.toArray(new AsianOption[options.size()]);
    }
}
