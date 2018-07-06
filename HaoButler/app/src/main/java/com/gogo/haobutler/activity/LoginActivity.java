package com.gogo.haobutler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.user.User;
import com.gogo.haobutler.activity.base.BaseActivity;
import com.gogo.haobutler.utils.ShareUtil;
import com.gogo.haobutler.view.HaoDialog;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author: 闫昊
 * @date: 2018/4/17
 * @function: 登录界面
 */

public class LoginActivity extends BaseActivity {
    private ImageView ivLogo;
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox chbPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextView tvForgetPassword;
    private List<String> list;
    private HaoDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        initData();
        initView();
    }

    /**
     * 未点击登录直接退出，也记住账户、密码
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareUtil.putBoolean(this, "autoPass", chbPassword.isChecked());
        if (ShareUtil.getBoolean(this, "autoPass", false)) {
            ShareUtil.putString(this, "username", edtUsername.getText().toString().trim());
            ShareUtil.putString(this, "password", edtPassword.getText().toString().trim());
        } else {
            //是否需要remove？
//            ShareUtil.remove(this, "username");
//            ShareUtil.remove(this, "password");
        }
    }

    private void initData() {
    }

    private void initView() {
        ivLogo = findViewById(R.id.iv_logo_login);
        edtUsername = findViewById(R.id.edt_username_login);
        edtPassword = findViewById(R.id.edt_password_login);
        chbPassword = findViewById(R.id.checkbox_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        tvForgetPassword = findViewById(R.id.tv_forget_password);
        dialog = new HaoDialog(this, 0, 0, R.layout.dialog_login_layout, R.style.theme_hao_dialog, Gravity.CENTER);
        dialog.setCancelable(false);
        Boolean autoPass = ShareUtil.getBoolean(this, "autoPass", false);
        chbPassword.setChecked(autoPass);

        /**
         * 增加setSelection()功能
         */
        if (autoPass) {
            edtUsername.setText(ShareUtil.getString(this, "username", ""));
            edtUsername.setSelection(0, edtUsername.getText().length());
            edtPassword.setText(ShareUtil.getString(this, "password", ""));
        }

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        chbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtil.putBoolean(LoginActivity.this, "autoPass", isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                String name = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    User user = new User();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            dialog.dismiss();
                            if (null == e) {
                                if (user.getEmailVerified()) {
                                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败：" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
//                    BmobUser.loginByAccount(name, password, new LogInListener<Object>());
                break;
            case R.id.btn_register:
//                CustomDialog customDialog = new CustomDialog(this, R.layout.dialog_login_layout);
//                customDialog.show();
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(this, ForgetPassActivity.class));
                break;
            default:
                break;

        }
    }
}
