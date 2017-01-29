package com.bargaista.surebetcalculator.Calculator;

/**
 * Base of calculator.
 * Defines public methods.
 * Getters and setters for all variables.
 */
public abstract class BaseCalculator {

    public static final int EQUAL = 1001;
    public static final int PROB = 1002;

    protected static final double ALPHA = 0.5; // heuristic parameter € [0, 1]

    protected boolean[] rounded;
    protected boolean[] profitWanted;
    protected double n;
    protected int distribution;
    protected static double[] odds;

    public abstract double calculatePercentage();

    public abstract double[][] calculateFromStake(double stake, int whichOne);

    public abstract double[][] calculateFromTotal(double total);

    protected static double[] rotateArray(double[] array, int n) {
        if (n < 0) n = array.length + n;
        double[] newArray = new double[array.length];
        for(int i = 0, l = array.length; i < l; i++){
            newArray[(i+n) % l] = array[i];
        }
        return newArray;
    }

    protected static boolean[] rotateArray(boolean[] array, int n) {
        if (n < 0) n = array.length + n;
        boolean[] newArray = new boolean[array.length];
        for(int i = 0, l = array.length; i < l; i++){
            newArray[(i+n) % l] = array[i];
        }
        return newArray;
    }

    protected static double mean(double[] array) {
        double sum = 0;
        for (double d: array)
            sum += d;
        return sum/array.length;
    }

    public boolean[] getRounded() {
        return rounded;
    }

    public void setRounded(boolean[] rounded) {
        this.rounded = rounded;
    }

    public boolean[] getProfitWanted() {
        return profitWanted;
    }

    public void setProfitWanted(boolean[] profitWanted) {
        this.profitWanted = profitWanted;
    }

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }

    public int getDistribution() {
        return distribution;
    }

    public void setDistribution(int distribution) {
        this.distribution = distribution;
    }

    public double[] getOdds() {
        return odds;
    }

    public void setOdds(double[] odds) {
        BaseCalculator.odds = odds;
    }
}
