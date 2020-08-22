package com.lyloou.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvStart;
    TextView tvStop;
    TextView tvSetting;
    TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStart = (TextView) findViewById(R.id.tv_start);
        tvStop = (TextView) findViewById(R.id.tv_stop);
        tvSetting = (TextView) findViewById(R.id.tv_to_setting);
        tvView = (TextView) findViewById(R.id.tv_view);
        tvStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("TTAG", "开启服务");
                Intent i = new Intent(getContext(), AutoClickService.class);
                startService(i);
            }
        });

        tvStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("TTAG", "关闭服务");
                Intent i = new Intent(getContext(), AutoClickService.class);
                stopService(i);
                tvStart.setText("开启服务");
            }
        });

        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(getContext(), AutoClickService.class.getName())) {// 判断服务是否开启
                    OpenAccessibilitySettingHelper.jumpToSettingPage(getContext());// 跳转到开启页面
                } else {
                    Toast.makeText(getContext(), "服务已开启", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private MainActivity getContext() {
        return MainActivity.this;
    }

    /**
     * 打印点击的点的坐标
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        tvView.setText("X at " + x + ";Y at " + y);
        return true;
    }
}
