package ken.superlibrary.com.appdemo.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.superlibrary.ken.base.BaseAppCompatActivity;
import com.superlibrary.ken.common.ComAdapter;
import com.superlibrary.ken.common.ComViewHolder;
import com.superlibrary.ken.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ken.superlibrary.com.appdemo.R;
import ken.superlibrary.com.appdemo.utils.ToastUtils;

public class SlideLayoutActivity extends BaseAppCompatActivity {


    @BindView(R.id.lv_list)
    SwipeMenuListView mLv;
    ComAdapter mAdapter;
    List<String> mList=new ArrayList<>();
    @Override
    protected int resId() {
        return R.layout.activity_slide_layout;
    }

    @Override
    protected void initData() {
        for (int i=0;i<30;i++){
            mList.add("item"+i);
        }
        mAdapter=new ComAdapter<String>(this,mList,R.layout.item_list) {
            @Override
            public void setViewContent(ComViewHolder holder, String o) {
                holder.setText(R.id.tv_list_item,o.toString());
            }
        };
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                openItem.setWidth(DensityUtils.dp2px(SlideLayoutActivity.this,90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(DensityUtils.dp2px(SlideLayoutActivity.this,90));
                //                // set item title
                deleteItem.setTitle("delete");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // set a icon
//                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        mLv.setMenuCreator(creator);
        mLv.setAdapter(mAdapter);
        mLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
               switch (index){
                   case 0:
                       ToastUtils.showToast(SlideLayoutActivity.this,"delete");
                       break;
                   case 1:
                       break;
                   default:
                       break;
               }
                return false;
            }
        });

    }
}
