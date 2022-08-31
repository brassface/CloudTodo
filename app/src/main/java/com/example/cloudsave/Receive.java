package com.example.cloudsave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Receive extends AppCompatActivity {
    private String todoId,username,mode,toUserName;
    private EditText title,place,month,day,hour,minute;
    private Button save,back,delete;
    private Spinner mySpinner;
    private TextView to_edit;
    private ArrayAdapter adapter;
    private List allItems=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        Intent intent=getIntent();
        Intent intent1=new Intent(Receive.this, BottomNavigationActivity.class);
        todoId=intent.getStringExtra("todoId");
        username=intent.getStringExtra("username");
        mode=intent.getStringExtra("mode");
        title=findViewById(R.id.receive_title_edit);
        place=findViewById(R.id.receive_place_edit);
        month=findViewById(R.id.receive_month_edit);
        day=findViewById(R.id.receive_day_edit);
        hour=findViewById(R.id.receive_hour_edit);
        minute=findViewById(R.id.receive_minute_edit);
        save=findViewById(R.id.receive_button_save);
        back=findViewById(R.id.receive_button_back);
        delete=findViewById(R.id.receive_button_delete);
        mySpinner=findViewById(R.id.receive_to_spinner);
        to_edit=findViewById(R.id.receive_to_edit);
        if (mode.equals("0")){
            delete.setVisibility(View.INVISIBLE);
        }else{
            delete.setVisibility(View.VISIBLE);
        }
        BmobQuery<todo> bmobQuery = new BmobQuery<todo>();
        bmobQuery.getObject(todoId, new QueryListener<todo>() {
            @Override
            public void done(todo object, BmobException e) {
                if(e==null){
                    title.setText(object.getTitle());
                    place.setText(object.getPlace());
                    month.setText(object.getMonth());
                    day.setText(object.getDay());
                    hour.setText(object.getHour());
                    minute.setText(object.getMinute());
                    to_edit.setText(object.getTo());
                }else{
                    Toast.makeText(Receive.this, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        allItems.add("");
        allItems.add(username);
        BmobQuery<relation> query0 = new BmobQuery<>();
        query0.addWhereEqualTo("up",username).findObjects(new FindListener<relation>() {
            @Override
            public void done(List<relation> object, BmobException e) {
                if (e == null) {
                    for (int i=0;i<object.size();i++){
                        allItems.add(object.get(i).getDown());
                    }
                } else {
                    Toast.makeText(Receive.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, allItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View arg1,
                                       int arg2, long arg3) {
                to_edit.setText(mySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo temp = new todo();
                temp.setObjectId(todoId);
                temp.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(Receive.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Receive.this, "删除失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(intent1);
                        finish();
                    }

                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo temp = new todo();
                temp.setTitle(title.getText().toString());
                temp.setPlace(place.getText().toString());
                temp.setMonth(month.getText().toString());
                temp.setDay(day.getText().toString());
                temp.setHour(hour.getText().toString());
                temp.setMinute(minute.getText().toString());
                temp.setFrom(username);
                temp.setTo(to_edit.getText().toString());
                temp.update(todoId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(Receive.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Receive.this, "保存失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        });
    }
}