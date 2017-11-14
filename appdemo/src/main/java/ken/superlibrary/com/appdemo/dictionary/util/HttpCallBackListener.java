package ken.superlibrary.com.appdemo.dictionary.util;

import java.io.InputStream;

/**
 * Created by hxb on 2016/5/3.
 * Http访问的回调接口
 */
public interface HttpCallBackListener {
    /**
     * 当Http访问完成时回调onFinish方法
     */
    void onFinish(InputStream inputStream);
    /**
     * 当Http访问失败时回调onError方法
     */
    void onError();
}
