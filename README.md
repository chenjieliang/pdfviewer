# pdfviewer
android使用PDF.js浏览pdf，支持在线预览

Android的WebView做不到ios的WebView那样可以很方便的直接预览pdf文件<br>
要实现利用WebView预览pdf我们可以使用谷歌文档服务：<br> mWebView.loadUrl("http://docs.google.com/gviewembedded=true&url=" + pdfUrl);<br>
但这种方式国内网络环境是不用考虑的。<br>
当然也有替代的方案：我们可以使用mozilla开源的PDF.js。<br>

PDF.js版本：v2.0.943<br>
原本mozilla提供的demo不支持在线预览，因此进行了以下修改，使得支持在线预览：<br>
打开./web/viewer.js,修改1564行代码为如下：<br>
//支持在线预览<br>
if (origin !== viewerOrigin<br>
        && !(protocol == 'blob:' || protocol == 'http:' || protocol == 'https:')) {<br>
    throw new Error('file origin does not match viewer\'s');<br>
}<br>
