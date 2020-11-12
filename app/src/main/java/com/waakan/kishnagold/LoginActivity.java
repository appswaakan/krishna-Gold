package com.waakan.kishnagold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    TextView tv1;

    SessionManager session;

    EditText editTextTextPassword, editTextTextEmailAddress;
    String username, password;
    String uname, pass, msg, status;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginFun();//only validate users can login
            }
        });
    }

    private void loginFun() {

        password = editTextTextPassword.getText().toString().trim();
        username = editTextTextEmailAddress.getText().toString().trim();

        url = "http://oceanone.in/krishna_gold/Dashboard/Log?username=" + username + "&password=" + password;


        if (username.isEmpty()) {
            Toasty.warning(LoginActivity.this, "please enter user name ", Toast.LENGTH_SHORT, true).show();

        } else if (password.isEmpty()) {
            Toasty.warning(LoginActivity.this, "please enter password ", Toast.LENGTH_SHORT, true).show();

        } else {


// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("response_login", response);

                            Gson gson = new Gson();
                            //   Toast.makeText(LoginActivity.this, "new url  "+url, Toast.LENGTH_SHORT).show();

                            JsonData data = gson.fromJson(response, JsonData.class);//Json to Gson

                            status = data.getStatus();

                            if (status.equals("1")) {

                                editTextTextEmailAddress.setText("");
                                editTextTextPassword.setText("");
                                Toasty.success(LoginActivity.this, "Loggin Successfully", Toast.LENGTH_SHORT, true).show();


                                // Creating user login session
                                // For testing i am stroing name, email as follow
                                // Use user real data
                                session.createLoginSession(username, password);


                                // Staring MainActivity
                                goToMainActivity();
                            } else {

                                Toasty.error(LoginActivity.this, "invalid user details.. " + response, Toasty.LENGTH_SHORT, true).show();
                                editTextTextEmailAddress.setText("");
                                editTextTextPassword.setText("");
                            }
                            //  Log.d("msg", msg);
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT, true).show();
                            Log.d("error", error.getMessage().toString());

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();

                    param.put("username", username);
                    param.put("password", password);

                    return param;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            // Add the request to the RequestQueue.
            requestQueue.add(stringRequest);

        }
    }

    private void init() {

        session = new SessionManager(getApplicationContext());

        btn_login = findViewById(R.id.button_login);
        tv1 = findViewById(R.id.textView3);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        String uname1 = spf.getString("uname", uname);
//        String pass1 = spf.getString("pass", pass);
//
//
//        Toast.makeText(this, uname1 + "---" + pass1, Toast.LENGTH_SHORT).show();
//
//        if (spf.contains(uname1) && spf.contains(pass1)) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//means user cannot go back
//            startActivity(intent);
//            finish();
//        }
//    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("u", editTextTextPassword.getText().toString().trim());
        intent.putExtra("p", editTextTextEmailAddress.getText().toString().trim());
        startActivity(intent);
        finish();


    }

    public void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onStart() {
        super.onStart();

        //  Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        if (session.isLoggedIn()) {
            goToMainActivity();
        } else {
            // goToLoginActivity();
        }
    }
}