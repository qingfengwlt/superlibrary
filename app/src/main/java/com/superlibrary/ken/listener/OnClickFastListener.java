package com.superlibrary.ken.listener;

import android.util.Log;
import android.view.View;

/**
 * Created by PC_WLT on 2017/6/21.
 * 防止按钮重复提交监听
 */

public abstract class OnClickFastListener extends BaseClickListener{

    // 防止快速点击默认等待时长为900ms
    private long DELAY_TIME = 2000;
    private static long lastClickTime;

    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 <= timeD && timeD < DELAY_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void onClick(View v) {
        // 判断当前点击事件与前一次点击事件时间间隔是否小于阙值
        Log.d("OnClickFastListener==",isFastDoubleClick()+"````````````````````");
        if (isFastDoubleClick()) {
            return;
        }

        onFastClick(v);
    }

    /**
     * 设置默认快速点击事件时间间隔
     * @param delay_time
     * @return
     */
    public OnClickFastListener setLastClickTime(long delay_time) {

        this.DELAY_TIME = delay_time;

        return this;
    }

    /**
     * 快速点击事件回调方法
     * @param v
     */
    public abstract void onFastClick(View v);
}
