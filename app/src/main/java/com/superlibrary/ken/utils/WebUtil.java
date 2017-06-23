package com.superlibrary.ken.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;


/**
 * @version v1.0 <描述当前版本功能>
 * @fileName: pkg.shi.com.util.WebUtil.java
 * @author: leitao
 * @date 2016-06-06 11:05
 * @update by     on
 */
public class WebUtil {
    public WebView mWv;

    public static WebUtil instance() {
        return new WebUtil();
    }

    public void setWV(WebView wv, String url,OnWebProgressListener onWebProgressListener) {
      setWV(wv,url,WebSettings.LOAD_CACHE_ELSE_NETWORK,onWebProgressListener);
    }
    public void setWV(WebView wv, String url,int cacheMode,OnWebProgressListener onWebProgressListener) {
        this.mWv = wv;
        this.mOnWebProgressListener=onWebProgressListener;
        WebSettings webSettings = wv.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
//        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //页面缓存格式：当前是无缓存
        webSettings.setCacheMode(cacheMode);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没有网的时候，可以加载以前是页面
        //加载需要显示的网页
//        webSettings.setBlockNetworkImage(false);
//        webSettings.setBlockNetworkLoads(false);
        webSettings.setDomStorageEnabled(false);
        webSettings.setDatabaseEnabled(false);
        wv.loadUrl(url);


//        mWv.loadData("file：///android_asset/agreement.html","text/html", "utf-8");
//        mWv.loadUrl("http://192.168.2.138:58083/driver/sdbmNew.html");
//        file：///android_asset/index.html
//        http://www.baidu.com
//      设置Web视图
        wv.setWebViewClient(new webViewClient());
        wv.setWebChromeClient(new MyWebChromeClient());

    }


    public void getBack() {
        mWv.goBack();
    }

    public void canGoBack() {
        mWv.canGoBack();
    }

    public void clearHistory() {
        mWv.clearHistory();
    }

    //Web视图
    private class webViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mOnWebProgressListener!=null){
                mOnWebProgressListener.onWebProgressStart();
            }

        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            ToastUtils.showToast(mWv.getContext(),"同步失败，请稍候再试");
//            view.loadUrl("file:///android_asset/error.html");
//            mWebview.loadUrl("file:///android_asset/errorpage/error.html");
            if (mOnWebProgressListener!=null){
                mOnWebProgressListener.onWebProgressFinish();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            ToastUtils.showToast(mWv.getContext(),"同步失败，请稍候再试");
//            view.loadUrl("file:///android_asset/error.html");
            if (mOnWebProgressListener!=null){
                mOnWebProgressListener.onWebProgressFinish();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mOnWebProgressListener!=null){
                mOnWebProgressListener.onWebProgressFinish();
            }
        }
    }


    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mOnWebProgressListener!=null){
                mOnWebProgressListener.onWebTittleChange(title);
            }
        }


        /**
         * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
         */
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton("确定", null);

            // 不需要绑定按键事件
            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });
            // 禁止响应按back键的事件
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
            return true;
            // return super.onJsAlert(view, url, message, result);
        }

        /**
         * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
         */
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });
            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
            // return super.onJsConfirm(view, url, message, result);
        }

        /**
         * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
         * window.prompt('请输入您的域名地址', '618119.com');
         */
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("提示").setMessage(message);

            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                        }

                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsPrompt", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });

            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
            // return super.onJsPrompt(view, url, message, defaultValue,
            // result);
        }
    }

    OnWebProgressListener mOnWebProgressListener;
    public interface OnWebProgressListener {

        void onWebProgressStart();

        void onWebProgressFinish();

        void onWebTittleChange(String title);
    }
}
