package com.wangjie.rapidfloatingactionbutton.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.wangjie.rapidfloatingactionbutton.example.cardtest.CardListSampleActivity;
import com.wangjie.rapidfloatingactionbutton.example.rfabgroup.RFABGroupSampleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main_separate_rfab_sample_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_label_list_sample_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_rfab_group_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_card_list_sample_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_main_label_list_sample_btn:
                startActivity(new Intent(this, LabelListSampleActivity.class));
                break;
            case R.id.activity_main_separate_rfab_sample_btn:
                startActivity(new Intent(this, SeparateRFABSampleActivity.class));
                break;
            case R.id.activity_main_rfab_group_btn:
                startActivity(new Intent(this, RFABGroupSampleActivity.class));
                break;
            case R.id.activity_main_card_list_sample_btn:
                startActivity(new Intent(this, CardListSampleActivity.class));
                break;
            default:
                break;
        }
    }
}
