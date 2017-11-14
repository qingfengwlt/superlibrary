package ken.superlibrary.com.appdemo.activity;

import android.util.Log;

import com.superlibrary.ken.MPresenter;
import com.superlibrary.ken.MView;
import com.superlibrary.ken.base.BaseAppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ken.superlibrary.com.appdemo.R;
import ken.superlibrary.com.appdemo.enity.ComBean;
import ken.superlibrary.com.appdemo.utils.RetrofitManager;
import ken.superlibrary.com.appdemo.utils.ToastUtils;
import ken.superlibrary.com.appdemo.utils.ToolUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OKHttpActivity extends BaseAppCompatActivity implements MView {

    MPresenter mPresenter;
    @Override
    protected int resId() {
        return R.layout.activity_okhttp;
    }

    @Override
    protected void initData() {
        mPresenter=new MPresenter(this);
        Map map=new HashMap();
        map.put("xsid","100011");
        mPresenter.loadData(RetrofitManager.getInstance().test4(ToolUtil.getRequesBody(map)),0);
//        requestData();
//        requestData1();
//        requestData3();
//        requestData4();

    }

    private void requestData4() {
        Map map=new HashMap();
        map.put("xsid","100011");
        RetrofitManager.getInstance().test4(ToolUtil.getRequesBody(map)).subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ComBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ComBean t) {

            }
        });
    }
    private void requestData3() {
        Map map=new HashMap();
        map.put("xsid","100011");
        RetrofitManager.getInstance().test3(ToolUtil.getRequesBody(map)).subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread()).subscribe(new rx.Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                Log.d(TAG,responseBody.toString());
                String json= null;
                try {
                    json = responseBody.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ToastUtils.showToast(OKHttpActivity.this,"json=="+json);
            }
        });
    }

    private void requestData2() {
        Map map=new HashMap();
        map.put("xsid","100011");
        RetrofitManager.getInstance().test2("100011").subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread()).subscribe(new rx.Observer<ComBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,e.getMessage());
            }

            @Override
            public void onNext(ComBean responseBody) {
                Log.d(TAG,responseBody.toString());
                String json=responseBody.toString();
                ToastUtils.showToast(OKHttpActivity.this,"json=="+json);
            }
        });

    }

    private void requestData1() {
        String url = "http://dict-co.iciba.com/api/dictionary.php?";
//        OkHttpUtils
//                .get()
//                .url(url)
//                .addParams("w", "hello")
//                .addParams("type","json")
//                .addParams("key","E0FC9F9D320339857E9AE68FA7167AA3")
//                .build()
//                .execute(new StringCallback() {
//                             @Override
//                             public void onError(okhttp3.Call call, Exception e, int id) {
//                                 Log.e(TAG,e.getMessage());
//                             }
//
//                             @Override
//                             public void onResponse(String response, int id) {
//                                 Log.e(TAG,response);
//
//                             }
//                         }
//                );
    }

    private void requestData() {
        String  auth="E0FC9F9D320339857E9AE68FA7167AA3";
        Call call= RetrofitManager.getInstance().getData("hello","json",auth);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG,"onResponse");
                String json=response.body().toString();
//                mTvTest.setText(json);
                Log.d(TAG,"json="+json);
                call.cancel();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG,"onResponse");
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg(String msg) {

    }

    @Override
    public void refreshData(Object response, int position) {

        ComBean bean= (ComBean) response;
        ToastUtils.showToast(this,bean.toString());
        Log.d(TAG,bean.toString());
    }

}
