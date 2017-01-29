package com.bargaista.surebetcalculator;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bargaista.surebetcalculator.Calculator.BaseCalculator;
import com.bargaista.surebetcalculator.Calculator.DoubleCalculator;
import com.bargaista.surebetcalculator.Calculator.TripleCalculator;
import com.bargaista.surebetcalculator.Odds.OddsFragmentBase;
import com.bargaista.surebetcalculator.Odds.OddsFragmentDouble;
import com.bargaista.surebetcalculator.Odds.OddsFragmentTriple;
import com.bargaista.surebetcalculator.Odds.OddsPresenter;
import com.bargaista.surebetcalculator.Stakes.StakesFragmentBase;
import com.bargaista.surebetcalculator.Stakes.StakesFragmentDouble;
import com.bargaista.surebetcalculator.Stakes.StakesFragmentTriple;
import com.bargaista.surebetcalculator.Stakes.StakesPresenter;

/**
 * Main activity of the application.
 * This activity holds an OddsFragment and a StakesFragment
 */
public class CalculatorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener, TextView.OnEditorActionListener {

    private int type = DOUBLE;

    private static final int DOUBLE = 2;
    private static final int TRIPLE = 3;

    private BaseCalculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_root);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(this);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_double);

        swapFragments(DOUBLE);
    }

    /**
     * Swap old fragments with corresponding new fragments
     * @param flag DOUBLE or TRIPLE: type of bet to show
     */
    private void swapFragments(int flag) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OddsFragmentBase oddsFragment = (OddsFragmentBase) fragmentManager.findFragmentById(R.id.oddsFragment);
        StakesFragmentBase stakesFragment = (StakesFragmentBase) fragmentManager.findFragmentById(R.id.stakesFragment);
        if (flag == DOUBLE) {
            if (oddsFragment == null || !(oddsFragment instanceof OddsFragmentDouble))
                oddsFragment = new OddsFragmentDouble();
            if (stakesFragment == null || !(stakesFragment instanceof StakesFragmentDouble))
                stakesFragment = new StakesFragmentDouble();
            calculator = new DoubleCalculator();
        }
        else { // if (flag == TRIPLE) {
            if (oddsFragment == null || !(oddsFragment instanceof OddsFragmentTriple))
                oddsFragment = new OddsFragmentTriple();
            if (stakesFragment == null || !(stakesFragment instanceof StakesFragmentTriple))
                stakesFragment = new StakesFragmentTriple();
            calculator = new TripleCalculator();
        }

        fragmentTransaction.replace(R.id.oddsFragment, oddsFragment);
        fragmentTransaction.replace(R.id.stakesFragment, stakesFragment);
        fragmentTransaction.commit();

        new OddsPresenter(oddsFragment, calculator);
        new StakesPresenter(stakesFragment, calculator);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_double:
                if (type != DOUBLE) {
                    swapFragments(DOUBLE);
                    type = DOUBLE;
                }
                break;
            case R.id.nav_triple:
                if (type != TRIPLE) {
                    swapFragments(TRIPLE);
                    type = TRIPLE;
                }
                break;
            case R.id.nav_info:
                Intent infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/RobbeSneyders/SurebetCalculator/blob/master/INFO.md"));
                startActivity(infoIntent);
                break;
            case R.id.nav_rate:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.bargaista.surebetcalculator"));
                startActivity(rateIntent);
                break;
            case R.id.nav_source:
                Intent sourceIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/RobbeSneyders/SurebetCalculator"));
                startActivity(sourceIntent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE){
            //Clear focus here from edittext
            hideKeyboard();
            v.clearFocus();
            findViewById(R.id.focusLayout).requestFocus(); // focusLayout was added with goal to steal focus from editText
        }
        return false;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {}

    @Override
    public void onDrawerOpened(View drawerView) {}

    @Override
    public void onDrawerClosed(View drawerView) {}

    @Override
    public void onDrawerStateChanged(int newState) {
        hideKeyboard();
    }
}
