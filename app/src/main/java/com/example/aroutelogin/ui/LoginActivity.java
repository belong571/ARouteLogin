package com.example.aroutelogin.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.aroutelogin.R;
import com.example.aroutelogin.base.BaseActivity;
import com.example.aroutelogin.interfaces.RoutePath;

@Route(path = RoutePath.LOGIN_PATH)
public class LoginActivity extends BaseActivity {


    @Autowired
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put(RoutePath.SP_IS_LOGIN, true);

                ToastUtils.showShort("登录成功");
                if (!StringUtils.isEmpty(path)) {
                    ARouter.getInstance().build(path)
                            .with(getIntent().getExtras())
                            .navigation();
                }
                finish();
            }
        });
    }
}
