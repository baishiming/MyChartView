package com.xietong.mychartview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BarChartView barChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Integer> mData = Arrays.asList(0, 76, 90, 50, 187);
        List<String> xList = Arrays.asList("1月份", "2月份", "3月份", "4月份", "5月份");
        barChartView = findViewById(R.id.barChartView);
        barChartView.updateValueData(mData,xList,mData);
    }
}
