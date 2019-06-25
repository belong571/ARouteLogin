package com.example.aroutelogin.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.aroutelogin.R;
import com.example.aroutelogin.interceptor.LoginNavigationCallbackImpl;
import com.example.aroutelogin.interfaces.RoutePath;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_first).setOnClickListener(this);
        findViewById(R.id.btn_second).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                ARouter.getInstance().build(RoutePath.LOGIN_PATH)
                        .navigation();

                break;
            case R.id.btn_first: // 不需要登录的
                ARouter.getInstance().build(RoutePath.FIRST_PATH)
                        .withString("msg", "ARouter传递过来的不需要登录的参数msg")
                        .navigation();

                break;
            case R.id.btn_second: // 需要登录的
                ARouter.getInstance().build(RoutePath.SECOND_PATH)
                        .withString("msg", "ARouter传递过来的需要登录的参数msg")
                        .navigation(this,new LoginNavigationCallbackImpl());
                break;
            case R.id.btn_exit: // 退出登录
                ToastUtils.showShort("退出登录成功");
                SPUtils.getInstance().remove(RoutePath.SP_IS_LOGIN);
                break;
        }
    }
}
