/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infine.montecarlo;

/**
 *
 * @author Florian Boulay, Nicolas Lecrique
 */
public class AsianCall extends AsianOption {

    public AsianCall(int startConstatation, int expiry, double strike) {
        super(startConstatation, expiry, strike);
    }

    public double getPayOff(double averageUnderlyingPrice) {
        return Math.max(averageUnderlyingPrice - strike, 0);
    }
}
