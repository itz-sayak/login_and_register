package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText ename, ephone, epassword;
    Button eregist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ename = findViewById(R.id.appCompatEditText);
        ephone = findViewById(R.id.appCompatEditText2);
        epassword = findViewById(R.id.appCompatEditText1);

        eregist = findViewById(R.id.appCompatButton);

        eregist.setOnClickListener(view -> {
            String name = ename.getText().toString();
            String phone = ephone.getText().toString();
            String password = epassword.getText().toString();

            if (isValid(name,phone,password)){
                registerUser(name,phone,password);
            }

        });
    }

    private boolean isValid(String name, String phone, String password) {
        if (name.isEmpty()){
            showMessage("please enter username");
            return false;
        }
        if (name.length()>20){
            showMessage("username is too long");
            return false;
        }
        if (phone.isEmpty()){
            showMessage("Please enter phone number");
            return false;
        }
        if (phone.length() != 10){
            showMessage("Enter a VALID phone number");

        }
        if (password.isEmpty()){
            showMessage("Enter Password");
            return false;
        }
        if (password.length()<8 || password.length()>20){
            showMessage("Enter Valid Password");
            return false;
        }
        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


    private void registerUser(String name, String phone, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Endpoint.register_url, response -> {
            Log.d("REG_RESPONSE", response);

            if (response.equals("Connection Successqueryok")){
                showMessage("Registration Done");
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
            else{
                showMessage("username already exist");
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        },error -> {
            showMessage("Please check your Internet_Connection");
            Log.d("VOLLEY",error.getMessage());
            Intent intent = new Intent(register.this, login.class);
            startActivity(intent);
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("phone",phone);
                params.put("password",password);
                return params;
            }

        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void signin(View view){
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
    }
    
    
    
}