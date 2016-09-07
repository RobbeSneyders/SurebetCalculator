package com.bargaista.surebetcalculator.Calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Robbe on 31/08/2016.
 */
public class CalculatorPresenter implements CalculatorContract.Presenter {

    private final CalculatorContract.OddsView oddsFragment;
    private final CalculatorContract.StakesView stakesFragment;

    public CalculatorPresenter(CalculatorContract.OddsView oddsFragment, CalculatorContract.StakesView stakesFragment) {
        this.oddsFragment = oddsFragment;
        this.stakesFragment = stakesFragment;
        oddsFragment.setPresenter(this);
        stakesFragment.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void calculatePercentage(String leftOdd, String middleOdd, String rightOdd) {
        String percentage = "";
        if ((leftOdd.length() != 0) && (middleOdd.length() != 0) && (rightOdd.length() != 0)) {
            double odd1 = Double.parseDouble(leftOdd);
            double odd2 = Double.parseDouble(middleOdd);
            double odd3 = Double.parseDouble(rightOdd);
            percentage = Calculator.calculatePercentage(odd1, odd2, odd3);
        }
        oddsFragment.showPercentage(percentage);
    }


    @Override
    public void calculateStakes(double stake, int whichOne) {
        List<String> odds = oddsFragment.getOdds();
        List<Double> parsedOdds = new ArrayList<>();
        for (String odd : odds) {
            parsedOdds.add(Double.valueOf(odd));
        }
        Collections.rotate(parsedOdds, whichOne);
        stakesFragment.setStakes(Calculator.calculateStakes(parsedOdds, stake));
    }
}
