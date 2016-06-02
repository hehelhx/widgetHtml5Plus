package com.camtalk.xinwei.widget;

import io.dcloud.EntryProxy;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ISysEventListener.SysEventType;
import io.dcloud.feature.internal.sdk.SDK;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;


/**
 * 本demo为以WebApp方式集成5+ sdk， 
 *
 */
public class SDK_WebApp extends Activity {

	boolean doHardAcc = true;


	Bundle bundle = null;

	/** SDKWebApp根据传递过来的url在js中接收，并再次分发，某些关于user的信息，需要在网页中获取本地缓存，而不是从native传递过去，
	 *  因此，在获取前，网页就必须要实现了对数据的缓存
	 * */

	/** 传递过来的url_path */
	String url = "";

	/** 传递过来的根据活动、老师id查询的内容 */
	int id = 0;

	/** 活动详情id */
	private int aid;
	View view;
	FrameLayout f;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getBundle();
		 {
			f = new FrameLayout(this);
			 view = MainActivity.listener.iApp.obtainWebAppRootView().obtainMainView();
			 view.setVisibility(View.VISIBLE);
			 f.addView(view, 0);
			 setContentView(f);
		}
	}

//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		Log.i("MAIN","onBackPressed");
//		if("/modules/works/addWorks.html".equals(url)){
//			BaseApplication.isBackFromUploadPage = true;
//			this.finish();
//		}
//		onDestroy();
//		this.finish();
//	}

	private void getBundle(){
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent.getFlags() != 0x10600000) {// 非点击icon调用activity时才调用newintent事件
	
		}
	}

	@Override
	protected void onDestroy() {
		f.removeAllViews();
		super.onDestroy();
		Log.i("MAIN","onDestroy");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		return  super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		
		return  super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		
		return super.onKeyLongPress(keyCode, event);
	}

	public void onConfigurationChanged(Configuration newConfig) {
		try {

			super.onConfigurationChanged(newConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
