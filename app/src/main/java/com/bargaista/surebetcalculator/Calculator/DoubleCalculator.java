package com.bargaista.surebetcalculator.Calculator;

import android.util.Log;

/**
 * Extension of BaseCalculator for double type of bet
 */
public class DoubleCalculator extends BaseCalculator {

    public DoubleCalculator() {
        rounded = new boolean[]{false, false};
        profitWanted = new boolean[]{true, true};
        distribution = EQUAL;
    }

    @Override
    public double calculatePercentage() {
        double stake1 = 1;
        double stake2 = stake1*odds[0]/odds[1];
        double total = stake1 + stake2;
        double factor = stake1*odds[0]/total;
        double percentage = (factor - 1)*100;
        return percentage;
    }

    @Override
    public double[][] calculateFromStake(double stake1, int whichOne) {
        double[] odds = rotateArray(this.odds, -whichOne);
        boolean[] profitWanted = rotateArray(this.profitWanted, -whichOne);
        boolean[] rounded = rotateArray(this.rounded, - whichOne);

        double odd1 = odds[0];
        double odd2 = odds[1];

        double stake2;
        double total;

        if ((profitWanted[0] && profitWanted[1]) || (!profitWanted[0] && !profitWanted[1])) {
            if (distribution == EQUAL)
                stake2 = (stake1 * odd1) / odd2;
            else
                stake2 = stake1 * (odd1*odd1 - odd1 + odd2)/(odd2*odd2 - odd2 + odd1);
            total = stake1 + stake2;
        }
        else if (profitWanted[0] && !profitWanted[1]){
            stake2 = stake1 / (odd2 - 1);
            total = stake1 + stake2;
        }
        else { // if (!profitWanted[0] && profitWanted[1]) {
            total = stake1 * odd1;
            stake2 = total - stake1;
        }

        double[] stakes = new double[]{stake1, stake2};

        double[] profit = new double[2];
        profit[0] = (stake1*odd1) - total;
        profit[1] = (stake2*odd2) - total;

        if (n != 0) {
            for (int i = 0; i < 2; i++) {
                if (rounded[i]) {
                    if (profit[i] > (2 - ALPHA) * mean(profit))
                        stakes[i] = n * Math.floor(stakes[i] / n);
                    else if (profit[i] < ALPHA * mean(profit))
                        stakes[i] = n * Math.ceil(stakes[i] / n);
                    else
                        stakes[i] = n * Math.round(stakes[i] / n);
                }
            }

            total = stakes[0] + stakes[1];
            profit[0] = (stakes[0] * odd1) - total;
            profit[1] = (stakes[1] * odd2) - total;
        }

        stakes = rotateArray(stakes, whichOne);

        double[] totals = new double[]{total};

        return new double[][]{stakes, totals, profit};
    }

    @Override
    public double[][] calculateFromTotal(double total) {
        double odd1 = odds[0];
        double odd2 = odds[1];

        double stake1;
        double stake2;

        if ((profitWanted[0] && profitWanted[1]) || (!profitWanted[0] && !profitWanted[1])) {
            if (distribution == EQUAL) {
                double percentage = calculatePercentage()/100;
                stake1 = total*(1+percentage)/odd1;
            }
            else {
                stake1 = total * (odd2 * odd2 - odd2 + odd1)/(odd1 * odd1 + odd2 * odd2);
            }
            stake2 = total - stake1;
        }
        else if (profitWanted[0] && !profitWanted[1]){
            stake2 = total/odd2;
            stake1 = total - stake2;
        }
        else { // if (!profitWanted[0] && profitWanted[1]) {
            stake1 = total/odd1;
            stake2 = total - stake1;
        }

        double[] stakes = new double[]{stake1, stake2};

        double[] profit = new double[2];
        profit[0] = (stake1*odd1) - total;
        profit[1] = (stake2*odd2) - total;

        if (n != 0) {
            for (int i = 0; i < 2; i++) {
                if (rounded[i]) {
                    Log.d(String.valueOf(profit[i]), String.valueOf((2 - ALPHA) * mean(profit)));
                    if (profit[i] > (2 - ALPHA) * mean(profit))
                        stakes[i] = n * Math.floor(stakes[i] / n);
                    else if (profit[i] < ALPHA * mean(profit))
                        stakes[i] = n * Math.ceil(stakes[i] / n);
                    else
                        stakes[i] = n * Math.round(stakes[i] / n);
                }
            }

            total = stakes[0] + stakes[1];
            profit[0] = (stakes[0] * odd1) - total;
            profit[1] = (stakes[1] * odd2) - total;
        }

        double[] totals = new double[]{total};

        return new double[][]{stakes, totals, profit};
    }
}
