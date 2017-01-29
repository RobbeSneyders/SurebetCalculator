package com.bargaista.surebetcalculator.Odds;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

/**
 * Extension of OddsFragmentBase for triple type bet
 */
public class OddsFragmentTriple extends OddsFragmentBase {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public List<String> getOdds() {
        String leftOdd = leftEditText.getText().toString();
        String middleOdd = middleEditText.getText().toString();
        String rightOdd = rightEditText.getText().toString();

        return Arrays.asList(leftOdd, middleOdd, rightOdd);
    }
}
