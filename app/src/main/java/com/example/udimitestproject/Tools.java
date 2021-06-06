package com.example.udimitestproject;

import java.text.DecimalFormat;

public class Tools {

    public static Double parseNumber(String number) {
        if(number == null)
            return 0.0;

        Double parseDouble = null;
        try {
            parseDouble = Double.parseDouble(number);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        return parseDouble == null ? 0.0 : parseDouble;
    }

    public static Double round(Double number, int numbersAfterDot) {
        if(number == null)
            return 0.0;

        String numberPattern = "#.";
        for(int i = 0; i < numbersAfterDot; ++i)
            numberPattern += "#";

        DecimalFormat df = new DecimalFormat(numberPattern);

        Double parseDouble = null;
        try {
            parseDouble = Double.valueOf(df.format(number));
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return parseDouble == null ? 0.0 : parseDouble;
    }
}
