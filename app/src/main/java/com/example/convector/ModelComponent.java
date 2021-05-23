package com.example.convector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ModelComponent implements MainInterface.Model {
    private List<Currency> currencyList = new ArrayList<>();
    private Currency firstCurrency;
    private Currency secondCurrency;

    @Override
    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    @Override
    public void setFirstCurrency(Currency firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    @Override
    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    @Override
    public void setSecondCurrency(Currency secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    @Override
    public double fromFirstToSecond(double value) {
        double a = firstCurrency.getValue() / firstCurrency.getNominal();
        double b = secondCurrency.getValue() / secondCurrency.getNominal();
        return a / b * value;
    }

    @Override
    public double fromSecondToFirst(double value) {
        double a = firstCurrency.getValue() / firstCurrency.getNominal();
        double b = secondCurrency.getValue() / secondCurrency.getNominal();
        return b / a * value;
    }

    @Override
    public List<Currency> getList() {
        return currencyList;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("Windows-1251"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    @Override
    public void loadCurrentCurrencyValue() {
        URL url;
        String response;
        try {
            url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(con.getInputStream());
                response = readFromStream(in);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader(response));

                List<String> tmp = null;
                int count = 0;
                currencyList.add(new Currency("RUS", 1, "Российский рубль", 1));

                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    switch (parser.getEventType()) {
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("Valute")) {
                                tmp = new ArrayList<>();
                            }

                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("Valute")) {
                                count++;
                            }
                            if (count > 1) {
                                count = 0;
                                assert tmp != null;
                                currencyList.add(new Currency(tmp.get(1), Integer.parseInt(tmp.get(2)),
                                        tmp.get(3), Double.parseDouble(tmp.get(4).replace(',','.'))));
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if (tmp != null) {
                                tmp.add(parser.getText());
                            }
                            break;

                        default:
                            break;
                    }
                    parser.next();
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        setFirstCurrency(currencyList.get(0));
        setSecondCurrency(currencyList.get(1));
    }


}
