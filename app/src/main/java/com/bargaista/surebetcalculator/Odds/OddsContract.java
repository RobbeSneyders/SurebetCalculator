package com.bargaista.surebetcalculator.Odds;

import com.bargaista.surebetcalculator.BaseView;

import java.util.List;

/**
 * Contract between the View and Presenter for the Odds feature
 */
public class OddsContract {

    /**
     * Functionality of View that Presenter can rely on
     */
    interface View extends BaseView<OddsContract.Presenter> {
        void showPercentage(String percentage);
        List<String> getOdds();
    }

    /**
     * Functionality of Presenter that View can rely on
     */
    interface Presenter {
        void onOddsChanged();
    }
}
