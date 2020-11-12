package com.waakan.kishnagold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {
    TextView login_txt;
    Button register_btn;
    EditText editText_jweller_name, editText_shopAddress, editText_pincode, editText_username, editText_Password, editText_EmailAddress, editText_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerData();   //data will be registered in mySql here
            }
        });
    }

    private void registerData() {

        String jweller_name, shop_address, pincode, username, password, email_address, mobile_number;

        jweller_name = editText_jweller_name.getText().toString().trim();
        shop_address = editText_shopAddress.getText().toString().trim();
        pincode = editText_pincode.getText().toString().trim();
        username = editText_username.getText().toString().trim();
        password = editText_Password.getText().toString().trim();
        email_address = editText_pincode.getText().toString().trim();
        mobile_number = editText_mobile.getText().toString().trim();

        String url = "http://oceanone.in/krishna_gold/app_json/customer_registration.php";

        if (jweller_name.isEmpty()) {

            Toasty.warning(RegisterActivity.this, "please enter jweller name ", Toast.LENGTH_SHORT, true).show();

        } else if (username.isEmpty()) {

            Toasty.warning(RegisterActivity.this, "please enter user name ", Toast.LENGTH_SHORT, true).show();


        } else if (password.isEmpty()) {

            Toasty.warning(RegisterActivity.this, "please enter password ", Toast.LENGTH_SHORT, true).show();

        } else if (shop_address.isEmpty()) {

            Toasty.warning(RegisterActivity.this, "please enter shop address ", Toast.LENGTH_SHORT, true).show();

        } else if (pincode.isEmpty()) {

            Toasty.warning(RegisterActivity.this, "please enter pin code", Toast.LENGTH_SHORT, true).show();
        } else if (email_address.isEmpty()) {

            Toasty.warning(RegisterActivity.this, "please enter email address", Toast.LENGTH_SHORT, true).show();


        } else if (mobile_number.isEmpty()) {

            Toasty.warning(RegisterActivity.this, "please enter mobile number ", Toast.LENGTH_SHORT, true).show();

        } else {


// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toasty.success(RegisterActivity.this, response, Toast.LENGTH_SHORT, true).show();
                            Log.d("response", response);


                            editText_jweller_name.setText("");
                            editText_shopAddress.setText("");
                            editText_pincode.setText("");
                            editText_username.setText("");
                            editText_Password.setText("");
                            editText_EmailAddress.setText("");
                            editText_mobile.setText("");

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toasty.error(RegisterActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT, true).show();
                            Log.d("error", error.getMessage().toString());

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name", jweller_name);
                    params.put("address", shop_address);
                    params.put("pincode", pincode);
                    params.put("mobile", mobile_number);
                    params.put("username", username);
                    params.put("password", password);
                    params.put("email", email_address);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            // Add the request to the RequestQueue.
            requestQueue.add(stringRequest);

        }
    }

    private void init() {
        login_txt = findViewById(R.id.login_tv);
        register_btn = findViewById(R.id.create_account_button);
        editText_jweller_name = findViewById(R.id.editText_jweller_name);
        editText_shopAddress = findViewById(R.id.editText_shopAddress);
        editText_pincode = findViewById(R.id.editText_pincode);
        editText_username = findViewById(R.id.editText_username);
        editText_Password = findViewById(R.id.editText_Password);
        editText_EmailAddress = findViewById(R.id.editText_EmailAddress);
        editText_mobile = findViewById(R.id.editText_mobile);
    }
}