package com.example.wkftv;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.Bugly;


public class BaseApplacation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplicationContext(), "c3d84c9d76", false);
        StringBuffer param = new StringBuffer();
        param.append("appid="+"5de89431");
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(BaseApplacation.this, param.toString());
     //   Beta.upgradeCheckPeriod = 60000;

    }
}
