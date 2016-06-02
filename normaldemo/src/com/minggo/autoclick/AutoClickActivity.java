package com.minggo.autoclick;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.youmi.android.addemo.MainActivity;
import com.youmi.android.addemo.R;

/**
 * Created by minggo on 16/6/2.
 */
public class AutoClickActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_click);
    }


    public void turnMotionEventActivty(View view){
        startActivity(new Intent(this,MotionEventActivity.class));
    }

    public void turnAccessilityActivity(View view){
        startActivity(new Intent(this,AccessibilityActivity.class));
    }

    public void turnYouMiAdActivity(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder dialog = new Builder(this);
        dialog.setTitle("退出提示");
        dialog.setMessage("您确定退出应用吗?");
        dialog.setNegativeButton("取消",null);
        dialog.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        dialog.show();

    }
}
