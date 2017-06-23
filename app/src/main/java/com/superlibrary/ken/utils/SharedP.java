package com.superlibrary.ken.utils;


import android.content.Context;
import android.content.SharedPreferences;



/**
 * SharedPreferences 小数据存在工具
 * 
 * @SharedP.java
 * @author wlt data:2016-4-18 time:上午11:08:31
 */
public class SharedP {

	private static String TAG=SharedP.class.getSimpleName();
	
	private static String KEY_CONFIG = "config_pas";// 个人用户信息
	private static String STRING_DefVALUE = "";// 获取失败时，默认字符串
	private static Boolean BOOLEAN_DEFVALUE = false;// 默认布尔
	private static int INT_DEFVALUE = -1; // 默认整型
	private static SharedPreferences mSharedP;
	private static SharedPreferences.Editor mEditor;

	public SharedP(Context context) {
		mSharedP = context.getSharedPreferences(KEY_CONFIG,
				Context.MODE_WORLD_READABLE);
		mEditor = mSharedP.edit();
	}

	public SharedP(Context context, String key) {
		mSharedP = context.getSharedPreferences(key, Context.MODE_WORLD_READABLE);
		mEditor = mSharedP.edit();
	}

	public static String getStringBySharedP(String key) {
		L.d(TAG, "get_String :" + key + " = " + mSharedP.getString(key, STRING_DefVALUE));
		return mSharedP.getString(key, STRING_DefVALUE);
	}

	public static boolean getBooleanBySharedP(String key) {
		L.d(TAG, "get_boolean :"+key+" = "+mSharedP.getBoolean(key,BOOLEAN_DEFVALUE));
		return mSharedP.getBoolean(key, BOOLEAN_DEFVALUE);
	}

	public static int getIntBySharedP(String key) {
		L.d(TAG, "get_int :"+key+" = "+mSharedP.getInt(key, INT_DEFVALUE));
		return mSharedP.getInt(key, INT_DEFVALUE);
	}
	

	public static void putStringBySharedP(String key,String value) {
		mEditor.putString(key, value); 
		mEditor.commit();
		L.d(TAG, "putString="+key+"  "+value);
	}

	public static void putBooleanBySharedP(String key,boolean value) {
		mEditor.putBoolean(key, value);
		mEditor.commit();
		L.d(TAG, "putBoolean="+key+" "+value+"");
	}

	public static void putIntBySharedP(String key,int value) {
		mEditor.putInt(key, value);
		mEditor.commit();
		L.d(TAG, "putInt="+key+" "+value+"");
	}

	public static void clearSharedP() {
		mEditor.clear().commit();
		L.d(TAG, "clearSharedP");
	}

	public static void removeKey(String key) {
		mEditor.remove(key).commit();
		L.d(TAG, "remove="+key);
	}
	public static boolean isLogin() {
		L.d(TAG, "isLogin=" + "isLogin");
		boolean isLogin=mSharedP.getBoolean("isLogin", BOOLEAN_DEFVALUE);
		L.d(TAG, "isLogin="+isLogin);
		return isLogin;
	}

	public static void setIsLogin(boolean isLogin) {
		mEditor.putBoolean("isLogin", isLogin);
		mEditor.commit();
		L.d(TAG, "putBoolean="+"isLogin="+" "+isLogin+"");
	}
}
