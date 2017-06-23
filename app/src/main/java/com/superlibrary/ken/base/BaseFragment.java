package com.superlibrary.ken.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PC_WLT on 2017/5/12.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return resLayout()==0?super.onCreateView(inflater, container, savedInstanceState):inflater.inflate(resLayout(),container,false);
    }
    protected abstract int resLayout();

}
