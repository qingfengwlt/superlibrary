package com.superlibrary.ken.base;

import android.app.Application;


/**
 * Created by PC_WLT on 2017/4/26.
 */

public class BaseApplication extends Application {

    public static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        //讯飞语音
//        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=58636b76");
//        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//        OkHttpUtils.initClient(okHttpClient);

    }
}
