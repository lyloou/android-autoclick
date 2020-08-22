package com.lyloou.demo1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.lyloou.demo1.Const.DEFAULT_CLICKED_ID;
import static com.lyloou.demo1.Const.KEY_VIEW_ID;
import static com.lyloou.demo1.Const.SP_NAME;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        final TextView tvStatus = findViewById(R.id.tv_status);
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        TextView tvSetting = findViewById(R.id.tv_to_setting);
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
        final EditText etViewId = findViewById(R.id.et_view_id);
        SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        String viewId = sp.getString(KEY_VIEW_ID, DEFAULT_CLICKED_ID);
        etViewId.setText(viewId);

        TextView tvOk = findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
                sp.edit().putString(KEY_VIEW_ID, etViewId.getText().toString()).apply();
                Toast.makeText(getContext(), "已保存", Toast.LENGTH_SHORT).show();
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
