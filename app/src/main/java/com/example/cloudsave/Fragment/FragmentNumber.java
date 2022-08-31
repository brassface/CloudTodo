package com.example.cloudsave.Fragment;


import static com.example.cloudsave.BottomNavigationActivity.username;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cloudsave.AdapterAll.NumberAdapter;
import com.example.cloudsave.BottomNavigationActivity;
import com.example.cloudsave.NumberAdd;
import com.example.cloudsave.R;
import com.example.cloudsave.Receive;
import com.example.cloudsave.relation;
import com.example.cloudsave.todo;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentNumber extends Fragment {

    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    List<relation> data;
    private NumberAdapter numberAdapter;
    private String username=BottomNavigationActivity.username;
    private Button buttonAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentnumber, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), NumberAdd.class);
                in.putExtra("mode","0");
                startActivity(in);
            }
        });
        //初始化刷新
        Refresh();

        //逻辑处理

        srlayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {
        BmobQuery<relation> query = new BmobQuery<>();
        query.setLimit(1000).addWhereEqualTo("up",username).order("-createdAt").findObjects(new FindListener<relation>() {
            @Override
            public void done(List<relation> list, BmobException e) {
                srlayout.setRefreshing(false);
                if (e==null){
                    data=list;
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    numberAdapter=new NumberAdapter(getActivity(),data);
                    rv.setAdapter(numberAdapter);
                }else{
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"获取数据失败"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initView() {
        rv=getActivity().findViewById(R.id.recyclerview3);
        srlayout=getActivity().findViewById(R.id.swipe3);
        buttonAdd=getActivity().findViewById(R.id.number_image_add);
    }
}
