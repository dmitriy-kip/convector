package com.example.convector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.convector.Fragments.CalculateFragment;
import com.example.convector.Fragments.ChooseCurrencyFragment;

public class MainActivity extends AppCompatActivity implements MainInterface.ViewComponent.ConvectorActivity, MainInterface.NavigationFragments {

    private MainInterface.Controller controller;
    private ProgressBar progressBar;
    private CalculateFragment calculateFragment;
    private TextView viewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller(this);

        progressBar = findViewById(R.id.progressBar);
        viewError = findViewById(R.id.viewError);

        if (isOnline()) {
            progressBar.setVisibility(View.VISIBLE);
            new Thread(runnable).start();
        } else {
            viewError.setText(R.string.network_error);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            controller.loadData();
            handler.sendEmptyMessage(1);
        }
        @SuppressLint("HandlerLeak")
        final
        Handler handler = new Handler((Handler.Callback) runnable){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (controller.getCurrencyList().size() != 0) {
                        goToCalculate();
                    } else {
                        viewError.setText(R.string.download_error);
                    }
                }
            }
        };
    };

    @Override
    public void goToCalculate() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        calculateFragment = new CalculateFragment();
        ft.replace(R.id.container, calculateFragment);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.commit();
    }

    @Override
    public void goToChooseCurrency(int numberOfButton) {
        getSupportActionBar().setTitle(R.string.chooseTitle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ChooseCurrencyFragment fragment = new ChooseCurrencyFragment();

        Bundle args = new Bundle();
        args.putInt("numberOfButton", numberOfButton);
        fragment.setArguments(args);

        ft.add(R.id.container, fragment);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.commit();
    }

    @Override
    public void backToCalculate() {
        getSupportActionBar().setTitle(R.string.app_name);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, calculateFragment);
        ft.commit();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}