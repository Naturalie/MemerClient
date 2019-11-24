package com.example.memer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    boolean success = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void sendRegister(View view){
        if(success){
            Intent intent = new Intent(this, LoginActivity.class);
            this.finish();
            startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
        else{
            Toast.makeText(this,"Something is incorrect!",Toast.LENGTH_LONG).show();
        }
    }


}
