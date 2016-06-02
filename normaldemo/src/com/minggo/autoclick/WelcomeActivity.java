package com.minggo.autoclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.youmi.android.addemo.R;

/**
 * Created by minggo on 16/6/2.
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weclome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(WelcomeActivity.this,AutoClickActivity.class));
               finish();
            }
        },3000);
    }
}
