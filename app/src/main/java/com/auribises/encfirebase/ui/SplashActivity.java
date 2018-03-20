package com.auribises.encfirebase.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.auribises.encfirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        auth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = auth.getCurrentUser();
        //firebaseUser.getUid();

        if(firebaseUser != null){
            //Toast.makeText(this,"User Exists "+firebaseUser.getUid(),Toast.LENGTH_LONG).show();
            handler.sendEmptyMessageDelayed(201,2500);
        }else{
            //Toast.makeText(this,"User Does Not Exists ",Toast.LENGTH_LONG).show();
            handler.sendEmptyMessageDelayed(101,2500);
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Intent intent = null;

            if(msg.what == 101){
                intent = new Intent(SplashActivity.this,RegisterUserActivity.class);
            }else{
                intent = new Intent(SplashActivity.this,HomeActivity.class);
            }

            startActivity(intent);
            finish();
        }
    };
}
