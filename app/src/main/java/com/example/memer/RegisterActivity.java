package com.example.memer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import connection.handlers.RegisterConnection;
import entities.User;
import entities.UserRegister;

public class RegisterActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private EditText repassword;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.loginInput);
        password = findViewById(R.id.passwordInput);
        repassword = findViewById(R.id.rePasswordInput);
        email = findViewById(R.id.emailInput);
    }


    public void sendRegister(View view){
        if(password.getText().toString().equals(repassword.getText().toString())) {
            UserRegister ur = new UserRegister(login.getText().toString(), password.getText().toString(), email.getText().toString());
            RegisterConnection rc = new RegisterConnection(ur);

            if(rc.getSuccess() == true){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                this.overridePendingTransition(0, 0);
            }
            else{
                Toast.makeText(this,"Something is incorrect!",Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(this,"Passwords are not same!",Toast.LENGTH_LONG).show();
        }



    }


}
