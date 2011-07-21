/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infine.montecarlo;

/**
 *
 * @author Florian Boulay, Nicolas Lecrique
 */
public abstract class AsianOption {

    int startConstatation;
    int expiry;
    double strike;
    private double computedPrice;

    protected AsianOption(int startConstatation, int expiry, double strike) {
        this.startConstatation = startConstatation;
        this.expiry = expiry;
        this.strike = strike;
    }

    public double getComputedPrice() {
        return computedPrice;
    }

    public void setComputedPrice(double computedPrice) {
        this.computedPrice = computedPrice;
    }

    public int getExpiry() {
        return expiry;
    }

    public int getStartConstatation() {
        return startConstatation;
    }

    public double getStrike() {
        return strike;
    }

    public abstract double getPayOff(double averageUnderlyingPrice);
}
