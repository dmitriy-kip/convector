package com.example.convector;

import java.util.List;

public interface MainInterface {

    interface NavigationFragments {
        void goToCalculate();
        void goToChooseCurrency(int numberOfButton);
        void backToCalculate();
    }

    interface ViewComponent {
        interface ConvectorActivity extends ViewComponent {
        }
        interface ConvectorCalculateFragment extends ViewComponent{
        }
        interface ChooseFragment extends ViewComponent{
        }
    }

    interface Controller {
        void loadData();
        List<Currency> getCurrencyList();
        Currency getFirstCurrency();
        void setFirstCurrency(Currency firstCurrency);
        Currency getSecondCurrency();
        void setSecondCurrency(Currency secondCurrency);
        double getFirstToSecond(double value);
        double getSecondToFirst(double value);
    }

    interface Model {
        void loadCurrentCurrencyValue();
        List<Currency> getList();
        Currency getFirstCurrency();
        void setFirstCurrency(Currency firstCurrency);
        Currency getSecondCurrency();
        void setSecondCurrency(Currency secondCurrency);
        double fromFirstToSecond(double value);
        double fromSecondToFirst(double value);
    }
}
