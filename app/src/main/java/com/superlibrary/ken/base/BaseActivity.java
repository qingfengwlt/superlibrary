package com.superlibrary.ken.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import pc.wlt.com.superlibrary.R;
import pc.wlt.com.superlibrary.widget.TitleBar;

/**
 * Created by PC_WLT on 2017/3/27.
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{


    protected boolean isImmersive=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        initNavigationBar();
        setContentView(resId());
//        ButterKnife.bind(this);
        initUI();
        initListener();
        initData();
    }
    /** 返回布局layout_id */
    protected abstract int resId();
    /** 初始化Id*/
    protected abstract void initUI();
    /** 设置监听*/
    protected abstract void initListener();

    /** 初始化数据*/
    protected abstract void initData();

    /**
     * 初始化标题
     * @param titleBar
     * @param title
     */
    protected void initTitleBar(TitleBar titleBar, String title){
        initTitleBar(titleBar,"", R.drawable.back_green,title,"",-1);
    }

    /**
     * 初始化标题
     * @param titleBar
     * @param lefttext
     * @param leftImgId
     * @param title
     */
    protected void initTitleBar(TitleBar titleBar, String lefttext, int leftImgId, String title){
         initTitleBar(titleBar,lefttext,leftImgId,title,"",-1);
    }
    /**
     * 初始化标题
     * @param titleBar
     * @param lefttext
     * @param leftImgId
     * @param title
     * @param rightText
     * @param rightImgId
     */
    protected void initTitleBar(TitleBar titleBar, String lefttext, int leftImgId, String title, String rightText, int rightImgId) {
        titleBar.setImmersive(isImmersive);
        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));
        if (leftImgId!=-1){
            titleBar.setLeftImageResource(leftImgId);
        }
        titleBar.setLeftText(lefttext);
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleBar.setTitle(title);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);
//        titleBar.addAction(new TitleBar.ImageAction(rightImgId) {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(BaseActivity.this, "点击了收藏", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        titleBar.addAction(new TitleBar.TextAction(rightText) {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(BaseActivity.this, "点击了发布", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 初始化导航栏
     */
    private void initNavigationBar() {
        if (hasKitKat() && !hasLollipop()) {
            isImmersive = true;
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
        }
    }

    /**
     * sdk<19
     * @return
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * >20
     * @return
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

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
