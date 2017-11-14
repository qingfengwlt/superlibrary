package ken.superlibrary.com.appdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.superlibrary.ken.base.BaseFragment;

import ken.superlibrary.com.appdemo.R;


/**
 * Created by PC_WLT on 2017/5/12.
 *
 * 组合题
 */

public class CombinationSelectionFG extends BaseFragment {
    Fragment fragment;
    @Override
    protected int resLayout() {
        return R.layout.fg_combination_selection;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDefaultFragment();

    }
    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fragment  = new SingleSelectionFG();
        transaction.replace(R.id.id_content, fragment);
        transaction.commit();
    }
}
