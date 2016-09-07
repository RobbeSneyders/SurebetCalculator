package com.bargaista.surebetcalculator.Calculator;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Robbe on 31/08/2016.
 */
public abstract class CalculatorFragmentBase extends Fragment implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        String string = editable.toString();

        Pattern pattern = Pattern.compile("([.])");
        Matcher matcher = pattern.matcher(string);

        int count = 0;
        while (matcher.find()) count++;

        if(editable.length() == 1 && string.equals(".")) {
            editable.clear();
        }
        else if(count == 2) {
            string = string.substring(0,string.length()-1);
            editable.clear();
            editable.append(string);
        }
        else {
            textChanged(editable);
        }
    }

    protected abstract void textChanged(Editable editable);

}
