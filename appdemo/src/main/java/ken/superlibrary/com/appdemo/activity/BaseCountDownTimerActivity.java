package ken.superlibrary.com.appdemo.activity;

import android.widget.TextView;

import com.superlibrary.ken.base.BaseAppCompatActivity;
import com.superlibrary.ken.utils.AutoCountDownTimer;

import butterknife.BindView;
import ken.superlibrary.com.appdemo.R;

public class BaseCountDownTimerActivity extends BaseAppCompatActivity {


    @BindView(R.id.tv_base_count_down_timer_one)
    TextView mTvOne;
    @BindView(R.id.tv_base_count_down_timer_two)
    TextView mTvTwo;
    @BindView(R.id.tv_base_count_down_timer_three)
    TextView mTvThree;

    private AutoCountDownTimer mAutoCountDownTimer,mAutoCountDownTimer2,mAutoCountDownTimer3;
    @Override
    protected int resId() {
        return R.layout.activity_base_count_down_timer;
    }

    @Override
    protected void initData() {
        mAutoCountDownTimer=new AutoCountDownTimer(System.currentTimeMillis(),1000) {
            @Override
            public void onFinish() {
                super.onFinish();
            }
        };
        //// TODO: 2017/5/10 设置显示格式 
        mAutoCountDownTimer.setFormat_hh_mm_ss("HH:mm:ss");
        //// TODO: 2017/5/10 设置显示的View :textView
        mAutoCountDownTimer.setmTextView(mTvOne);
        mAutoCountDownTimer.start();

        mAutoCountDownTimer2=new AutoCountDownTimer(System.currentTimeMillis(),1000) {
            @Override
            public void onFinish() {
                super.onFinish();
            }
        };
        mAutoCountDownTimer2.setFormat_hh_mm_ss("HH小时mm分钟ss秒");
        mAutoCountDownTimer2.setmTextView(mTvTwo);
        mAutoCountDownTimer2.start();

        mAutoCountDownTimer3=new AutoCountDownTimer(60*1000,1000) {
            @Override
            public void onFinish() {
                super.onFinish();
            }
        };
        mAutoCountDownTimer3.setFormat_hh_mm_ss("ss秒");
        mAutoCountDownTimer3.setmTextView(mTvThree);
        mAutoCountDownTimer3.start();
    }

}
