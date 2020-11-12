package com.waakan.kishnagold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

//kotlin code
//        var handler= Handler()
//        handler.postDelayed({
//
//                var intent= Intent(this,CategoryActivity::class.java)
//        startActivity(intent)
//        finish()
//        },7000)


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        Intent intent= new Intent(Splash_activity.this,LoginActivity.class);
        startActivity(intent);
        finish();

            }
        },2000);

    }
}