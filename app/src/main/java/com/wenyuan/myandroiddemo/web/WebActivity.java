package com.wenyuan.myandroiddemo.web;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.thirdparty.viewframe.pullrefresh.UltraPTRFragmentLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class WebActivity extends BaseActivity implements UltraPTRFragmentLayout.PullRefreshListener {

    //private final String mUrl = "http://v.youku.com/v_show/id_XMTgyNDMwNjcyNA==.html?from=s1.8-1-1.2&spm=a2h0k.8191407.0.0";
    private final String mUrl = "https://www.cnblogs.com/zgz345/p/3768174.html";
    //private final String mUrl = "http://blog.csdn.net/cappuccinolau/article/details/8262821/";
    //private final String mUrl = "http://www.tv189.com/";
    //private final String mUrl = "https://www.baidu.com/";

    //private final String mUrl = "http://h5.nty.tv189.com/tysx/v/C39289074.html?appid=115020310221&token=d2ac851942301c1903149e08307064ab&devid=000001&version=5.3.4.7&time=20161208175325&tyk=Qjk2WVu3wI0CpiOXrhmCK8jkLqQXXhvhMINLnbIZ1bPoQXkJ02OF1g%3D%3D&NetType=WIFI&appid=115020310221&token=d2ac851942301c1903149e08307064ab&devid=000001&version=5.3.4.7&time=20161208175325&tyk=Qjk2WVu3wI0CpiOXrhmCK8jkLqQXXhvhMINLnbIZ1bPoQXkJ02OF1g%3D%3D&NetType=WIFI";
    //private final String mUrl = "http://h5.nty.tv189.com/2016/06/jizi/index.html?channelId=02292320&appid=115020310221&token=d2ac851942301c1903149e08307064ab&devid=000001&recommendid=ATSY1011110&version=5.3.4.7&time=20161208175924&tyk=pjti1pqn%2FwWdtP3RTGA%2B%2FcLZb6XjuUHDg4JtCD%2B7a%2B9mZTrtzj%2BWnA%3D%3D&NetType=WIFI";
    //private final String mUrl = "http://e.tv189.com?appid=115020310221&token=d2ac851942301c1903149e08307064ab&devid=000001&recommendid=ATSY1021110&version=5.3.4.7&time=20161208180043&tyk=gUAGGlhc1U5taa%2Bt7vmizb2M39Y387RJoAs3VVLui0mk9pSyJIt%2FrA%3D%3D&NetType=WIFI";

    //网页中有H5播放flash video的时候按下全屏按钮将会调用到这个方法，一般用作设置网页播放全屏操作
    private WebView mWebviewShow;
    private ProgressBar mProgressBar;
    private UltraPTRFragmentLayout mPTRFragmentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mWebviewShow = (WebView) findViewById(R.id.webview_show);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mPTRFragmentLayout = (UltraPTRFragmentLayout) findViewById(R.id.pull_refresh_web);
        mPTRFragmentLayout.setPullRefreshListener(this);
        initWebView();
    }

    /**
     * 对webview做初始化设置
     */
    private void initWebView() {
        WebSettings webSettings = mWebviewShow.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //4、打开页面时， 自适应屏幕：
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //5. 使页面支持缩放：
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        /*----------------------------------------*/
        mWebviewShow.getSettings().setAppCacheEnabled(true); //开启缓存功能
        mWebviewShow.getSettings().setDomStorageEnabled(true);//开启DOM形式存储
        String path = mContext.getCacheDir().getAbsolutePath();
        mWebviewShow.getSettings().setAppCachePath(path);//设置缓存路径
        mWebviewShow.getSettings().setAllowFileAccess(true);//启用或禁止WebView访问文件数据
        //websetting.setAppCacheMaxSize(size);      //设置缓存文件大小，但现在已不再提倡这个方法
        mWebviewShow.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
        //LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        mWebviewShow.setWebViewClient(new MyWebViewClient());
        mWebviewShow.setWebChromeClient(new MyChromeClient());
        mWebviewShow.requestFocusFromTouch();//如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。

        mWebviewShow.loadUrl(mUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_web)
            startActivity(InteractionActivity.class);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebviewShow.canGoBack()) {
                mWebviewShow.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void PullRefreshOperation(PtrFrameLayout frame) {
        mWebviewShow.reload();
    }

    /**
     * 用于处理webView的控制问题，如加载、关闭、错误处理等
     */
    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mPTRFragmentLayout.refreshComplete();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mPTRFragmentLayout.refreshComplete();
            mDialogV7Factory.showTextDialog("error", "加载页面出错", true);
            mWebviewShow.stopLoading();// todo 先停止加载
            mWebviewShow.loadUrl("file:///android_asset/nullimage.png");
        }

    }

    /**
     * 处理 html javascript事件
     */
    public class MyChromeClient extends WebChromeClient {
        /**
         * 控制 网页对话框是否显示
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();// todo 这里必须调用，否则页面会阻塞造成假死
            return false;//false : 允许弹出alert
        }

        /**
         * 网页加载进度
         *
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress >= 100)
                mProgressBar.setVisibility(View.GONE);
            else if (newProgress < 100) {
                if (mProgressBar.getVisibility() != View.VISIBLE)
                    mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
        }

        /**
         * 网页全屏时调用
         *
         * @param view
         * @param callback
         */
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
        }
    }
}
