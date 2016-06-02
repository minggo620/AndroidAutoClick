package com.minggo.autoclick;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.youmi.android.addemo.R;

/**
 * Created by minggo on 16/6/2.
 */
public class MotionEventActivity extends Activity implements OnClickListener{

    private Button tagBt;
    private Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);
        initUI();
        start();
    }

    private void initUI(){
        tagBt = (Button) findViewById(R.id.bt_motionevent);
        tagBt.setOnClickListener(this);
        
        dialog = new Builder(this);
        dialog.setTitle("按钮已被点击");

    }

    private void start(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                setSimulateClick(tagBt,tagBt.getWidth()/2-tagBt.getX(),tagBt.getHeight()/2+tagBt.getY());

            }
        },1000);
    }

    //MotionEvent模拟点击
    private void setSimulateClick(View view, float x, float y) {

        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }



    @Override
    public void onClick(View v) {
        dialog.show();
    }
}
