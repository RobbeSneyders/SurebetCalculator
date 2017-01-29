package com.bargaista.surebetcalculator.Odds;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

/**
 * Extension of OddsFragmentBase for double type bet
 */
public class OddsFragmentDouble extends OddsFragmentBase {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((View) rightEditText.getParent().getParent()).setVisibility(View.GONE);
    }

    @Override
    public List<String> getOdds() {
        String leftOdd = leftEditText.getText().toString();
        String middleOdd = middleEditText.getText().toString();

        return Arrays.asList(leftOdd, middleOdd);
    }
}
