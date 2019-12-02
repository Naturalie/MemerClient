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

            if(rc.getSuccess() == 0){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                this.overridePendingTransition(0, 0);
            }
            else if(rc.getSuccess() == 1){
                Toast.makeText(this,"Login pattern is wrong!",Toast.LENGTH_LONG).show();
            }else if(rc.getSuccess() == 2){
                Toast.makeText(this,"Password pattern is wrong!",Toast.LENGTH_LONG).show();
            }else if(rc.getSuccess() == 3){
                Toast.makeText(this,"Email pattern is wrong!",Toast.LENGTH_LONG).show();
            }else if(rc.getSuccess() == 4){
                Toast.makeText(this,"User already exist!",Toast.LENGTH_LONG).show();
            }else if(rc.getSuccess() == 5){
                Toast.makeText(this,"Email is already taken!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Something is wrong with connection!",Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(this,"Passwords are not same!",Toast.LENGTH_LONG).show();
        }



    }


}
