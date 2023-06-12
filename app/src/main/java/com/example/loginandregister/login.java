package com.example.loginandregister;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {


    String stn;
    EditText etname, etpassword;
    Button getin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etname = findViewById(R.id.appCompatEditText);
        etpassword = findViewById(R.id.appCompatEditText1);
        getin = findViewById(R.id.appCompatButton);

        getin.setOnClickListener(view -> {
            String lname = etname.getText().toString();
            String password = etpassword.getText().toString();

            if (isValiid(lname, password)){
                signIn(lname, password);
            }


        });



    }
    public void SignUp(View view){
        Intent intent = new Intent(login.this, register.class);
        startActivity(intent);

    }



    private void signIn(String lname, String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoint.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stn = etname.getText().toString();
                Log.d("LOGIN_RESPONSE", response);

                if (response.equals("Connection Successsuccess")) {
                    Intent intent = new Intent(login.this, home.class);
                    intent.putExtra("lname", stn);
                    startActivity(intent);
                    etname.setText(null);
                    etpassword.setText(null);

                }
                else {
                    showMessage("Invalid username or password.");
                    Intent intent = new Intent(login.this, login.class);
                    startActivity(intent);
                    etname.setText(null);
                    etpassword.setText(null);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage("Please Check your Internet Connection");
                Log.d("VOLLEY",error.getMessage());
                Intent intent = new Intent(login.this, login.class);
                startActivity(intent);
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lname", lname);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean isValiid(String lname, String password) {
        if(lname.isEmpty()){
            showMessage("Enter Username");
            etname.setText(null);
            return false;
        }
        if(password.isEmpty()){
            showMessage("Enter Password");
            etpassword.setText(null);
            return false;
        }
        return true;
    }

    private void showMessage(String message_) {
        Toast.makeText(this,message_, Toast.LENGTH_SHORT).show();
    }

}