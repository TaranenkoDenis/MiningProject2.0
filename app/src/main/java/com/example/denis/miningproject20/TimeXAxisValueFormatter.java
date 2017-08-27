package com.example.denis.miningproject20;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by denis on 26.08.17.
 */

public class TimeXAxisValueFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date date = new Date((long) value);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d H:mm:ss");
        sdf.setTimeZone(TimeZone.getDefault());

        return sdf.format(date);
    }
}
