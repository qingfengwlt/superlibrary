package com.superlibrary.ken.widget;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.PopupWindow;

import pc.wlt.com.superlibrary.R;


/**
 * Popupwindow的基类
 * @fileName: BasePopWin.java
 * @author leitao
 * @time 2016/5/13　9:29
 */

public class BasePopWin extends PopupWindow {

	public BasePopWin(Context context) {
//		this.setContentView(view);
		// // 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// // 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		 this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		 ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
//		this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_popwin));
		this.setOutsideTouchable(false);
		this.setBackgroundDrawable(dw);
		this.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				BasePopWin.this.dismiss();

			}
		});
	}


}
