package com.example.convector.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.convector.Controller;
import com.example.convector.MainInterface;
import com.example.convector.R;

public class CalculateFragment extends Fragment implements MainInterface.ViewComponent.ConvectorCalculateFragment {

    private MainInterface.Controller controller;
    private MainInterface.NavigationFragments listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calc, container, false);

        controller = new Controller(this);
        TextView firstButton = rootView.findViewById(R.id.changeCurrencyFirst);
        TextView secondButton = rootView.findViewById(R.id.changeCurrencySecond);
        EditText valueFirstCurrency = rootView.findViewById(R.id.valueFirstCurrency);
        EditText valueSecondCurrency = rootView.findViewById(R.id.valueSecondCurrency);

        TextView firstCurrency = rootView.findViewById(R.id.firstCurrency);
        TextView secondCurrency = rootView.findViewById(R.id.secondCurrency);
        firstCurrency.setText(controller.getFirstCurrency().getCharCode());
        secondCurrency.setText(controller.getSecondCurrency().getCharCode());

        final boolean[] a = {true};
        final boolean[] b = {true};
        //следим за изменением текста в поле для ввода колличества валюты
        valueFirstCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                a[0] = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b[0]) {
                    String str = s.toString().replace(',', '.');
                    if (str.equals("") || str.equals(".")) s = "0";
                    if (str.startsWith(".")) s = "0" + s;
                    if (str.length() - str.replaceAll("\\.","").length() > 1) {
                        valueSecondCurrency.setText(R.string.count_error);
                    } else {
                        double value = Double.parseDouble(s.toString().replace(',', '.'));
                        String result = String.format("%.3f", controller.getFirstToSecond(value));
                        valueSecondCurrency.setText(result);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                a[0] = true;
            }
        });
        valueSecondCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                b[0] = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (a[0]) {
                    String str = s.toString().replace(',', '.');
                    if (str.equals("") || str.equals(".")) s = "0";
                    if (str.startsWith(".")) s = "0" + s;
                    if (str.length() - str.replaceAll("\\.","").length() > 1) {
                        valueFirstCurrency.setText(R.string.count_error);
                    } else {
                        double value = Double.parseDouble(s.toString().replace(',', '.'));
                        String result = String.format("%.3f", controller.getSecondToFirst(value));
                        valueFirstCurrency.setText(result);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                b[0] = true;
            }
        });

        firstButton.setOnClickListener(v -> listener.goToChooseCurrency(1));
        secondButton.setOnClickListener(v -> listener.goToChooseCurrency(2));

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (MainInterface.NavigationFragments) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
