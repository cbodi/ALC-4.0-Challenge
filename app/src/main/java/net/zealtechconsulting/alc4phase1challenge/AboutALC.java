package net.zealtechconsulting.alc4phase1challenge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutALC extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_alc);

        webView = (WebView) findViewById(R.id.webview_about_alc);
        //The statement webView.setWebViewClient(new WebViewClient()); alone is sufficient if loading non ssl url or ssl url with supported certificate.
        // However, if loading ssl url with certificates not supported, we Catch the exception in onReceivedSslError method and do respective
        // action when certificate is not supported.
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
                Log.d("CHECK", "onReceivedSslError");
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutALC.this);
                AlertDialog alertDialog = builder.create();
                String message = "Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";
                alertDialog.setTitle("SSL Certificate Error");
                alertDialog.setMessage(message);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("CHECK", "Button ok pressed");
                        // Ignore SSL certificate errors
                        handler.proceed();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("CHECK", "Button cancel pressed");
                        handler.cancel();
                    }
                });
                alertDialog.show();
            }
        }); //this line tells android to open the URL below within our app.

        webView.loadUrl("https://www.andela.com/alc/"); //This will load the url outside our app if not combined with setWebViewClient.

        WebSettings webSettings = webView.getSettings(); //by declaring an instance of type WebSettings as indicated here, we can now use the instance variable
        // to control the settings of our webView.
        webSettings.setJavaScriptEnabled(true); //This will enable javascript on our web browser.
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) { //the if statement checks to know if we can go back to a previous page within the URL in our webView.
            webView.goBack(); //if it returns true then we call the goBack method of our webView
        } else { //if not then we call the onBackPressed method of the super class to close our activity.
            super.onBackPressed(); //this method is called to close our app.
        }
    }
}
