package com.lyloou.demo1;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

import static com.lyloou.demo1.Const.DEFAULT_CLICKED_ID;
import static com.lyloou.demo1.Const.KEY_STATUS;
import static com.lyloou.demo1.Const.KEY_VIEW_ID;
import static com.lyloou.demo1.Const.SP_NAME;

/**
 * @author gaok
 * @author lyloou
 * @description 自动点击AccessibilityService版
 * @data 2018年1月23日 下午9:25:44
 * @data 2020年8月22日 下午16:06:44
 */
public class AutoClickService extends AccessibilityService {
    private static final String TAG = "TTAG";

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

        SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        boolean aBoolean = sp.getBoolean(KEY_STATUS, false);
        ztLog("状态：" + aBoolean);
        if (!aBoolean) {
            return;
        }

        String viewId = sp.getString(KEY_VIEW_ID, DEFAULT_CLICKED_ID);
        ztLog("想要点击：" + viewId);
        List<AccessibilityNodeInfo> accessibilityNodeInfosByViewId = rootInfo.findAccessibilityNodeInfosByViewId(viewId);
        if (!accessibilityNodeInfosByViewId.isEmpty()) {
            performClick(accessibilityNodeInfosByViewId.get(0));
            ztLog("自动点击了", true);
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
