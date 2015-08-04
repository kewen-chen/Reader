package org.gdgny.androidfan.reader;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RelativeLayout;

/**
 * Created by apple on 15/7/29.
 */
public class aboutActivity extends ActionBarActivity {
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_fan);
        relativeLayout =(RelativeLayout)findViewById(R.id.rl);
//        relativeLayout.setBackgroundColor(Color.parseColor("#2196f3"));
//        toolbar =(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }
}
