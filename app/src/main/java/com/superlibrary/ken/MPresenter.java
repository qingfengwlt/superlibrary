package com.superlibrary.ken;


import io.reactivex.Observable;
import retrofit2.Call;

/**
 * @author leitao
 * @fileName pkg.shi.com.presenter
 * @date 2016/4/20
 * @update by     on
 */
public class MPresenter<T> implements MModelImpl.OnLoadDataListener{

    MView<T> mView;
    MModelImpl<T> mModel;

    public MPresenter(MView<T> mView) {
        this.mView = mView;
        this.mModel=new MModelImpl<>();
    }

    public void loadData(Call call, int position){
        mView.showProgress();
        mModel.loadData(call,position,this);
    }
    public void loadData(Observable observable, int position){
        mView.showProgress();
        mModel.loadData(observable,position,this);
    }
    @Override
    public void onSuccess(Object response,int position) {
        mView.showProgress();
        mView.refreshData(response,position);

    }

    @Override
    public void onFailure(String msg) {
        mView.hideProgress();
        mView.showLoadFailMsg(msg);
    }
}
