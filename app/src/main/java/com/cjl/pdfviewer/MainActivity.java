package com.cjl.pdfviewer;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cjl.pdfviewer.library.PDFViewer;

public class MainActivity extends AppCompatActivity {

    PDFViewer mPdfViewer;
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPdfViewer = new PDFViewer(mWebView);
        //加载本地文件
        preView("file:///android_asset/pdfjs/web/compressed.tracemonkey-pldi-09.pdf");
        //加载网络文件
        //preView("http://192.168.60.147:8854/product/demo.pdf");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }

    /**
     * 预览pdf
     *
     * @param pdfUrl url或者本地文件路径
     */
    private void preView(String pdfUrl) {

        //1.只使用pdf.js渲染功能，自定义预览UI界面
        // mWebView.loadUrl("file:///android_asset/pdfjs/index.html?" + pdfUrl);
        //2.使用mozilla官方demo加载在线pdf
        //mWebView.loadUrl("http://mozilla.github.io/pdf.js/web/viewer.html?file=" + pdfUrl);
        //3.pdf.js放到本地
        mPdfViewer.loadUrl(pdfUrl);
        //mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl);
        //4.使用谷歌文档服务
        //mWebView.loadUrl("http://docs.google.com/gviewembedded=true&url=" + pdfUrl);
    }
}

