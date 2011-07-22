package com.infine.montecarlo;

/**
 *
 * @author Florian Boulay, Nicolas Lecrique
 */
public class AsianPricer {

    public static void Price(AsianOption[] asianOptions, double vol, double spot, double rate, int nbIterations) {
        GeometricBrownianMotion brownian = new GeometricBrownianMotion(spot, vol, rate);
        int nbDaysToSimulate = findMaxExpiry(asianOptions);

//        Object[] lockOptions = new Object[asianOptions.length];
        for (int i = 0; i < nbIterations ; i++) {
            double[] underlyingPath = brownian.GeneratePath(nbDaysToSimulate);
            double[] sum = new double[nbDaysToSimulate];
            sum[0] = underlyingPath[0];
            for (int iDay = 1; iDay < nbDaysToSimulate; iDay++) {
                sum[iDay] = sum[iDay - 1] + underlyingPath[iDay];
            }
            for (int iOption = 0; iOption < asianOptions.length; iOption++) {
                AsianOption asianOption = asianOptions[iOption];
                int startConstatation = asianOption.getStartConstatation();
                int expiry = asianOption.getExpiry();
                double average = (sum[expiry] - sum[startConstatation]) / (expiry - startConstatation);
//                synchronized (lockOptions[iOption]) {
                    // this operation has to be locked because of concurrent write access
                    //to ComputedPrice, += operation on doubles is not an atomic operation
                    asianOption.setComputedPrice(asianOption.getComputedPrice() + asianOption.getPayOff(average));
//                }
            }
        }

       for (int iOption = 0; iOption < asianOptions.length; iOption++) {
            AsianOption asianOption = asianOptions[iOption];
            asianOption.setComputedPrice(asianOption.getComputedPrice() * Math.exp(-rate * asianOption.getExpiry() / 252d) / nbIterations);
        }
    }

    private static int findMaxExpiry(AsianOption[] asianOptions) {
        int max = 0;

        for (AsianOption asianOption : asianOptions) {
            if (asianOption.getExpiry() + 1 > max) {
                max = asianOption.getExpiry() + 1;
            }
        }

        return max;
    }
}
