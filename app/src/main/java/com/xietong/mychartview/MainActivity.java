package com.xietong.mychartview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_barChart;
    private Button btn_slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        btn_barChart = (Button) findViewById(R.id.btn_barChart);
        btn_slider = (Button) findViewById(R.id.btn_slider);

        btn_barChart.setOnClickListener(this);
        btn_slider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_barChart:
                startActivity(new Intent(this,BarChartActivity.class));
                break;
            case R.id.btn_slider:
                startActivity(new Intent(this,SliderActivity.class));
                break;
        }
    }
}
