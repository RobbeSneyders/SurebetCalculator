package com.bargaista.surebetcalculator.Calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Robbe on 31/08/2016.
 */
public class Calculator {

    public static String calculatePercentage(double odd1, double odd2, double odd3) {
        double result = ((odd2/((odd2/odd1)+1+(odd2/odd3)))-1)*100;
        result = Math.round(result * 100);
        result = result/100;
        return String.valueOf(result) + "%";
    }

    public static List<Double> calculateStakes(List<Double> odds, double stake1) {
        double odd1 = odds.get(0);
        double odd2 = odds.get(1);
        double odd3 = odds.get(2);

        double stake2 = (stake1*odd1)/odd2;
        double stake3 = (stake1*odd1)/odd3;
        double total = stake1 + stake2 + stake3;
        double profit = (stake1*odd1)-total;

        return Arrays.asList(stake1, stake2, stake3, total, profit);
    }

}
