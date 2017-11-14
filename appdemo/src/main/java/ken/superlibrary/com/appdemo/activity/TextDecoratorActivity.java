package ken.superlibrary.com.appdemo.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.superlibrary.ken.base.BaseAppCompatActivity;
import com.tuyenmonkey.textdecorator.TextDecorator;
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener;

import butterknife.BindView;
import ken.superlibrary.com.appdemo.R;

/**
 * 文本中，单词标色
 */
public class TextDecoratorActivity extends BaseAppCompatActivity {

    @BindView(R.id.tv_text_Decorator)
    TextView mTv;
    @Override
    protected int resId() {
        return R.layout.activity_text_decorator;
    }

    @Override
    protected void initData() {
        String text="Noting is im possible format for a willing heart! Are you already ? Noting format";
//        TextDecorator
//                .decorate(mTv, text)
//                .setTextColor(R.color.colorAccent, 0, 5)
//                .setBackgroundColor(R.color.colorPrimary, 6, 11)
//                .strikethrough(12, 26)
//                .setTextStyle(Typeface.BOLD | Typeface.ITALIC, 27, 40)
//                .setTypeface("serif", 70, 77)
//                .setSuperscript(78, 86)
//                .setSubscript(87, 92)
//                .underline(120, 200)
//                .blur(3, BlurMaskFilter.Blur.NORMAL, 0, 2)
//                .makeTextClickable(new OnTextClickListener() {
//                    @Override public void onClick(View view, String text) {
//                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
//                    }
//                }, 250, 270, false)
//                .build();
        TextDecorator
                .decorate(mTv, text)
                .setTextColor(R.color.colorAccent, "possible", "you","Noting","Noting")
//                .setBackgroundColor(R.color.colorPrimary, "dolor", "elit")
//                .strikethrough("Duis", "Praesent")
//                .underline("sodales", "quam")
//                .setSubscript("vitae")
                .makeTextClickable(new OnTextClickListener() {
                    @Override public void onClick(View view, String text) {
                        Toast.makeText(TextDecoratorActivity.this, text, Toast.LENGTH_SHORT).show();
                    }
                }, false, "porta", "commodo", "tempor venenatis nulla")
                .setTextColor(android.R.color.holo_green_light, "for", "a")
                .build();
    }
}
