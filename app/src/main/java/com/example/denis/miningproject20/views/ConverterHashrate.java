package com.example.denis.miningproject20.views;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.acl.LastOwnerException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by denis on 31.07.17.
 */

public class ConverterHashrate {
    private static final ConverterHashrate ourInstance = new ConverterHashrate();
    private static final Double KILO = Math.pow(10, 3);
    private static final Double MEGA = Math.pow(10, 6);
    private static final Double GIGA = Math.pow(10, 9);
    private static final Double TERA = Math.pow(10, 12);
    private static final String STRING_KILO = " KH/s";
    private static final String STRING_MEGA = " MH/s";
    private static final String STRING_GIGA = " GH/s";
    private static final String STRING_TERA = " TH/s";
    private static final String LOG_TAG = "MY_LOG: " + ConverterHashrate.class.getSimpleName();


    public static ConverterHashrate getInstance() {
        return ourInstance;
    }

    private ConverterHashrate() {
    }

    public String convertDoubleHashRateToString(Double number) {
        BigDecimal resultBD;
        String resultStr = "";

        Log.d(LOG_TAG, "convertDoubleHashRateToString => number = " + number);

        if(number == null)
            return "";

        if(KILO <= number && number < MEGA){
            resultBD = new BigDecimal(number / KILO).setScale(1, RoundingMode.UP);
            resultStr += resultBD.toString() + STRING_KILO;
        } else if(MEGA <= number && number < GIGA) {
            resultBD = new BigDecimal(number / MEGA).setScale(1, RoundingMode.UP);
            resultStr += resultBD.toString() + STRING_MEGA;
        } else if(GIGA <= number && number < TERA) {
            resultBD = new BigDecimal(number / GIGA).setScale(1, RoundingMode.UP);
            resultStr += resultBD.toString() + STRING_GIGA;
        } else if(TERA <= number) {
            resultBD = new BigDecimal(number / TERA).setScale(1, RoundingMode.UP);
            resultStr += resultBD.toString() + STRING_TERA;
        }

        Log.d(LOG_TAG, "convertDoubleHashRateToString => resultString = " + resultStr);

        return resultStr;
    }

    public Double convertStringHashRateToDouble(String strNumber) {
        Pattern p = Pattern.compile("\\s[G-T]H/s");
        Pattern p2 = Pattern.compile("[0-9]*[.,]?[0-9]+");

        Matcher m = p.matcher(strNumber);
        Matcher m2 = p2.matcher(strNumber);

        Double doubleNumber = 0D;

        Log.d(LOG_TAG, "convertStringHashRateToDouble => stringNumber = " + strNumber);

        if(m.find() && m2.find()) {
            doubleNumber = Double.valueOf(strNumber.substring(m2.start(), m2.end()));

            switch (strNumber.substring(m.start(), m.end())){
                case STRING_KILO:
                    doubleNumber *= KILO;
                    break;
                case STRING_MEGA:
                    doubleNumber *= MEGA;
                    break;
                case STRING_GIGA:
                    doubleNumber *= GIGA;
                    break;
                case STRING_TERA:
                    doubleNumber *= TERA;
            }
        }

        Log.d(LOG_TAG, "convertStringHashRateToDouble => doubleNumber = " + doubleNumber);

        return doubleNumber;
    }
}
