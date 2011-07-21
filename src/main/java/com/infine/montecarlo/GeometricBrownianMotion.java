/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infine.montecarlo;

/**
 *  
 * Generate value following a standard normal distribution ( μ = 0 and σ^2 = 1)
 * Use box-muller algorithm in polar form
 * The spare aims to not to waste random generated values (improves entropy)
 * Nb : could be done more efficiently but in a far more complex way with the Ziggurat algorithm
 * 
 * @author Florian Boulay, Nicolas Lecrique
 */
public class GeometricBrownianMotion {

    private static final ThreadLocal<NormalDistributionGenerator> generator;
    private double dailySigma;
    private double dailyDrift;
    private double startSpot;

    static {
        generator = new ThreadLocal<NormalDistributionGenerator>();
        generator.set(new NormalDistributionGenerator());
    }

    public GeometricBrownianMotion(double startSpot, double sigma, double drift) {
        this.dailySigma = sigma * Math.sqrt(1 / (double) 252);
        this.dailyDrift = (drift - sigma * sigma / 2d) / 252d;
        this.startSpot = startSpot;
    }

    public double[] GeneratePath(int nbDays) {
        double[] path = new double[nbDays];
        double currentSpot = startSpot;
        for (int i = 0; i < nbDays; i++) {
            currentSpot *= Math.exp(dailyDrift + dailySigma * generator.get().get());
            path[i] = currentSpot;
        }
        return path;
    }
}
