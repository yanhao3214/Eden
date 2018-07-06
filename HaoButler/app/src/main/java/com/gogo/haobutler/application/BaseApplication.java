package com.gogo.haobutler.application;

import android.app.Application;
import android.content.Intent;

import com.gogo.haobutler.utils.Consts;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.litepal.LitePalApplication;

import cn.bmob.v3.Bmob;

/**
 * @author: 闫昊
 * @date: 2018/4/12
 * @function: 项目初始化、参数配置
 */

public class BaseApplication extends LitePalApplication {
    public static RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), Consts.BUGLY_APP_ID, false);
        Bmob.initialize(this,Consts.BMOB_APP_ID);
        mRefWatcher = LeakCanary.install(this);
        initUMeng();
    }

    private void initUMeng() {

        /**
         * 参数1:上下文，不能为空
         * 参数2:设备类型：UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        //Log开关
        UMConfigure.setLogEnabled(true);
        //日志加密
        UMConfigure.setEncryptEnabled(true);
        //跟踪Activity执行轨迹
        MobclickAgent.openActivityDurationTrack(false);
    }
}
