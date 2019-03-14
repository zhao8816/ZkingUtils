package com.zking.utool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zking.utools.LogUtils;
import com.zking.utools.ToastUtil;
import com.zking.utools.ZkUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLog = findViewById(R.id.btn_log);
        Button btnToast = findViewById(R.id.btn_toast);
        btnLog.setOnClickListener(this);
        btnToast.setOnClickListener(this);
        ZkUtils.init(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log:
                LogUtils.eHttp("123");
                LogUtils.wNormal("0000");
                break;
            case R.id.btn_toast:
                ToastUtil.init(false);
                ToastUtil.showShort(MainActivity.this,"3344");
                break;
            default:
                break;
        }
    }
}
