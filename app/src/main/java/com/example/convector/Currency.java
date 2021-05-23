package com.example.convector;

public class Currency {
    private String charCode;
    private int nominal;
    private String name;
    private double value;

    public Currency(String charCode, int nominal, String name, double value) {
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getName() {
        return name;
    }

    public int getNominal() {
        return nominal;
    }

    public double getValue() {
        return value;
    }
}
