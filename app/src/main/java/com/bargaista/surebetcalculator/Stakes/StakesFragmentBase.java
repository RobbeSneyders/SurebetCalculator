package com.bargaista.surebetcalculator.Stakes;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.bargaista.surebetcalculator.Calculator.BaseCalculator;
import com.bargaista.surebetcalculator.CompositeOnEditorActionListener;
import com.bargaista.surebetcalculator.R;
import com.bargaista.surebetcalculator.TextWatcherFragment;

import java.util.List;

/**
 * BaseFragment displaying the stakes
 */
public abstract class StakesFragmentBase extends TextWatcherFragment
        implements StakesContract.View, OnEditorActionListener {

    private StakesContract.Presenter presenter;

    protected EditText leftEditText;
    protected EditText middleEditText;
    protected EditText rightEditText;
    protected EditText totalEditText;
    protected EditText roundEditText;

    protected TextView leftProfitTV;
    protected TextView middleProfitTV;
    protected TextView rightProfitTV;

    protected List<EditText> stakeEditTexts;
    protected List<EditText> allEditTexts;
    protected List<CheckBox> profitCheckBoxes;
    protected List<CheckBox> roundCheckBoxes;
    protected List<TextView> profitTextViews;

    private TextView toggleTextView;
    private LinearLayout profitsLayout;

    private Toast toast;

    @Override
    public void setPresenter(StakesContract.Presenter presenter) {
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
        roundEditText = (EditText) view.findViewById(R.id.roundEditText);

        toggleTextView = (TextView) view.findViewById(R.id.toggleTextView);
        toggleTextView.setOnClickListener(onToggleTVListener);

        profitsLayout = (LinearLayout) view.findViewById(R.id.profits);

        leftProfitTV = (TextView) view.findViewById(R.id.leftProfitTextView);
        middleProfitTV = (TextView) view.findViewById(R.id.middleProfitTextView);
        rightProfitTV = (TextView) view.findViewById(R.id.rightProfitTextView);
    }

    @Override
    public void onResume() {
        super.onResume();
        setOnEditorActionListeners();
        addTextChangedListeners();
        addCheckBoxListeners();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void setStakes(List<String> stakes) {
        for (int i = 0, n = stakes.size(); i < n; i++)
            setText(stakeEditTexts.get(i),stakes.get(i));
    }

    @Override
    public void setTotal(String total) {
        setText(totalEditText, total);
    }

    @Override
    public void setN(String n) {
        setText(roundEditText, n);
    }

    @Override
    public void setProfits(List<String> profits) {
        for (int i = 0, n = profitTextViews.size(); i < n; i++) {
            profitTextViews.get(i).setText(profits.get(i));
            Log.d("Profit" + String.valueOf(i+1), profits.get(i));
        }
        setProfitsVisible(true);
    }

    private void setProfitsVisible(boolean visible) {
        if (visible)
            profitsLayout.setVisibility(View.VISIBLE);
        else
            profitsLayout.setVisibility(View.GONE);
    }

    @Override
    public void clearStakes() {
        for (EditText et: stakeEditTexts)
            setText(et, "");
        setText(totalEditText, "");
        setProfitsVisible(false);
    }

    @Override
    public void clearOtherStakes(int whichOne) {
        for (EditText et: allEditTexts) {
            if (allEditTexts.indexOf(et) != whichOne && et != roundEditText)
                setText(et, "");
        }
        setProfitsVisible(false);
    }

    private void setOnEditorActionListeners() {
        roundEditText.setOnEditorActionListener((OnEditorActionListener) getActivity());

        CompositeOnEditorActionListener listener = new CompositeOnEditorActionListener();
        listener.registerListener((OnEditorActionListener) getActivity());
        listener.registerListener(this);

        for (EditText et: stakeEditTexts) {
            et.setOnEditorActionListener(listener);
        }
        totalEditText.setOnEditorActionListener(listener);
    }

    private void addTextChangedListeners() {
        for (EditText et: allEditTexts)
            et.addTextChangedListener(this);
    }

    private void addCheckBoxListeners() {
        for (CheckBox cb: profitCheckBoxes) {
            cb.setOnClickListener(onProfitCheckedListener);
        }
        for (CheckBox cb: roundCheckBoxes) {
            cb.setOnClickListener(onRoundCheckedListener);
        }
    }

    @Override
    public void setDistribution(String distr) {
        toggleTextView.setText(distr);
    }

    @Override
    public void textChanged(Editable editable) {
        if (roundEditText.getText() == editable)
            presenter.onNChanged(editable.toString());
        else {
            for (EditText et : allEditTexts) {
                if (et.getText() == editable)
                    presenter.onStakesChanged(allEditTexts.indexOf(et));
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE){
            if (v == totalEditText) {
                presenter.onRequestCalculationFromTotal(v.getText().toString());
            }
            else {
                for (EditText et: stakeEditTexts) {
                    if (v == et)
                        presenter.onRequestCalculationFromStake(v.getText().toString(), stakeEditTexts.indexOf(v));
                }
            }
        }
        return false;
    }

    @Override
    public void setRounded(boolean[] rounded) {
        for (int i = 0; i < rounded.length; i++)
            roundCheckBoxes.get(i).setChecked(rounded[i]);
    }

    @Override
    public void setProfitWanted(boolean[] profitWanted) {
        for (int i = 0; i < profitWanted.length; i++)
            profitCheckBoxes.get(i).setChecked(profitWanted[i]);
    }

    private boolean[] isCheckedList(List<CheckBox> list) {
        boolean[] checked = new boolean[list.size()];
        for (CheckBox cb: list)
            checked[list.indexOf(cb)] = cb.isChecked();
        return checked;
    }

    @Override
    public void showError(String error) {
        toast(error);
    }

    private void toast(String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    View.OnClickListener onToggleTVListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            int flag;
            if (tv.getText().toString().equals("E")) {
                tv.setText("P");
                toast(getString(R.string.distr_prob));
                flag = BaseCalculator.PROB;
            }
            else {
                tv.setText("E");
                toast(getString(R.string.distr_equal));
                flag = BaseCalculator.EQUAL;
            }
            presenter.onDistributionChanged(flag);
        }
    };

    View.OnClickListener onProfitCheckedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox checkBox = (CheckBox) v;
            boolean checked = checkBox.isChecked();
            presenter.onProfitWantedChanged(isCheckedList(profitCheckBoxes));
            if (checked)
                toast(getString(R.string.profit));
            else
                toast(getString(R.string.no_profit));
        }
    };

    View.OnClickListener onRoundCheckedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox checkBox = (CheckBox) v;
            boolean checked = checkBox.isChecked();
            presenter.onRoundedChanged(isCheckedList(roundCheckBoxes));
            if (checked)
                toast(getString(R.string.round_stakes));
            else
                toast(getString(R.string.no_round_stakes));
        }
    };

    protected void setText(EditText editText, String string) {
        editText.removeTextChangedListener(this);
        editText.setText(string);
        editText.addTextChangedListener(this);
    }
}
