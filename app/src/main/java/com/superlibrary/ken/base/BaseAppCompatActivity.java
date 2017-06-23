package com.superlibrary.ken.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.ButterKnife;

/**
 * Created by PC_WLT on 2017/4/6.
 * 1.集成ButterKnife
 * 2.仿照Ios，点击编辑框以外，自动隐藏软键盘（自动）
 * 3.基本跳转简化 （open(Activity)）
 *
 * butterKnife 工具类不能放在Library中，否则无法使用 （需要在项目app中集成环境）
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(resId());TAG=this.getClass().getSimpleName();
        ButterKnife.bind(this);
        initData();
    }

    protected abstract int resId();
    protected abstract void initData();

    /**
     *     // TODO: 2017/3/22  by wlt
     * 仿照iso，编辑框点击其他区域，隐藏软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Bugtags.onDispatchTouchEvent(this, ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


    /**
     * 通过new Intent(Context packageContext, Class<?> pClass)跳转到pClass页面
     *
     * @param pClass  ：要跳转的activity页面
     * @param pBundle :通过Bundle传递参数
     * @param uri     ：通过setData传递URI
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        openActivity(pClass, pBundle, null);
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过new Intent(String action)进行指定跳转
     *
     * @param pAction ：跳转的action动作名
     * @param pBundle ：要传递的参数
     * @param uri     ：要传递的URI(.getData())
     */
    protected void openActivity(String pAction, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    protected void openActivity(String pAction, Bundle pBundle) {
        openActivity(pAction, pBundle, null);
    }

    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 接收传递过来的Intent对象
     *
     * @return：Intent对象
     */
    protected Intent getPrevIntent() {
        return getIntent();
    }

    /**
     * 直接接收Intent对象中的Bundle对象，getgetExtras();
     *
     * @return
     */
    protected Bundle getPrevExtras() {
        return getPrevIntent().getExtras();
    }


    /**
     * 直接传送Bundle
     * @param b
     * @param resultCode
     */
    protected void openActivityForResult(Bundle b,int resultCode){
        Intent intent = new Intent();
        if (b != null) {
            intent.putExtras(b);
        }
        startActivityForResult(intent,resultCode);
    }

    /**
     * 关闭该activity
     */
    public void finishDefault() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
