package com.bargaista.surebetcalculator.Calculator;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;

import com.bargaista.surebetcalculator.R;

import java.util.List;

/**
 * Created by Robbe on 31/08/2016.
 */
public class CalculatorFragmentStakes extends CalculatorFragmentBase implements CalculatorContract.StakesView {

    private CalculatorContract.Presenter presenter;

    private EditText leftEditText;
    private EditText middleEditText;
    private EditText rightEditText;
    private EditText totalEditText;

    @Override
    public void setPresenter(CalculatorContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculator_fragment_stakes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        leftEditText = (EditText) view.findViewById(R.id.leftStakeEditText);
        middleEditText = (EditText) view.findViewById(R.id.middleStakeEditText);
        rightEditText = (EditText) view.findViewById(R.id.rightStakeEditText);
        totalEditText = (EditText) view.findViewById(R.id.totalStakeEditText);

        addTextChangedListeners();

        leftEditText.setOnEditorActionListener((OnEditorActionListener) getActivity());
        middleEditText.setOnEditorActionListener((OnEditorActionListener) getActivity());
        rightEditText.setOnEditorActionListener((OnEditorActionListener) getActivity());
        totalEditText.setOnEditorActionListener((OnEditorActionListener) getActivity());
    }

    private void addTextChangedListeners() {
        leftEditText.addTextChangedListener(this);
        middleEditText.addTextChangedListener(this);
        rightEditText.addTextChangedListener(this);
        totalEditText.addTextChangedListener(this);
    }

    private void removeTextChangedListeners() {
        leftEditText.removeTextChangedListener(this);
        middleEditText.removeTextChangedListener(this);
        rightEditText.removeTextChangedListener(this);
        totalEditText.removeTextChangedListener(this);
    }

    @Override
    protected void textChanged(Editable editable) {
        EditText editText = (EditText) getActivity().getCurrentFocus();
        int i = -1;
        if (editText == leftEditText)
            i = 0;
        else if (editText == middleEditText)
            i = 1;
        else if (editText == rightEditText)
            i = 2;
        else if (editText == totalEditText)
            i = 3;
        presenter.calculateStakes(Double.valueOf(editable.toString()), i);
    }

    @Override
    public void setStakes(List<Double> stakes) {
        removeTextChangedListeners();
        leftEditText.setText(stakes.get(0).toString());
        middleEditText.setText(stakes.get(1).toString());
        rightEditText.setText(stakes.get(2).toString());
        totalEditText.setText(stakes.get(3).toString());
        addTextChangedListeners();
    }
}
