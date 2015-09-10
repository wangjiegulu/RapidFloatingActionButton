package com.wangjie.rapidfloatingactionbutton.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingButtonSeparateListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/4/15.
 */
public class SeparateRFABSampleActivity extends AppCompatActivity implements OnRapidFloatingButtonSeparateListener {
    private RapidFloatingActionButton rfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.separate_rfab_sample);
        rfab = (RapidFloatingActionButton) findViewById(R.id.separate_rfab_sample_rfab);
        rfab.setOnRapidFloatingButtonSeparateListener(this);
    }

    @Override
    public void onRFABClick() {
        Toast.makeText(this, "RFAB clicked", Toast.LENGTH_SHORT).show();
    }
}
