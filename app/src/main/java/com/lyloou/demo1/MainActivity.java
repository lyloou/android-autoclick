package com.lyloou.demo1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvStatus;
    TextView tvRefresh;
    TextView tvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStatus = findViewById(R.id.tv_status);
        tvRefresh = findViewById(R.id.tv_refresh);
        tvSetting = findViewById(R.id.tv_to_setting);
        tvStatus.setText(isServiceOpen() ? "已开启" : "已关闭");

        tvRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean serviceOpen = isServiceOpen();
                tvStatus.setText(serviceOpen ? "已开启" : "已关闭");
                if (serviceOpen) {// 判断服务是否开启
                    Toast.makeText(getContext(), "服务已开启", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAccessibilitySettingHelper.jumpToSettingPage(getContext());// 跳转到开启页面
            }
        });
    }

    private boolean isServiceOpen() {
        return OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(getContext(), AutoClickService.class.getName());
    }

    private MainActivity getContext() {
        return MainActivity.this;
    }
}
