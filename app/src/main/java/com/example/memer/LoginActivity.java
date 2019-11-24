package com.example.memer;

import androidx.appcompat.app.AppCompatActivity;

import entities.User;
import connection.handlers.LoginConnection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public SharedPreferences preferences = null;
    EditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        StrictMode.ThreadPolicy policy = new
        StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void sendLogin(View view){
        final User JSON = new User(login.getText().toString(),password.getText().toString());
        final LoginConnection connection = new LoginConnection(JSON);
        saveToken(connection.getTokenString());

        if(connection.getSuccess()){
            Intent intent = new Intent(this, MainActivity.class);
            this.finish();
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else{
            Toast.makeText(this,"Something is incorrect!",Toast.LENGTH_LONG).show();
        }
    }

    public void goToRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.finish();
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    private void saveToken(String token){
        preferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        preferences.edit().putString("token", token).apply();
    }
    public String getToken(){
        return preferences.getString("token","");
    }

}

