package com.example.aroutelogin.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.aroutelogin.R;
import com.example.aroutelogin.base.BaseActivity;
import com.example.aroutelogin.interfaces.RoutePath;


@Route(path = RoutePath.SECOND_PATH)
public class SecondActivity extends BaseActivity {

    private TextView tvMsg;

    @Autowired
    public String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        tvMsg.setText(msg);

    }

    private void initView() {
        tvMsg = findViewById(R.id.tv_msg);
    }
}
