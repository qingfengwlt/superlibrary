package ken.superlibrary.com.appdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author leitao
 * @fileName pkg.shi.com.util
 * @date 2016/4/13
 * @update by     on
 */
public class ToolUtil {

    /** 获取手机唯一标示*/
    public static  String getPhoneImei(Context context){
       return  Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static RequestBody getRequesBody(Map map){
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Log.d("json", "mapToJson = " + JsonSwitch.toJson(map));
        return RequestBody.create(mediaType, JsonSwitch.toJson(map));
    }

    /**
     * 是否有网络
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.i("NetWorkState", "Unavailabel");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.i("NetWorkState", "Availabel");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //保留2位小数
    public static double get2Double(double a){
        DecimalFormat df=new DecimalFormat("0.00");
        return new Double(df.format(a).toString());
    }
}
