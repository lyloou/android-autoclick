package com.lyloou.demo1;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

/**
 * @author gaok
 * @description 自动点击AccessibilityService版
 * @data 2018年1月23日 下午9:25:44
 */
public class AutoClickService extends AccessibilityService {
    private static final String TAG = "TTAG";
    public static final String CAN_CLICKED_ID = "com.lagou.education:id/iv_resume_video";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        try {
            AccessibilityNodeInfo rootInfo = getRootInActiveWindow();
            monitor(rootInfo);
        } catch (Exception e) {
            ztLog("Exception:" + e.getMessage(), true);
        }
    }

    @Override
    public void onInterrupt() {
    }

    @SuppressLint("NewApi")
    private void monitor(AccessibilityNodeInfo rootInfo) {
        if (rootInfo == null || TextUtils.isEmpty(rootInfo.getClassName())) {
            return;
        }

        List<AccessibilityNodeInfo> accessibilityNodeInfosByViewId = rootInfo.findAccessibilityNodeInfosByViewId(CAN_CLICKED_ID);
        if (!accessibilityNodeInfosByViewId.isEmpty()) {
            Log.i(TAG, "---->" + accessibilityNodeInfosByViewId.size());
            performClick(accessibilityNodeInfosByViewId.get(0));
        }

    }

    private void performClick(AccessibilityNodeInfo targetInfo) {
        targetInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    private void ztLog(String str) {
        ztLog(str, false);
    }

    private void ztLog(String str, boolean showToast) {
        Log.i(TAG, str);
        if (showToast) {
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceConnected() {
        Log.i(TAG, "str");
    }

}
