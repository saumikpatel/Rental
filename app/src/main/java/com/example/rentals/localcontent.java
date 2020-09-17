package com.example.rentals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class localcontent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localcontent);

     //   Webview browser=(Webview) findViewById(R.id.browser); //if you gave the id as browser
        WebView browser =(WebView) findViewById(R.id.browser);
        browser.getSettings().setJavaScriptEnabled(true); //Yes you have to do it
        browser.loadUrl("file:///android_asset/local.html?lat=45.501734&lng=-73.56542"); //If you put the HTML file in asset folder of android
    }
}