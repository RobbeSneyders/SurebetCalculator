package com.bargaista.surebetcalculator.Calculator;

/**
 * Extension of BaseCalculator for triple type bet
 */
public class TripleCalculator extends BaseCalculator {

    public TripleCalculator() {
        rounded = new boolean[]{false, false, false};
        profitWanted = new boolean[]{true, true, true};
        distribution = EQUAL;
    }

    @Override
    public double calculatePercentage() {
        double stake1 = 1;
        double stake2 = stake1*odds[0]/odds[1];
        double stake3 = stake1*odds[0]/odds[2];
        double total = stake1 + stake2 + stake3;
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
        double odd3 = odds[2];

        double stake2;
        double stake3;
        double total;

        if ((profitWanted[0] && profitWanted[1] && profitWanted[2]) || (!profitWanted[0] && !profitWanted[1] && !profitWanted[2])) {
            if (distribution == EQUAL) {
                stake2 = (stake1 * odd1) / odd2;
                stake3 = (stake1 * odd1) / odd3;
                total = stake1 + stake2 + stake3;
            }
            else {
                total = stake1 / ((((odd2 * odd2) * (odd3 * odd3)) - ((odd2 - odd1) * (odd3 * odd3)) - ((odd3 - odd1) * (odd2 * odd2))) / (((odd1 * odd1) * (odd2 * odd2)) + ((odd2 * odd2) * (odd3 * odd3)) + ((odd1 * odd1) * (odd3 * odd3))));
                stake2 = total * ((((odd1 * odd1) * (odd3 * odd3)) - ((odd1 - odd2) * (odd3 * odd3)) - ((odd3 - odd2) * (odd1 * odd1))) / (((odd1 * odd1) * (odd2 * odd2)) + ((odd2 * odd2) * (odd3 * odd3)) + ((odd1 * odd1) * (odd3 * odd3))));
                stake3 = total - stake1 - stake2;
            }
        }
        else if (profitWanted[0] && !profitWanted[1] && !profitWanted[2]) {
            stake2 = stake1/(odd2-1-(odd2/odd3));
            stake3 = stake2*odd2/odd3;
            total = stake1 + stake2 + stake3;
        }
        else if (!profitWanted[0] && profitWanted[1] && !profitWanted[2]) {
            total = stake1 * odd1;
            stake3 = total / odd3;
            stake2 = total - stake1 - stake3;
        }
        else if (!profitWanted[0] && !profitWanted[1] && profitWanted[2]) {
            total = stake1 * odd1;
            stake2 = total / odd2;
            stake3 = total - stake1 - stake2;
        }
        else if (profitWanted[0] && profitWanted[1] && !profitWanted[2]) {
            if (distribution == EQUAL) {
                stake2 = stake1 * odd1 / odd2;
                stake3 = stake1 * (1 + odd1 / odd2) / (odd3 - 1);
            }
            else {
                double alpha = (odd2 - odd1) * odd3 / (odd3 - 1);
                stake2 = stake1 * (odd1*odd1 + alpha)/(odd2*odd2 - alpha);
                stake3 = (stake1 + stake2) / (odd3 - 1);
            }
            total = stake1 + stake2 + stake3;
        }
        else if (profitWanted[0] && !profitWanted[1] && profitWanted[2]) {
            if (distribution == EQUAL) {
                stake2 = stake1 * (1 + odd1/odd3) / (odd2 - 1);
                stake3 = stake1 * odd1 / odd3;
            }
            else {
                double alpha = (odd3 - odd1) * odd2 / (odd2 - 1);
                stake3 = stake1 * (odd1*odd1 + alpha)/(odd3*odd3 - alpha);
                stake2 = (stake1 + stake3) / (odd2 - 1);
            }
            total = stake1 + stake2 + stake3;
        }
        else { //if (!profitWanted[0] && profitWanted[1] && profitWanted[2]) {
            total = stake1 * odd1;
            if (distribution == EQUAL)
                stake2 = (total - stake1) * (odd3/(odd2+odd3));
            else
                stake2 = (total * (odd3 * odd3 - odd3 + odd2) - stake1 * odd3 * odd3) /(odd2 * odd2 + odd3 * odd3);
            stake3 = total - stake1 - stake2;
        }

        double[] stakes = new double[]{stake1, stake2, stake3};

        double[] profit = new double[3];
        profit[0] = (stake1*odd1) - total;
        profit[1] = (stake2*odd2) - total;
        profit[2] = (stake3*odd3) - total;

        if (n != 0) {
            for (int i = 0; i < 3; i++) {
                if (rounded[i]) {
                    if (profit[i] > (2 - ALPHA) * mean(profit))
                        stakes[i] = n * Math.floor(stakes[i] / n);
                    else if (profit[i] < ALPHA * mean(profit))
                        stakes[i] = n * Math.ceil(stakes[i] / n);
                    else
                        stakes[i] = n * Math.round(stakes[i] / n);
                }
            }

            total = stakes[0] + stakes[1] + stakes[2];
            profit[0] = (stakes[0] * odd1) - total;
            profit[1] = (stakes[1] * odd2) - total;
            profit[2] = (stakes[2] * odd3) - total;
        }

        stakes = rotateArray(stakes, whichOne);

        double[] totals = new double[]{total};

        return new double[][]{stakes, totals, profit};
    }

    @Override
    public double[][] calculateFromTotal(double total) {
        double odd1 = odds[0];
        double odd2 = odds[1];
        double odd3 = odds[2];

        double stake1;
        double stake2;
        double stake3;

        if ((profitWanted[0] && profitWanted[1] && profitWanted[2]) || (!profitWanted[0] && !profitWanted[1] && !profitWanted[2])) {
            double percentage = calculatePercentage()/100;
            if (distribution == EQUAL) {
                stake1 = total*(1+percentage)/odd1;
                stake2 = total*(1+percentage)/odd2;
            }
            else {
                stake1 = (total + total * percentage * ((odd2 * odd2 + odd3 * odd3) / (odd1 * odd1 + odd2 * odd2 + odd3 * odd3)) * ((odd1 + odd2 + odd3) / (odd2 + odd3)))/odd1;
                stake2 = (total + total * percentage * ((odd1 * odd1 + odd3 * odd3) / (odd2 * odd2 + odd1 * odd1 + odd3 * odd3)) * ((odd2 + odd1 + odd3) / (odd1 + odd3)))/odd2;
            }
            stake3 = total - stake1 - stake2;
        }
        else if (profitWanted[0] && !profitWanted[1] && !profitWanted[2]) {
            stake2 = total/odd2;
            stake3 = total/odd3;
            stake1 = total - stake2 - stake3;
        }
        else if (!profitWanted[0] && profitWanted[1] && !profitWanted[2]) {
            stake1 = total/odd1;
            stake3 = total/odd3;
            stake2 = total - stake1 - stake3;
        }
        else if (!profitWanted[0] && !profitWanted[1] && profitWanted[2]) {
            stake1 = total/odd1;
            stake2 = total/odd2;
            stake3 = total - stake1 - stake2;
        }
        else if (profitWanted[0] && profitWanted[1] && !profitWanted[2]) {
            stake3 = total/odd3;
            if (distribution == EQUAL)
                stake1 = (total - stake3) * odd1/(1 + odd1/odd2) / odd1;
            else
                stake1 = (total * (odd2 * odd2 - odd2 + odd1) - stake3 * odd2 * odd2) /(odd1 * odd1 + odd2 * odd2);
            stake2 = total - stake1 - stake3;
        }
        else if (profitWanted[0] && !profitWanted[1] && profitWanted[2]) {
            stake2 = total/odd2;
            if (distribution == EQUAL)
                stake1 = (total - stake2) * odd1/(1 + odd1/odd3) / odd1;
            else
                stake1 = (total * (odd3 * odd3 - odd3 + odd1) - stake2 * odd3 * odd3) /(odd1 * odd1 + odd3 * odd3);
            stake3 = total - stake1 - stake2;
        }
        else { //if (!profitWanted[0] && profitWanted[1] && profitWanted[2]) {
            stake1 = total/odd1;
            if (distribution == EQUAL)
                stake2 = (total - stake1) * odd2/(1 + odd2/odd3) / odd2;
            else
                stake2 = (total * (odd3 * odd3 - odd3 + odd2) - stake1 * odd3 * odd3) /(odd2 * odd2 + odd3 * odd3);
            stake3 = total - stake1 - stake2;
        }

        double[] stakes = new double[]{stake1, stake2, stake3};

        double[] profit = new double[3];
        profit[0] = (stake1*odd1) - total;
        profit[1] = (stake2*odd2) - total;
        profit[2] = (stake3*odd3) - total;

        if (n != 0) {
            for (int i = 0; i < 3; i++) {
                if (rounded[i]) {
                    if (profit[i] > (2 - ALPHA) * mean(profit))
                        stakes[i] = n * Math.floor(stakes[i] / n);
                    else if (profit[i] < ALPHA * mean(profit))
                        stakes[i] = n * Math.ceil(stakes[i] / n);
                    else
                        stakes[i] = n * Math.round(stakes[i] / n);
                }
            }

            total = stakes[0] + stakes[1] + stakes[2];
            profit[0] = (stakes[0] * odd1) - total;
            profit[1] = (stakes[1] * odd2) - total;
            profit[2] = (stakes[2] * odd3) - total;
        }

        double[] totals = new double[]{total};

        return new double[][]{stakes, totals, profit};
    }
}
