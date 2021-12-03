package com.example.notessaver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Website extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

    // here we load the website for my app
        webView =   (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.loadUrl("file:///android_asset/website.html");
    }
    //not needed since you cant click any links on my website
    public void onBackPressed(){
        if (webView.canGoBack()){
            webView.goBack();
        }else   {
            super.onBackPressed();
        }
    }
}
