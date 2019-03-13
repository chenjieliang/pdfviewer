package com.cjl.pdfviewer.library;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebView;

import com.cjl.pdfviewer.library.code.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * @author chenjieliang
 */
public class PDFViewer {

    private static final String PDF_VIEWER_HTML = "file:///android_asset/pdfjs/web/viewer.html";
    private WebView webView;

    public PDFViewer(WebView webView){
        this.webView = webView;
    }

    public void loadUrl(String docPath){
        if (webView==null) {
            throw new NullPointerException("WebView is null");
        }
        if (Build.VERSION.SDK_INT >= 19) {
            webView.loadUrl(PDF_VIEWER_HTML + "?file=" + docPath);
        } else {
            if (!TextUtils.isEmpty(docPath)) {
                byte[] bytes = null;

                try {
                    bytes = docPath.getBytes("UTF-8");
                } catch (UnsupportedEncodingException var4) {
                    var4.printStackTrace();
                }

                if (bytes != null) {
                    docPath = (new BASE64Encoder()).encode(bytes);
                }
            }

            webView.loadUrl(PDF_VIEWER_HTML + "?file=" + docPath);
        }

    }
}
