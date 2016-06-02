package com.minggo.autoclick;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.youmi.android.addemo.R;

/**
 * Created by minggo on 16/6/2.
 */
public class AccessibilityActivity extends Activity implements OnClickListener{

    private Dialog mTipsDialog;
    //已经在xml设定了id为bt_accessibility
    private Button accessibilityBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);
        accessibilityBt = (Button) findViewById(R.id.bt_accessibility);
        accessibilityBt.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ClickService.isRunning()) {
            if(mTipsDialog != null) {
                mTipsDialog.dismiss();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent("auto.click");
                    intent.putExtra("flag",1);
                    intent.putExtra("id","bt_accessibility");

                    sendBroadcast(intent);
                }
            },1500);
        } else {
            showOpenAccessibilityServiceDialog();
        }
    }

    /** 显示未开启辅助服务的对话框*/
    private void showOpenAccessibilityServiceDialog() {
        if(mTipsDialog != null && mTipsDialog.isShowing()) {
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.dialog_tips_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccessibilityServiceSettings();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("需要开启辅助服务正常使用");
        builder.setView(view);
        builder.setPositiveButton("打开辅助服务", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAccessibilityServiceSettings();
            }
        });
        mTipsDialog = builder.show();
    }

    /** 打开辅助服务的设置*/
    private void openAccessibilityServiceSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "找[AutoClick],然后开启服务", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        new Builder(this).setTitle("按钮已被点击").show();
    }
}
