package com.example.a2020project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchAddress extends AppCompatActivity {
    private WebView webView;
    private TextView txt_address;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchaddress);

        txt_address = findViewById(R.id.sign_txt_address);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }
    @SuppressLint("JavascriptInterface")
    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webview);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");

        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());

        // webview url load. php 파일 주소
        webView.loadUrl("http://khprince.com/restaurantApp/addressInquiry.php");


        webView.getSettings().setDomStorageEnabled(true);

    }


    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txt_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }
}
