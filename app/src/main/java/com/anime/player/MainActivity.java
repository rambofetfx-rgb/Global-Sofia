package com.anime.player;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Cria o WebView dinamicamente ocupando toda a tela
        myWebView = new WebView(this);
        setContentView(myWebView);

        WebSettings webSettings = myWebView.getSettings();

        // 1. Essencial para rodar players de vídeo e scripts
        webSettings.setJavaScriptEnabled(true);
        
        // 2. Essencial para players que salvam progresso ou dados no navegador
        webSettings.setDomStorageEnabled(true);
        
        // 3. Permite que vídeos HTTP rodem dentro de uma página HTTPS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 4. Configurações extras para mídia
        webSettings.setMediaPlaybackRequiresUserGesture(false); // Permite autoplay
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // Força os links a abrirem dentro do App e não no Google Chrome
        myWebView.setWebViewClient(new WebViewClient());
        // Essencial para renderizar players de vídeo pesados
        myWebView.setWebChromeClient(new WebChromeClient());

        // Carrega o seu site ou arquivo local (ATUALIZE AQUI SEU LINK)
        // Se for um arquivo no próprio app: myWebView.loadUrl("file:///android_asset/index.html");
        myWebView.loadUrl("file:///android_asset/index.html"); 
    }

    // Permite usar o botão "Voltar" do celular para voltar a página do anime
    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
