package com.bargaista.surebetcalculator;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Enables the use of multiple OnEditorActionListeners on a single EditText by using the
 * composite pattern.
 */
public class CompositeOnEditorActionListener implements OnEditorActionListener {

    private List<OnEditorActionListener> registeredListeners= new ArrayList<>();

    public void registerListener(OnEditorActionListener listener) {
        registeredListeners.add(listener);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        for (OnEditorActionListener listener: registeredListeners)
            listener.onEditorAction(v, actionId, event);
        return false;
    }
}
