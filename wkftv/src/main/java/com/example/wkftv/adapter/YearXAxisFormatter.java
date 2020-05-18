package com.example.wkftv.adapter;

import android.util.Log;

import com.example.wkftv.DataBean;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Philipp Jahoda on 14/09/15.
 */
@SuppressWarnings("unused")
public class YearXAxisFormatter extends ValueFormatter
{

    List<String > dataBeans;

    public YearXAxisFormatter(List<String> dataBeans) {
        // take parameters to change behavior of formatter
        this.dataBeans=dataBeans;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {

        Log.i("value-->",value+"");
        return dataBeans.get((int)value);
    }


}
