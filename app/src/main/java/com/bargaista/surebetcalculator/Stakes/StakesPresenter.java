package com.bargaista.surebetcalculator.Stakes;

import com.bargaista.surebetcalculator.Calculator.BaseCalculator;
import com.bargaista.surebetcalculator.Odds.OddsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for Stakes feature
 */
public class StakesPresenter implements StakesContract.Presenter {

    StakesContract.View stakesFragment;
    BaseCalculator calculator;

    public StakesPresenter(StakesContract.View stakesFragment, BaseCalculator calculator) {
        this.stakesFragment = stakesFragment;
        this.calculator = calculator;
        stakesFragment.setPresenter(this);
    }

    @Override
    public void onResume() {
        if (calculator.getN() != 0)
            stakesFragment.setN(String.format("%.2f", calculator.getN()));
        stakesFragment.setRounded(calculator.getRounded());
        stakesFragment.setProfitWanted(calculator.getProfitWanted());
        if (calculator.getDistribution() == calculator.EQUAL)
            stakesFragment.setDistribution("E");
        else if (calculator.getDistribution() == calculator.PROB)
            stakesFragment.setDistribution("P");

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStakesChanged(int whichOne) {
        if (calculator.getOdds() == null) {
            stakesFragment.clearStakes();
            stakesFragment.showError("Fill in odds first");
        }
        else {
            stakesFragment.clearOtherStakes(whichOne);
        }
    }

    @Override
    public void onNChanged(String n) {
        stakesFragment.clearStakes();
        if (!n.equals(""))
            calculator.setN(Integer.parseInt(n));
        else
            calculator.setN(0);
    }

    @Override
    public void onRoundedChanged(boolean[] rounded) {
        stakesFragment.clearStakes();
        calculator.setRounded(rounded);
    }

    @Override
    public void onProfitWantedChanged(boolean[] profitWanted) {
        stakesFragment.clearStakes();
        calculator.setProfitWanted(profitWanted);
    }

    @Override
    public void onDistributionChanged(int flag) {
        stakesFragment.clearStakes();
        calculator.setDistribution(flag);
    }

    @Override
    public void onRequestCalculationFromStake(String stake, int whichOne) {
        if (!stake.equals(""))
            showSolution(calculator.calculateFromStake(Double.parseDouble(stake), whichOne));
    }

    @Override
    public void onRequestCalculationFromTotal(String total) {
        if (!total.equals(""))
            showSolution(calculator.calculateFromTotal(Double.parseDouble(total)));
    }

    private void showSolution(double[][] solution) {
        List<String> stakes = new ArrayList<String>();
        for (double s: solution[0])
            stakes.add(String.format("%.2f", s));

        String total = String.format("%.2f", solution[1][0]);

        List<String> profits = new ArrayList<String>();
        for (double r: solution[2])
            profits.add(String.format("%.2f", r));

        stakesFragment.setStakes(stakes);
        stakesFragment.setTotal(total);
        stakesFragment.setProfits(profits);
    }

    /**
     * Called when OddsChangedEvent is posted to EventBus
     * @param event empty holder
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOddsChangedEvent(OddsPresenter.OddsChangedEvent event) {
        stakesFragment.clearStakes();
    };

}
