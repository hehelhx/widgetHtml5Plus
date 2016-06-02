package com.camtalk.xinwei.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import io.dcloud.EntryProxy;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.IWebviewStateListener;
import io.dcloud.feature.internal.sdk.SDK;


public class MainActivity extends Activity {
    EntryProxy proxy;
    public static globalModeListener listener = null;
    private Button mGotoMui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 初始化5+内核*/
        MainActivity.listener = new globalModeListener(this);
        proxy = EntryProxy.init(this, listener);
        proxy.onCreate(this, savedInstanceState, SDK.IntegratedMode.WEBAPP, null);
        /* 定义button*/
        mGotoMui = (Button) findViewById(R.id.gotoMui);
        mGotoMui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(MainActivity.this, SDK_WebApp.class);
                startActivity(it);
                overridePendingTransition(R.anim.enter_in_right, R.anim.exit_out_left);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        proxy.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        proxy.onResume(this);
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getFlags() != 0x10600000) {// 非点击icon调用activity时才调用newintent事件
            proxy.onNewIntent(this, intent);
        }
    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(null!=proxy){
            proxy.onStop(this);
        }
    }
    public class globalModeListener implements ICore.ICoreStatusListener, IOnCreateSplashView, Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        public IApp iApp = null;
        Activity activity;

        public globalModeListener(Activity activity) {
            // TODO Auto-generated constructor stub
            this.activity = activity;
        }

        @Override
        public void onCloseSplash() {
            // TODO Auto-generated method stub

        }

        @Override
        public Object onCreateSplash(Context arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void onCoreInitEnd(ICore arg0) {
            String appBasePath = "/apps/helloMui";
            iApp = SDK.startWebApp(this.activity, appBasePath, null, new IWebviewStateListener() {

                @Override
                public Object onCallBack(int pType, Object pArgs) {
                    // TODO Auto-generated method stub
                    switch (pType) {
                        case IWebviewStateListener.ON_WEBVIEW_READY:

                            break;
                        case IWebviewStateListener.ON_PAGE_STARTED:

                            break;
                        case IWebviewStateListener.ON_PROGRESS_CHANGED:

                            break;
                        case IWebviewStateListener.ON_PAGE_FINISHED:

                            // 页面加载完毕，设置显示webview
                            break;
                    }
                    return null;
                }
            }, this);

            iApp.setIAppStatusListener(new IApp.IAppStatusListener() {

                @Override
                public boolean onStop() {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public void onStart() {

                    int i = 0;
                    i++;
                }

                @Override
                public void onPause(IApp arg0, IApp arg1) {
                    // TODO Auto-generated method stub

                }
            });

        }

        @Override
        public void onCoreReady(ICore arg0) {
            // TODO Auto-generated method stub
            SDK.initSDK(arg0);
            // 设置当前应用可使用的5+ API
            SDK.requestAllFeature();
        }

        @Override
        public boolean onCoreStop() {
            // TODO Auto-generated method stub
            return false;
        }

    }
}
