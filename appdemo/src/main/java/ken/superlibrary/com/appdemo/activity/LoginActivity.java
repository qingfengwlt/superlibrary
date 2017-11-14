package ken.superlibrary.com.appdemo.activity;

import android.view.View;
import android.widget.Button;

import com.superlibrary.ken.MPresenter;
import com.superlibrary.ken.MView;
import com.superlibrary.ken.base.BaseAppCompatActivity;
import com.superlibrary.ken.utils.L;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ken.superlibrary.com.appdemo.R;
import ken.superlibrary.com.appdemo.utils.RetrofitManager;
import ken.superlibrary.com.appdemo.utils.ToolUtil;
import retrofit2.Call;

public class LoginActivity extends BaseAppCompatActivity implements MView {

    @BindView(R.id.btn_test)
    Button mTvTest;

    MPresenter mPresenter;
    @Override
    protected int resId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mPresenter=new MPresenter(this);
    }

    @OnClick(R.id.btn_test)
    void onClick(View view) {
        Map map=new HashMap<String,String>();
        map.put("xsid","100011");
        Call call= RetrofitManager.getInstance().test(ToolUtil.getRequesBody(map));
        mPresenter.loadData(call,0);

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg(String msg) {
        L.e(TAG,""+msg);
    }

    @Override
    public void refreshData(Object response, int position) {
        L.e(TAG,""+response.toString());
    }


}
