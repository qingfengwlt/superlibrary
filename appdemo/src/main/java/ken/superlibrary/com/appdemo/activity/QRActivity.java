package ken.superlibrary.com.appdemo.activity;

import android.widget.ImageView;

import com.superlibrary.ken.base.BaseAppCompatActivity;
import com.superlibrary.ken.utils.QRCodeUtil;
import com.superlibrary.ken.utils.ScreenUtils;

import butterknife.BindView;
import ken.superlibrary.com.appdemo.R;

/**
 * 生产二维码
 */
public class QRActivity extends BaseAppCompatActivity {

    @BindView(R.id.iv_qr)
    ImageView mIvQR;

    String path="http://47.93.45.118:8080/wanshida/file/app/mkapp.apk";
    @Override
    protected int resId() {
        return R.layout.activity_qr;
    }

    @Override
    protected void initData() {
        int width= ScreenUtils.getScreenWidth(this)-20;
        mIvQR.setImageBitmap(QRCodeUtil.createQRImage(path,width ,width));

    }
}
