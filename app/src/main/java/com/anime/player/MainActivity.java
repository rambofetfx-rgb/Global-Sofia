package com.anime.player;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                // Permite apenas navegação normal via HTTP/HTTPS
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false; 
                }

                // Bloqueia esquemas de aplicações externas (aliexpress://, intent://, etc.)
                return true; 
            }
        });

        // Carrega o ficheiro HTML guardado na pasta assets
        webView.loadUrl("file:///android_asset/index.html");
    }
}
