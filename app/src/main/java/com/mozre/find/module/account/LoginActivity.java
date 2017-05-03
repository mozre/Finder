package com.mozre.find.module.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mozre.find.R;
import com.mozre.find.app.BaseActivity;
import com.mozre.find.domain.User;
import com.mozre.find.module.main.HomeActivity;
import com.mozre.find.presenter.LoginPresenter;
import com.mozre.find.util.NetState;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {

    private static final String TAG = "LoginActivity";
    private EditText mEditTextLoginName;
    private EditText mEditTextLoginPassword;
    private Button mButtonLogin;
    private Button mButtonRegister;
    private Button mButtonLoginN;
    private ProgressDialog dialog;

    private void init() {
        mEditTextLoginName = (EditText) findViewById(R.id.login_view_login_name);
        mEditTextLoginPassword = (EditText) findViewById(R.id.login_view_login_password);
        mButtonLogin = (Button) findViewById(R.id.login_view_login);
        mButtonRegister = (Button) findViewById(R.id.login_view_register);
        mButtonLoginN = (Button) findViewById(R.id.login_view_login_n);
        mButtonLoginN.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("登陆提示");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("数据加载中");


//        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("登录");
        setContentView(R.layout.activity_login);
        this.init();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_view_login:
//                Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//                dialog.show();
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                dialog.dismiss();
//                startActivity(new Intent(this, HomeActivity.class));
                if (NetState.isNetWorkConnection(this)) {
                    Toast.makeText(LoginActivity.this, "网络可用", Toast.LENGTH_SHORT).show();
                }

                LoginPresenter loginPresenter = new LoginPresenter(getBaseContext(), this);
                loginPresenter.signIn(mEditTextLoginName.getText().toString(), mEditTextLoginPassword.getText().toString());

                break;
            case R.id.login_view_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_view_login_n:
                User user = new User(getBaseContext());
                user.setUserName("mozre");
                user.setUserIconAddress("E:\\usericon\\mozre.png");
                user.setPhone("231313");
                user.setToken("5ee69f48-3aed-4215-847d-b724e8732544");
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void errorUserInfo() {
        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startHomeView() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}

