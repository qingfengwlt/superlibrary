package com.superlibrary.ken.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

/***
 * 
 * @fileName ComFragmentAdapter.java
 * @author leitao
 * @date 2015-7-30
 * @update by
 */
public abstract class ComFragmentAdapter extends FragmentPagerAdapter {

	private String[] mTabs;
	private FragmentManager fm;

	public ComFragmentAdapter(FragmentManager fm, String[] tabs) {
		super(fm);
//		ComFragmentAdapter(fm);
		this.fm=fm;
		this.mTabs=tabs;
	}
	public ComFragmentAdapter(FragmentManager fm) {
		super(fm);

	}
	
	
	@Override
	public abstract Fragment getItem(int postion);

	@Override
	public int getCount() {
		return mTabs.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTabs[position];
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	} 
	
	@Override
	public Object instantiateItem(View container, int position) {
		  String name = makeFragmentName(container.getId(), position);
	        Fragment fragment = fm.findFragmentByTag(name);
	        if (fragment != null) {
//	            if (DEBUG) Log.v(TAG, "Attaching item #" + position + ": f=" + fragment);
	            fm.beginTransaction().attach(fragment);
	        } else {
	            fragment = getItem(position);
//	            if (DEBUG) Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
	            fm.beginTransaction().add(container.getId(), fragment,
	                    makeFragmentName(container.getId(), position));
	        }
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}
	
	private static String makeFragmentName(int viewId, long id) {
	    return "android:switcher:" + viewId + ":" + id;
	}
	
}
