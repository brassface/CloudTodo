package com.example.cloudsave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class RePassword extends AppCompatActivity {
    private EditText oldPassword,newPassword,newPassword2;
    private Button yes,no;
    private String right,o,n,n2,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_password);
        userId=BottomNavigationActivity.userId;
        System.out.println("userId="+userId);
        oldPassword=findViewById(R.id.repassword_old_edit);
        newPassword=findViewById(R.id.repassword_new_edit);
        newPassword2=findViewById(R.id.repassword_new2_edit);
        yes=findViewById(R.id.repassword_yes_button);
        no=findViewById(R.id.repassword_no_button);
        Intent intent=new Intent(RePassword.this,BottomNavigationActivity.class);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n=newPassword.getText().toString();
                n2=newPassword2.getText().toString();
                System.out.println("两次密码输入分别为"+n+","+n2);
                if (!n.equals(n2)){
                    Toast.makeText(RePassword.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    newPassword.setText("");
                    newPassword2.setText("");
                }else {
                    BmobQuery<User> bmobQuery = new BmobQuery<User>();
                    bmobQuery.getObject(userId, new QueryListener<User>() {
                        @Override
                        public void done(User object, BmobException e) {
                            if (e == null) {
                                right = object.getPw();
                                System.out.println("right password="+right);
                            } else {
                                Toast.makeText(RePassword.this, "网络异常", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    o = oldPassword.getText().toString();
                    System.out.println("old shuru="+o);
                    if (o.equals(right)) {
                        User temp= new User();
                        temp.setPassword(n);
                        temp.setPw(n);
                        temp.update(userId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(RePassword.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RePassword.this, "网络异常", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RePassword.this, "原密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                        oldPassword.setText("");
                    }
                }
            }
        });
    }
}