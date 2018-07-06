package com.gogo.haobutler.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.user.User;
import com.gogo.haobutler.activity.base.BaseActivity;
import com.gogo.haobutler.utils.Consts;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author: 闫昊
 * @date: 2018/4/17
 * @function:
 */

public class RegisteredActivity extends BaseActivity {
    private EditText edtName;
    private EditText edtAge;
    private EditText edtDesc;
    private EditText edtPassword;
    private EditText edtPasswordAgain;
    private EditText edtEmail;
    private RadioGroup rgGender;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private TextView tvEmailVerify;
    private Button btnRegister;
    private int userGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        initView();
    }

    private void initView() {
        edtName = findViewById(R.id.edt_username);
        edtAge = findViewById(R.id.edt_age);
        edtDesc = findViewById(R.id.edt_description);
        edtPassword = findViewById(R.id.edt_password);
        edtPasswordAgain = findViewById(R.id.edt_password_again);
        edtEmail = findViewById(R.id.edt_email);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        tvEmailVerify = findViewById(R.id.tv_email_verify);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        rgGender.setOnCheckedChangeListener(new OnGenderListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                saveUserBmob();
                break;
            default:
                break;
        }
    }

    /**
     * 获取用户信息，并上保存到Bmob云后台
     */
    private void saveUserBmob() {
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String desc = edtDesc.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String passwordAgain = edtPasswordAgain.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(desc)) {
            desc = getResources().getString(R.string.auto_user_desc);
        }
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(passwordAgain) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(passwordAgain)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User();
            user.setUsername(name);
            user.setAge(Integer.parseInt(age));
            user.setDescription(desc);
            user.setPassword(password);
            user.setEmail(email);
            user.setGender(userGender);
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (null == e) {
                        Toast.makeText(RegisteredActivity.this,
                                "注册成功: user=" + user.toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisteredActivity.this,
                                "注册失败: e：" + e.toString(), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
        }
    }

    private class OnGenderListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_male:
                    userGender = Consts.USER_MALE;
                    break;
                case R.id.rb_female:
                    userGender = Consts.USER_FEMALE;
                    break;
                default:
                    break;
            }
        }
    }
}
