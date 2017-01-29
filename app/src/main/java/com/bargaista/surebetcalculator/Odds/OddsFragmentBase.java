package com.bargaista.surebetcalculator.Odds;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bargaista.surebetcalculator.R;
import com.bargaista.surebetcalculator.TextWatcherFragment;

import java.util.List;

/**
 * Fragment located in the AppBarLayout, displaying the odds and percentage of the bet.
 */
public abstract class OddsFragmentBase extends TextWatcherFragment
        implements OddsContract.View {

    protected OddsContract.Presenter presenter;

    protected EditText leftEditText;
    protected EditText middleEditText;
    protected EditText rightEditText;
    protected TextView percentageTextView;

    @Override
    public void setPresenter(OddsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculator_fragment_odds, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        leftEditText = (EditText) view.findViewById(R.id.leftOddEditText);
        middleEditText = (EditText) view.findViewById(R.id.middleOddEditText);
        rightEditText = (EditText) view.findViewById(R.id.rightOddEditText);
        percentageTextView = (TextView) view.findViewById(R.id.percentageTextView);

        leftEditText.addTextChangedListener(this);
        middleEditText.addTextChangedListener(this);
        rightEditText.addTextChangedListener(this);
    }

    @Override
    public void showPercentage(String percentage) {
        percentageTextView.setText(percentage);
    }

    @Override
    public void textChanged(Editable editable) {
        presenter.onOddsChanged();
    }

    @Override
    public abstract List<String> getOdds();
}
