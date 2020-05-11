package com.example.myapplication.ChartData;

public class ChartData {
    String values;
    int sales;

    public ChartData(String values, int sales) {
        this.values = values;
        this.sales = sales;
    }

    public String getValues() {
        return values;
    }

    public int getSales() {
        return sales;
    }
}
