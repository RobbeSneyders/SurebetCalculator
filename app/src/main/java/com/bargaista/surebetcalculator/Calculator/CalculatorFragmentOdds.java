package com.bargaista.surebetcalculator.Calculator;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.bargaista.surebetcalculator.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Robbe on 31/08/2016.
 */
public class CalculatorFragmentOdds extends CalculatorFragmentBase implements CalculatorContract.OddsView {

    private CalculatorContract.Presenter presenter;

    private EditText leftEditText;
    private EditText middleEditText;
    private EditText rightEditText;
    private TextView percentageTextView;

    @Override
    public void setPresenter(CalculatorContract.Presenter presenter) {
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

        rightEditText.setOnEditorActionListener((OnEditorActionListener) getActivity());

        leftEditText.addTextChangedListener(this);
        middleEditText.addTextChangedListener(this);
        rightEditText.addTextChangedListener(this);

        percentageTextView = (TextView) view.findViewById(R.id.percentageTextView);
    }

    public void showPercentage(String percentage) {
        percentageTextView.setText(percentage);
    }

    @Override
    protected void textChanged(Editable editable) {
        presenter.calculatePercentage(getLeftOdd(), getMiddleOdd(), getRightOdd());
    }


    @Override
    public List<String> getOdds() {
        return Arrays.asList(getLeftOdd(), getMiddleOdd(), getRightOdd());
    }

    private String getLeftOdd() {
        return leftEditText.getText().toString();
    }

    private String getMiddleOdd() {
        return middleEditText.getText().toString();
    }

    private String getRightOdd() {
        return rightEditText.getText().toString();
    }
}
