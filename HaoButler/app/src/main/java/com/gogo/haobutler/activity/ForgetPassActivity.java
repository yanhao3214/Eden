package com.gogo.haobutler.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.user.User;
import com.gogo.haobutler.activity.base.BaseActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author: 闫昊
 * @date: 2018/4/19
 * @function:
 */

public class ForgetPassActivity extends BaseActivity {
    private EditText edtEmail;
    private Button btnReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_layout);
        initView();
    }

    private void initView() {
        edtEmail = findViewById(R.id.edt_reset_pass);
        findViewById(R.id.btn_by_email).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_by_email:
                String email = edtEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgetPassActivity.this,
                            getString(R.string.input_no_empty), Toast.LENGTH_SHORT).show();
                } else {
                    User.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPassActivity.this,
                                        getString(R.string.email_send_ok), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPassActivity.this,
                                        getString(R.string.email_send_error) + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }
}
