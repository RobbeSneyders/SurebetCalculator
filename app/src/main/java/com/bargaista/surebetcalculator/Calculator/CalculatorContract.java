package com.bargaista.surebetcalculator.Calculator;

import com.bargaista.surebetcalculator.BasePresenter;
import com.bargaista.surebetcalculator.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbe on 31/08/2016.
 */
public interface CalculatorContract {

    interface OddsView extends BaseView<Presenter> {
        void showPercentage(String percentage);
        List<String> getOdds();
    }

    interface StakesView extends BaseView<Presenter> {
        void setStakes(List<Double> stakes);
    }

    interface Presenter extends BasePresenter {
        void calculatePercentage(String leftOdd, String middleOdd, String rightOdd);
        void calculateStakes(double stake, int whichOne);
    }
}
