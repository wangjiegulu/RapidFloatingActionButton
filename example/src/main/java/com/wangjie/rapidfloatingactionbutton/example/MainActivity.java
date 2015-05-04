package com.wangjie.rapidfloatingactionbutton.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.wangjie.androidinject.annotation.annotations.base.AIClick;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;

@AILayout(R.layout.activity_main)
public class MainActivity extends AIActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    @AIClick({R.id.activity_main_label_list_sample_btn, R.id.activity_main_separate_rfab_sample_btn})
    public void onClickCallbackSample(View view) {
        switch (view.getId()) {
            case R.id.activity_main_label_list_sample_btn:
                startActivity(new Intent(context, LabelListSampleActivity.class));
                break;
            case R.id.activity_main_separate_rfab_sample_btn:
                startActivity(new Intent(context, SeparateRFABSampleActivity.class));
                break;
            default:
                break;
        }
    }
}
