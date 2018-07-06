package com.gogo.haobutler.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.phone.PhoneData;
import com.gogo.haobutler.activity.base.BaseActivity;
import com.gogo.haobutler.utils.Consts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: 闫昊
 * @date: 2018/5/23 0023
 * @function: 手机号归属地查询/自己开辟线程实现网络请求
 */
public class AttributionActivity extends BaseActivity {
    private EditText edtPhone;
    private CircleImageView civIcon;
    private TextView tvResult;
    private TextView tvNumber;
    private TextView tvProvince;
    private TextView tvCarrier;
    private TextView tvOwner;
    private PhoneHandler mPhoneHandler = new PhoneHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribution_layout);
        initView();
    }

    private void initView() {
        edtPhone = findViewById(R.id.edt_phone);
        civIcon = findViewById(R.id.civ_carrier_icon);
        tvResult = findViewById(R.id.tv_result);
        tvNumber = findViewById(R.id.tv_number);
        tvProvince = findViewById(R.id.tv_province);
        tvCarrier = findViewById(R.id.tv_carrier);
        tvOwner = findViewById(R.id.tv_owner_carrier);

        Button btnQuery = findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                Animation animPhone = AnimationUtils.loadAnimation(this, R.anim.edit_phone_anim);
                edtPhone.startAnimation(animPhone);

                /**
                 * 1.输入不为空则隐藏输入键盘
                 * 2.获取号码转换为Url
                 * 3.获取json
                 * 4.解析json
                 * 5.更新UI
                 */
                String url;
                String phoneNumber = edtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(this, getString(R.string.input_no_empty),
                            Toast.LENGTH_SHORT).show();
                } else {
                    hideKeyboard(edtPhone);
                    url = Consts.BAIDU_PHONE_URL + "?tel=" + phoneNumber;
                    //解析“百度号码归属地API”返回的json数据需要用到电话号码
                    new PhoneThread(url, phoneNumber).start();
                }
                break;
            default:
                break;
        }
    }

    class PhoneThread extends Thread {
        private String url;
        private String number;

        private PhoneThread(String url, String number) {
            this.url = url;
            this.number = number;
        }

        /**
         * 子线程不能直接弹出Toast(其实就是子线程更新UI)，方法：
         * 1. Looper.prepare(); Looper.loop();
         * 2. runOnUiThread(new Runnable(){});
         * 3. mHandler.senMessage();
         */
        @Override
        public void run() {
            String result = getJsonFromURL(url);
            if (TextUtils.isEmpty(result)) {
                Looper.prepare();
                Toast.makeText(AttributionActivity.this, R.string.attribution_return_null,
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                PhoneData phone = parseJsonToPhone(result, number);
                Message msg = Message.obtain();
                msg.obj = phone;
                mPhoneHandler.sendMessage(msg);
            }
        }
    }

    private static class PhoneHandler extends Handler {
        private WeakReference<Context> reference;

        PhoneHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            PhoneData phone = (PhoneData) msg.obj;
            AttributionActivity activity = (AttributionActivity) reference.get();
            activity.tvResult.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.tv_phone_result_anim));
            activity.tvNumber.setText(phone.getNumber());
            activity.tvProvince.setText(phone.getProvince());
            activity.tvCarrier.setText(phone.getCarrier());
            activity.tvOwner.setText(phone.getOwnerCarrier());
            if (phone.getCarrier() == null) {
                Toast.makeText(activity, R.string.attribution_no_result, Toast.LENGTH_SHORT)
                        .show();
                activity.civIcon.setImageResource(R.drawable.icon_question_phone);
            } else {
                switch (phone.getCarrier()) {
                    case "移动":
                        activity.civIcon.setImageResource(R.drawable.icon_mobile_phone);
                        break;
                    case "电信":
                        activity.civIcon.setImageResource(R.drawable.icon_telecom_phone);
                        break;
                    case "联通":
                        activity.civIcon.setImageResource(R.drawable.icon_unicom_phone);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private String getJsonFromURL(String urlStr) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new URL(urlStr).openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("yh", "getJsonFromURL: " + sb.toString());
        return sb.toString();
    }

    /**
     * 将获取的json字符串转换为Phone对象
     *
     * @param url
     * @param number
     * @return
     */
    private PhoneData parseJsonToPhone(String url, String number) {
        PhoneData phone = new PhoneData();
        try {
            JSONObject object = new JSONObject(url);
            JSONObject numberObj = object.getJSONObject("response").getJSONObject(number);
            JSONObject detailObj = numberObj.getJSONObject("detail");
            phone.setNumber(number);
            phone.setProvince(detailObj.getString("province"));
            phone.setCarrier(detailObj.getString("operator"));
            phone.setOwnerCarrier(numberObj.getString("location"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return phone;
    }

    private void hideKeyboard(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive(view)) {
            im.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    private PhoneData parseJsonByFastJson(String t) {
        return new PhoneData();
    }

    private PhoneData parseJsonByGson(String t) {
        return new PhoneData();
    }
}
