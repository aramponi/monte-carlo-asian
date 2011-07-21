/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infine.montecarlo;

import java.util.Random;

/**
 * Generate series of geometric brownian motions using sigma and drift on an annual basis
 * 
 * @author Florian Boulay, Nicolas Lecrique
 */
public class NormalDistributionGenerator {

    private Random uniformGenerator = new Random();
    private double spare;
    private boolean spareComputed;

    public double get() {
        if (spareComputed) {
            spareComputed = false;
            return spare;
        }

        double s, u, v;
        do {
            u = uniformGenerator.nextDouble() * 2 - 1;
            v = uniformGenerator.nextDouble() * 2 - 1;
            s = u * u + v * v;
        } while (s >= 1 || s == 0);

        spare = v * Math.sqrt(-2 * Math.log(s) / s);
        spareComputed = true;
        double result = u * Math.sqrt(-2 * Math.log(s) / s);
        return result;
    }
}
