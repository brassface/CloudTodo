package com.example.cloudsave.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cloudsave.AdapterAll.FamilyAdapter;
import com.example.cloudsave.AdapterAll.HomeAdapter;
import com.example.cloudsave.BottomNavigationActivity;
import com.example.cloudsave.R;
import com.example.cloudsave.Receive;
import com.example.cloudsave.relation;
import com.example.cloudsave.todo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentFamily extends Fragment {

    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    List<todo> data=new ArrayList<>();
    private FamilyAdapter familyAdapter;
    private String username=BottomNavigationActivity.username;
    private Button buttonAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentfamily, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //初始化
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), Receive.class);
                in.putExtra("mode","0");
                startActivity(in);
            }
        });
        //刷新
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
        BmobQuery<relation> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("up", username);
        categoryBmobQuery.findObjects(new FindListener<relation>() {
            @Override
            public void done(List<relation> object, BmobException e) {
                if (e == null) {
                    //查询成功
                    int i=0;
                    while (i<object.size()) {
                        BmobQuery<todo> query = new BmobQuery<todo>();
                        query.setLimit(1000).addWhereEqualTo("to",object.get(i).getDown()).order("-createdAt").findObjects(new FindListener<todo>() {
                            @Override
                            public void done(List<todo> list, BmobException e) {
                                srlayout.setRefreshing(false);
                                if (e == null) {
                                    data.addAll(list);
                                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    familyAdapter = new FamilyAdapter(getActivity(), data);
                                    rv.setAdapter(familyAdapter);
                                } else {
                                    srlayout.setRefreshing(false);
                                    Toast.makeText(getActivity(), "获取数据失败" + e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        i++;
                    }
                } else {
                    Toast.makeText(getActivity(), "网络异常请稍后再试"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        rv=getActivity().findViewById(R.id.recyclerview2);
        srlayout=getActivity().findViewById(R.id.swipe2);
        buttonAdd=getActivity().findViewById(R.id.familytodo_image_add);
    }
}
