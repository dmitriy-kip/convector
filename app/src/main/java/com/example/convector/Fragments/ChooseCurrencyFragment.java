package com.example.convector.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convector.ChooseAdapter;
import com.example.convector.Controller;
import com.example.convector.MainInterface;
import com.example.convector.R;

public class ChooseCurrencyFragment extends Fragment implements MainInterface.ViewComponent.ChooseFragment {

    private MainInterface.Controller controller;
    private MainInterface.NavigationFragments listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose_currency, container, false);

        controller = new Controller(this);
        int numberOfButton = 0;

        Bundle args = getArguments();
        if (args != null){
            numberOfButton = args.getInt("numberOfButton");
        }

        if (numberOfButton == 0){
            TextView textError = rootView.findViewById(R.id.error);
            textError.setVisibility(View.VISIBLE);
            return rootView;
        }

        RecyclerView recyclerViewChoose = rootView.findViewById(R.id.currencyList);
        recyclerViewChoose.setLayoutManager(new LinearLayoutManager(getContext()));
        ChooseAdapter adapter = new ChooseAdapter(controller.getCurrencyList(),getContext(), numberOfButton);
        recyclerViewChoose.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.choose_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                listener.backToCalculate();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
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
