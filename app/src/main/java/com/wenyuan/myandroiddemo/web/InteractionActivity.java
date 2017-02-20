package com.wenyuan.myandroiddemo.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.wenyuan.myandroiddemo.BaseActivity;
import com.wenyuan.myandroiddemo.R;
import com.wenyuan.myandroiddemo.utils.copy.ToastUtils;

public class InteractionActivity extends BaseActivity implements View.OnClickListener {

    private Button mButInvokJs;
    private TextView mTextJsInvok;
    private WebView mWebviewInteraction;
    private Button mShowAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getLayoutResource() {
        setContentView(R.layout.activity_interaction);
    }

    @Override
    protected void initView() {
        setToolbar(true);
        mButInvokJs = (Button) findViewById(R.id.but_invok_js);
        mButInvokJs.setOnClickListener(this);
        mTextJsInvok = (TextView) findViewById(R.id.text_js_invoke);
        mWebviewInteraction = (WebView) findViewById(R.id.webview_interaction);

        WebSettings settings = mWebviewInteraction.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        mWebviewInteraction.requestFocusFromTouch();
        mWebviewInteraction.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtils.show(mContext, message);
                result.confirm();// todo 这里必须调用，否则页面会阻塞造成假死
                return false;//允许webview弹出警示框
            }
        });
        //mWebviewInteraction.setWebChromeClient(new WebChromeClient());
        //  添加javascript与android交互接口
        mWebviewInteraction.addJavascriptInterface(new ProxyBridge(), "interactionInvoke");
        mWebviewInteraction.loadUrl("file:///android_asset/interaction.html");
        mShowAlert = (Button) findViewById(R.id.show_alert);
        mShowAlert.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_invok_js:
                // android调用javascript函数:webView.loadUrl("javascript:show('"+info+"')");
                mWebviewInteraction.loadUrl("javascript:androidInvokeJs('" + "我是android传来的数据!" + "')");
                break;
            case R.id.show_alert:
                mWebviewInteraction.loadUrl("javascript:androidInvokeJsForAlert('" + "alert框" + "')");
                break;
        }
    }

    /**
     * android 和 javascript 交互的代理
     */
    public class ProxyBridge {

        public ProxyBridge() {
        }

        @JavascriptInterface
        public void jsInvokeForandroid(String s) {
            mTextJsInvok.setText(s);
        }

    }
}
