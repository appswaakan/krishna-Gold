package com.waakan.kishnagold;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    FloatingActionButton fab;


    SessionManager sessionManager;
    private long backPressTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        webView = findViewById(R.id.my_web_view);
        sessionManager = new SessionManager(getApplicationContext());

//        String username = getIntent().getStringExtra("u");
//        String password = getIntent().getStringExtra("p");

        HashMap<String, String> userdetails = sessionManager.getUserDetails();
        String username = userdetails.get(sessionManager.KEY_UNAME);
        String password = userdetails.get(sessionManager.KEY_PASS);

        //     Toast.makeText(this, userdetails.get(sessionManager.KEY_UNAME) + "--" + userdetails.get(sessionManager.KEY_PASS), Toast.LENGTH_SHORT).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(" Alert!");
                builder.setMessage("are you sure you want to log out?");
                builder.setIcon(R.drawable.useralert);


                AlertDialog.OnClickListener listener = new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == dialog.BUTTON_POSITIVE) {
                            sessionManager.logoutUser();
                            dialog.dismiss();
                            finish();
                        } else if (which == dialog.BUTTON_NEGATIVE) {
                            dialog.dismiss();
                        } else {

                        }
                    }
                };
                builder.setPositiveButton("Yes", listener);
                builder.setNegativeButton("No", listener);
                builder.setNeutralButton("Cancel", listener);
                builder.show();


            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
           //     Toast.makeText(MainActivity.this, url, Toast.LENGTH_LONG).show();

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        //  String url="http://oceanone.in/krishna_gold/Dashboard/Log";
        // String url_home=" http://oceanone.in/krishna_gold/app_json/login.php?username="+username+"&password="+password;
        //   String url_home = "http://oceanone.in/krishna_gold/?username=" + username + "&password=" + password;
        String url_home = "http://oceanone.in/krishna_gold/";
        //  Toast.makeText(this, url_home, Toast.LENGTH_LONG).show();

        webView.loadUrl("http://oceanone.in/krishna_gold/Dashboard/Log?username="+username+"&password="+password);
      //  webView.loadUrl(url_home);


        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
    }

    @Override
    public void onBackPressed() {

        if (backPressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            webView.goBack();//to go back to the previous web page
            Toasty.warning(this, "press again to exit!", Toasty.LENGTH_SHORT, true).show();
        }
        backPressTime = System.currentTimeMillis();
 }
}