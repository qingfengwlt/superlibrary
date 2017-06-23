package com.superlibrary.ken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author leitao
 * @fileName pkg.shi.com.model.bean
 * @date 2016/4/20
 * @update by     on
 */
public class MModelImpl<T> {

    public void loadData(Call call, final int position, final OnLoadDataListener listener){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    listener.onSuccess(response.body().string(),position);
                } catch (IOException e) {
                    listener.onFailure("请保持网络通畅"+e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure("请保持网络通畅"+t.getMessage());
            }
        });
    }

    public void loadData(Observable observable, final int position, final OnLoadDataListener listener){
        observable.subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                    listener.onSuccess(o,position);
            }
        });
    }
    public interface OnLoadDataListener{
        void onSuccess(Object response, int position);
//        void onSuccess(String response, int position);
        void onFailure(String msg);
    }

}
