package com.example.cloudsave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cloudsave.Fragment.FragmentHome;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "d073d70eb1d5623262b3486ec070fc73");
        setContentView(R.layout.activity_splash);
        BmobUser bombUser=BmobUser.getCurrentUser(BmobUser.class);
        if(bombUser!=null){
            String username=bombUser.getUsername();
            String userId=bombUser.getObjectId();
            Intent intent=new Intent(splash.this, BottomNavigationActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("userId",userId);
            startActivity(intent);
            finish();
        }
        else{
            startActivity(new Intent(splash.this, LoginActivity.class));
            finish();
        }
    }
}