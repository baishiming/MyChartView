package com.xietong.mychartview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SliderActivity extends AppCompatActivity {

    private TextView mTextView;
    private MySliderView mMySilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        mTextView = (TextView) findViewById(R.id.textview);
        mMySilder = (MySliderView) findViewById(R.id.mysilider);
        mMySilder.setOnItemSelectListener(new MySliderView.OnItemSelectListener() {
            @Override
            public void onItemSelect(int index, String indexString) {

                mTextView.setText(indexString);
            }
        });
    }
}
