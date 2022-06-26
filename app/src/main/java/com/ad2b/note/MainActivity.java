package com.ad2b.note;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  static boolean isAppError = false;

  @SuppressLint({"SetJavaScriptEnabled", "SourceLockedOrientationActivity"})
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    final String url = "https://a670dc6ee3964468b450b1c518dafd.herokuapp.com";
    final String auth = "36ee5d25999d4e488854ad7ca0775ba8";
    final WebView web = findViewById(R.id.web);
    final ProgressBar progressBar = findViewById(R.id.progressBar);

    web.loadUrl(url + "?auth=" + auth);
    web.setFocusable(true);
    web.setVisibility(View.GONE);
    web.setFocusableInTouchMode(true);
    web.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {
        if (isAppError) {
          web.setVisibility(View.GONE);
          progressBar.setVisibility(View.VISIBLE);
          Toast.makeText(MainActivity.this, "An error occurs. Internet is required.", Toast.LENGTH_LONG).show();
          return;
        }
        web.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
      }

      @Override
      public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        isAppError = true;
      }
    });
    web.getSettings().setJavaScriptEnabled(true);
    web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    web.getSettings().setDomStorageEnabled(true);
    web.getSettings().setAppCacheEnabled(true);
    web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
  }
}