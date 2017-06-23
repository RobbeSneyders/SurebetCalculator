package com.bargaista.surebetcalculator.Odds;

import com.bargaista.surebetcalculator.Calculator.BaseCalculator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Presenter for Odds feature
 */
public class OddsPresenter implements OddsContract.Presenter {

    OddsContract.View oddsFragment;
    BaseCalculator calculator;

    public OddsPresenter(OddsContract.View oddsFragment, BaseCalculator calculator) {
        this.oddsFragment = oddsFragment;
        this.calculator = calculator;
        oddsFragment.setPresenter(this);
    }

    @Override
    public void onOddsChanged() {
        List<String> oddsList = oddsFragment.getOdds();

        String percentage;
        if (!areOddsOk(oddsList)) {
            percentage = "";
            calculator.setOdds(null);
        }
        else {
            double[] odds = new double[oddsList.size()];

            for (int i = 0, n = oddsList.size(); i < n; i++)
                odds[i] = parseOdd(oddsList.get(i));

            calculator.setOdds(odds);
            percentage = String.format("%.2f", calculator.calculatePercentage()) + "%";
        }
        oddsFragment.showPercentage(percentage);
        EventBus.getDefault().post(new OddsChangedEvent());
    }

    private boolean areOddsOk(List<String> odds) {
        for (String odd : odds) {
            if (odd.equals("") || odd.indexOf("/") == odd.length() - 1)
                return false;
        }
        return true;
    }

    private double parseOdd(String string) {
        double odd;
        if (string.contains("/")) {
            String[] parts = string.split("/");
            odd = Double.parseDouble(parts[0])/Double.parseDouble(parts[1]) + 1;
        }
        else {
            odd = Double.parseDouble(string);
        }
        return odd;
    }

    public static class OddsChangedEvent{}
}
