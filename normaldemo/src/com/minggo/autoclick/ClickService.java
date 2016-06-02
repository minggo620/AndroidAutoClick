package com.minggo.autoclick;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Iterator;
import java.util.List;

/**
 * Created by minggo on 16/5/30.
 */
public class ClickService extends AccessibilityService {

    private ClickReceiver receiver;
    private static ClickService service;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //接收事件,如触发了通知栏变化、界面变化等
        Log.i("mService", "AccessibilityEvent按钮点击变化");
        //performClick();
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {

        Log.i("mService", "按钮点击变化");

        //接收按键事件
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {
        Log.i("mService", "授权中断");
        //服务中断，如授权关闭或者将服务杀死
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i("mService", "service授权成功");
        service = this;
        //连接服务后,一般是在授权成功后会接收到
        if (receiver == null) {
            receiver = new ClickReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("auto.click");
            registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    //执行返回
    public void performBack() {
        Log.i("mService", "执行返回");
        this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    //执行点击
    private void performClick() {

        Log.i("mService","点击执行");

        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        //通过名字获取
        //targetNode = findNodeInfosByText(nodeInfo,"广告");
        //通过id获取
        targetNode = findNodeInfosById(nodeInfo,"com.haozhi.projectb:id/bt_browser");
        if (targetNode.isClickable()) {
            targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    //执行点击
    private void performClick(String resourceId) {

        Log.i("mService","点击执行");

        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        targetNode = findNodeInfosById(nodeInfo,"com.youmi.android.addemo:id/"+resourceId);
        if (targetNode.isClickable()) {
            targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }


    //通过id查找
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if(list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    //通过文本查找
    public static AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 判断当前服务是否正在运行
     * */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isRunning() {
        if(service == null) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) service.getSystemService(Context.ACCESSIBILITY_SERVICE);
        AccessibilityServiceInfo info = service.getServiceInfo();
        if(info == null) {
            return false;
        }
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        Iterator<AccessibilityServiceInfo> iterator = list.iterator();

        boolean isConnect = false;
        while (iterator.hasNext()) {
            AccessibilityServiceInfo i = iterator.next();
            if(i.getId().equals(info.getId())) {
                isConnect = true;
                break;
            }
        }
        if(!isConnect) {
            return false;
        }
        return true;
    }

    public class ClickReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int i = intent.getIntExtra("flag", 0);
            Log.i("mService","广播flag="+i);
            if (i == 1) {
                String resourceid = intent.getStringExtra("id");
                performClick(resourceid);
            }

        }

    }


}
