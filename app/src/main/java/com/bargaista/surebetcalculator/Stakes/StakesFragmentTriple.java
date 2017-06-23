package com.bargaista.surebetcalculator.Stakes;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.bargaista.surebetcalculator.R;

import java.util.Arrays;

/**
 * Extension of StakesFragmentBase for triple type bet
 */
public class StakesFragmentTriple extends StakesFragmentBase {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stakeEditTexts = Arrays.asList(leftEditText, middleEditText, rightEditText);
        allEditTexts = Arrays.asList(leftEditText, middleEditText, rightEditText, totalEditText, roundEditText);
        profitTextViews = Arrays.asList(leftProfitTV, middleProfitTV, rightProfitTV);

        CheckBox cb1 = (CheckBox) view.findViewById(R.id.checkBoxDistr1);
        CheckBox cb2 = (CheckBox) view.findViewById(R.id.checkBoxDistr2);
        CheckBox cb3 = (CheckBox) view.findViewById(R.id.checkBoxDistr3);
        CheckBox cb4 = (CheckBox) view.findViewById(R.id.checkBoxRound1);
        CheckBox cb5 = (CheckBox) view.findViewById(R.id.checkBoxRound2);
        CheckBox cb6 = (CheckBox) view.findViewById(R.id.checkBoxRound3);

        profitCheckBoxes = Arrays.asList(cb1, cb2, cb3);
        roundCheckBoxes = Arrays.asList(cb4, cb5, cb6);
    }
}
