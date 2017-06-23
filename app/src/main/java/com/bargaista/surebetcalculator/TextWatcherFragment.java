package com.bargaista.surebetcalculator;

import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This extension of Fragment implements TextWatcher and makes sure the users input is fit for use
 * in the rest of the application.
 */
public abstract class TextWatcherFragment extends Fragment
        implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        String string = editable.toString();

        Pattern dot = Pattern.compile("([.])");
        Matcher dotMatcher = dot.matcher(string);

        int dotCount = 0;
        while (dotMatcher.find()) dotCount++;

        Pattern slash = Pattern.compile("([/])");
        Matcher slashMatcher = slash.matcher(string);

        int slashCount = 0;
        while (slashMatcher.find()) slashCount++;

        // can't start with ".", "/" or "0"
        if(editable.length() == 1 && (dotCount == 1 || slashCount == 1 || editable.toString().equals("0"))) {
            editable.clear();
        }
        // only one "." or "/" in total
        else if(dotCount + slashCount == 2) {
            string = string.substring(0,string.length()-1);
            editable.clear();
            editable.append(string);
        }
        // if ok, call textChanged
        else {
            textChanged(editable);
        }
    }

    public abstract void textChanged(Editable editable);
}
