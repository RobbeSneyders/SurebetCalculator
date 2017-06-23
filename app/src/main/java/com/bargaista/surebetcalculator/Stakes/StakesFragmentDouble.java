package com.bargaista.surebetcalculator.Stakes;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.bargaista.surebetcalculator.R;

import java.util.Arrays;

/**
 * Extension of StakesFragmentBase for double type bet
 */
public class StakesFragmentDouble extends StakesFragmentBase {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((View) rightEditText.getParent().getParent().getParent()).setVisibility(View.GONE);
        rightProfitTV.setVisibility(View.GONE);

        stakeEditTexts = Arrays.asList(leftEditText, middleEditText);
        allEditTexts = Arrays.asList(leftEditText, middleEditText, totalEditText, roundEditText);
        profitTextViews = Arrays.asList(leftProfitTV, middleProfitTV);

        CheckBox cb1 = (CheckBox) view.findViewById(R.id.checkBoxDistr1);
        CheckBox cb2 = (CheckBox) view.findViewById(R.id.checkBoxDistr2);
        CheckBox cb4 = (CheckBox) view.findViewById(R.id.checkBoxRound1);
        CheckBox cb5 = (CheckBox) view.findViewById(R.id.checkBoxRound2);

        profitCheckBoxes = Arrays.asList(cb1, cb2);
        roundCheckBoxes = Arrays.asList(cb4, cb5);
    }
}
