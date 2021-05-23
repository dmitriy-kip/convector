package com.example.convector;

import java.util.List;

public class Controller implements MainInterface.Controller {
    private MainInterface.ViewComponent viewComponent;
    private MainInterface.Model model;

    public  Controller(MainInterface.ViewComponent viewComponent){
        this.viewComponent = viewComponent;
        this.model = SingletonModel.getInstance().getModel();
    }

    @Override
    public void loadData(){
        model.loadCurrentCurrencyValue();
    }

    @Override
    public List<Currency> getCurrencyList() {
        return model.getList();
    }

    @Override
    public Currency getFirstCurrency() {
        return model.getFirstCurrency();
    }

    @Override
    public void setFirstCurrency(Currency firstCurrency) {
        model.setFirstCurrency(firstCurrency);
    }

    @Override
    public Currency getSecondCurrency() {
        return model.getSecondCurrency();
    }

    @Override
    public void setSecondCurrency(Currency secondCurrency) {
        model.setSecondCurrency(secondCurrency);
    }

    @Override
    public double getFirstToSecond(double value) {
        return model.fromFirstToSecond(value);
    }

    @Override
    public double getSecondToFirst(double value) {
        return model.fromSecondToFirst(value);
    }
}
