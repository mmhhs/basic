package com.feima.baseproject.activity.set;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseActivity;
import com.feima.baseproject.listener.IOnTryClickListener;
import com.feima.baseproject.util.BaseConstant;
import com.feima.baseproject.util.OptionUtil;
import com.feima.baseproject.util.ResultUtil;
import com.feima.baseproject.util.net.HttpUtil;
import com.feima.baseproject.util.tool.LogUtil;
import com.feima.baseproject.view.dialog.ViewUtil;

import butterknife.InjectView;


/**
 * 协议界面
 */
public class ActWebActivity extends BaseActivity {
    @InjectView(R.id.ft_ui_web_webview)
    public WebView webView;
    private String title,urlStr;
    public ViewUtil viewTool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTaskTag(getClass().getSimpleName());
        setContentView(R.layout.ft_ui_web);
    }

    @Override
    protected void onDestroy(){
        try {
            if(webView !=null){
                webView.destroy();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void init() {
        viewTool = new ViewUtil();
        try {
            title = getIntent().getStringExtra(BaseConstant.INTENT_TYPE);
            urlStr = ResultUtil.judgeWebUrl("" + getIntent().getStringExtra(BaseConstant.INTENT_CONTENT));
        }catch (Exception e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {

            if (0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE))

            { WebView.setWebContentsDebuggingEnabled(true);}

        }
        setClient(webView);
        readHtml();
    }


    /**
     * 从网络上获取协议
     */
    private void readHtml(){
        try {
            if (!HttpUtil.isnet(this)){
                viewTool.addErrorView(this, getString(R.string.net_tip),
                        containLayout, loadLayout, onTryClickListener);
                return;
            }
            OptionUtil.setWebsetting(webView);
////            webView.getSettings().setJavaScriptEnabled(true);
            webView.clearCache(true);
            webView.clearHistory();
            webView.loadUrl(urlStr);
            webView.requestFocus();
//            webView.loadData(urlStr,"text/html", "utf-8");
            LogUtil.e("urlStr= " + urlStr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setClient(WebView webView){
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
    }

    WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result)
        {
            // TODO Auto-generated method stub
            return super.onJsAlert(view, url, message, result);
        }
    };

    private WebViewClient webViewClient = new WebViewClient(){

        //在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

//        //重写此方法才能够处理在浏览器中的按键事件
//        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//            return super.shouldOverrideKeyEvent(view, event);
//        }

//        //报告错误信息
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//
//            super.onReceivedError(view, errorCode, description, failingUrl);
//        }

//        //更新历史记录
//        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
//
//            super.doUpdateVisitedHistory(view, url, isReload);
//        }

        //在页面加载开始时调用
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            viewTool.addLoadView(ActWebActivity.this, getString(R.string.task2), containLayout, loadLayout);
            containLayout.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        //在页面加载结束时调用
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            if(containLayout!=null&&loadLayout!=null){
                viewTool.removeLoadView(containLayout, loadLayout);
            }else {
                loadLayout.setVisibility(View.GONE);
            }
        }


    };

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }else {
            finishSelf();
        }
    }

    private IOnTryClickListener onTryClickListener = new IOnTryClickListener() {

        @Override
        public void onTry() {
            readHtml();
        }

        @Override
        public void onOption() {

        }
    };
}