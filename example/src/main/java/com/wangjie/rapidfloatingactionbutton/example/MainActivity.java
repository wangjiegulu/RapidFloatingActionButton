package com.wangjie.rapidfloatingactionbutton.example;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.present.AIActionBarActivity;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButtonHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;

import java.util.ArrayList;
import java.util.List;

@AILayout(R.layout.activity_main)
public class MainActivity extends AIActionBarActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentListener {

    @AIView(R.id.activity_main_rfal)
    private RapidFloatingActionLayout rfaLayout;
    @AIView(R.id.activity_main_rfab)
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionButtonHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>().setLabel("item1").setResId(R.drawable.ic_launcher).setWrapper(0));
        items.add(new RFACLabelItem<Integer>().setLabel("item2").setResId(R.drawable.ic_launcher).setWrapper(1));
        items.add(new RFACLabelItem<Integer>().setLabel("item3longlabel").setResId(R.drawable.ic_launcher).setWrapper(2));
        items.add(new RFACLabelItem<Integer>().setLabel("item4").setResId(R.drawable.ic_launcher));
        rfaContent.setItems(items);

        rfabHelper = new RapidFloatingActionButtonHelper(
                context,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked label: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked icon: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }
}
