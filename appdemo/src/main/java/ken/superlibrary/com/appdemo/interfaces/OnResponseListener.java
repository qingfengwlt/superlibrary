package ken.superlibrary.com.appdemo.interfaces;

/**
 * Created by PC_WLT on 2017/4/26.
 */

public interface OnResponseListener {

    abstract void onSuccess(int code, String str);
    abstract void onFail(int code, Exception e);
}
