package com.example.cloudsave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudsave.Fragment.FragmentHome;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {
    private EditText account,pw;
    private Button login,first_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bmob.initialize(this, "d073d70eb1d5623262b3486ec070fc73");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account=findViewById(R.id.username);
        pw=findViewById(R.id.password);
        login=findViewById(R.id.login);//登录按钮
        first_login=findViewById(R.id.register);//注册按钮
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                User user = new User();
                //此处替换为你的用户名
                user.setUsername(account.getText().toString().trim());
                //此处替换为你的密码
                user.setPassword(pw.getText().toString().trim());
                user.login(new SaveListener<User>(){
                    @Override
                    public void done(User bmobUser, BmobException e){
                        if(e==null){
                            User user = BmobUser.getCurrentUser(User.class);
                            Snackbar.make(v, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                            String username=user.getUsername();
                            String userId=user.getObjectId();
                            Intent intent=new Intent(LoginActivity.this, BottomNavigationActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                            finish();
                        }else{
                            Snackbar.make(v, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        first_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                }else if(pw.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.addWhereEqualTo("username",account.getText().toString().trim());
                    query.findObjects(new FindListener<User>() {
                        public void done(List<User> object, BmobException e) {
                            if(e==null){
                                if(object.size()!=0) {
                                    Toast.makeText(LoginActivity.this, "账号被占用，请另取", Toast.LENGTH_SHORT).show();
                                }else {
                                    User user = new User();
                                    user.setUsername(account.getText().toString().trim());
                                    user.setPassword(pw.getText().toString().trim());
                                    user.setPw(pw.getText().toString().trim());
                                    user.signUp(new SaveListener<User>() {
                                        @Override
                                        public void done(User user, BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}