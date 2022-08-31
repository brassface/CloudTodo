package com.example.cloudsave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class NumberAdd extends AppCompatActivity {
    private EditText editDownName,editCalled,editPassword;
    private Button add,delete,back;
    private String downName,called,password,mode,relationId,downUsername,downCalled;
    private TextView textDownName,textCalled,textPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_add);
        editDownName = findViewById(R.id.number_down_name_edit);
        editCalled = findViewById(R.id.number_called_edit);
        editPassword = findViewById(R.id.number_password_edit);
        textDownName=findViewById(R.id.number_down_name_edit_unable);
        textCalled=findViewById(R.id.number_called_edit_unable);
        textPassword=findViewById(R.id.number_password_text);
        add = findViewById(R.id.number_add_button);
        delete=findViewById(R.id.number_delete_button);
        back = findViewById(R.id.number_back_button);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        relationId=intent.getStringExtra("relationId");
        downUsername=intent.getStringExtra("downUsername");
        downCalled=intent.getStringExtra("downCalled");

        Intent intent1 = new Intent(NumberAdd.this, BottomNavigationActivity.class);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
                finish();
            }
        });
        if (mode.equals("0")) {
            add.setVisibility(View.VISIBLE);
            delete.setVisibility(View.INVISIBLE);
            editDownName.setVisibility(View.VISIBLE);
            editCalled.setVisibility(View.VISIBLE);
            editPassword.setVisibility(View.VISIBLE);
            textDownName.setVisibility(View.INVISIBLE);
            textCalled.setVisibility(View.INVISIBLE);
            textPassword.setVisibility(View.VISIBLE);
        }else{
            add.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.VISIBLE);
            editDownName.setVisibility(View.INVISIBLE);
            editCalled.setVisibility(View.INVISIBLE);
            editPassword.setVisibility(View.INVISIBLE);
            textPassword.setVisibility(View.INVISIBLE);
            textDownName.setVisibility(View.VISIBLE);
            textCalled.setVisibility(View.VISIBLE);
            textDownName.setText(downUsername);
            textCalled.setText(downCalled);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downName = editDownName.getText().toString();
                called = editCalled.getText().toString();
                password = editPassword.getText().toString();
                if (downName == null || downName.equals("")) {
                    Toast.makeText(NumberAdd.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                if (called == null || called.equals("")) {
                    Toast.makeText(NumberAdd.this, "称谓不能为空", Toast.LENGTH_SHORT).show();
                }
                if (password == null || password.equals("")) {
                    Toast.makeText(NumberAdd.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("username", downName);
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> object, BmobException e) {
                        if (e == null) {
                            String rightPassword = object.get(0).getPw();
                            if (rightPassword.equals(password)) {
                                relation temp = new relation();
                                temp.setUp(BottomNavigationActivity.username);
                                temp.setDown(downName);
                                temp.setCalled(called);
                                temp.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(NumberAdd.this, "添加成功", Toast.LENGTH_SHORT).show();
                                            startActivity(intent1);
                                            finish();
                                        } else {
                                            Toast.makeText(NumberAdd.this, "添加失败,请检测网络连接", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(NumberAdd.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(NumberAdd.this, "查询失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relation p2 = new relation();
                p2.setObjectId(relationId);
                p2.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(NumberAdd.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NumberAdd.this, "删除失败,请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        });
    }
}