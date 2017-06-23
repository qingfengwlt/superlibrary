package com.superlibrary.ken.utils;

import android.os.CountDownTimer;
import android.widget.TextView;


/**
 * Created by PC_WLT on 2017/5/10.
 *
 * 时分秒--倒计时
 * 依赖于TextView存在
 */

public abstract class AutoCountDownTimer extends CountDownTimer{
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    private String format_hh_mm_ss = "HH:mm:ss";

    private TextView mTextView;

    public AutoCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public AutoCountDownTimer(long millisInFuture, long countDownInterval, String dataformat) {
        super(millisInFuture, countDownInterval);
        this.format_hh_mm_ss=dataformat;
    }

    public void setmTextView(TextView textView){
        this.mTextView=textView;
    }

    public String getFormat_hh_mm_ss() {
        return format_hh_mm_ss;
    }

    public void setFormat_hh_mm_ss(String format_hh_mm_ss) {
        this.format_hh_mm_ss = format_hh_mm_ss;
    }

    @Override
    public  void onTick(long millisUntilFinished){
            if (mTextView!=null){
               mTextView.setText(formatTime(millisUntilFinished));
            }
    }

    @Override
    public void onFinish() {
    }
    public CharSequence formatTime(long remainMillis) {

        String formatted = format_hh_mm_ss;

        long remainSec = remainMillis / 1000;

        long sec = remainSec % 60;

        long min = remainSec / 60 % 60;

        long hour = remainSec / 60 / 60 % 24;

        long day = remainSec / 60 / 60 / 24;

        if (formatted.contains("dd")) {
            if (day == 0) {
                int ddIndex = formatted.indexOf("dd");
                String ddStr = formatted.substring(ddIndex, ddIndex + 3);
                formatted = formatted.replace(ddStr, "");
            } else {
                formatted = formatted.replace("dd", formatNum(day));
            }
        } else {
            hour += day * 24;
        }

        if (formatted.contains("HH")) {
            formatted = formatted.replace("HH", formatNum(hour));
        } else {
            min += hour * 60;
        }

        if (formatted.contains("mm")) {
            formatted = formatted.replace("mm", formatNum(min));
        } else {
            sec += min * 60;
        }

        if (formatted.contains("ss")) {
            formatted = formatted.replace("ss", formatNum(sec));
        }

        return formatted;
    }

    private String formatNum(long num) {
        if (num > 9)
            return num + "";
        else
            return "0" + num;
    }
}
