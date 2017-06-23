package com.bargaista.surebetcalculator.Stakes;

import com.bargaista.surebetcalculator.BaseView;

import java.util.List;

/**
 * Contract between the View and Presenter for the Stakes feature
 */
public class StakesContract {

    /**
     * Functionality of View that Presenter can rely on
     */
    interface View extends BaseView<StakesContract.Presenter> {
        void setStakes(List<String> stakes);

        void setTotal(String total);

        void setProfits(List<String> results);

        void clearStakes();

        void clearOtherStakes(int whichOne);

        void showError(String error);

        void setN(String n);

        void setRounded(boolean[] rounded);

        void setProfitWanted(boolean[] profitWanted);

        void setDistribution(String distribution);
    }

    /**
     * Functionality of Presenter that View can rely on
     */
    interface Presenter {
        void onStakesChanged(int whichOne);

        void onNChanged(String n);

        void onRoundedChanged(boolean[] rounded);

        void onProfitWantedChanged(boolean[] profitWanted);

        void onDistributionChanged(int flag);

        void onRequestCalculationFromStake(String stake, int whichOne);

        void onRequestCalculationFromTotal(String total);

        void onResume();

        void onPause();
    }
}
