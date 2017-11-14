package ken.superlibrary.com.appdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ken.superlibrary.com.appdemo.R;


/**
 * Created by PC_WLT on 2017/5/11.
 */

public class TestFragment extends Fragment {

    public TestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_base,container,false);
    }

}
