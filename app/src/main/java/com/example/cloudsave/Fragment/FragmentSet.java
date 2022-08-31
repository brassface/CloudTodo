package com.example.cloudsave.Fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.cloudsave.RePassword;
import com.example.cloudsave.relation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentSet extends Fragment {
    private TextView textUsername;
    private Button buttonAbout,buttonRePassword,buttonClose;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentset, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        username=BottomNavigationActivity.username;

        textUsername=getActivity().findViewById(R.id.set_username_edit);
        buttonAbout=getActivity().findViewById(R.id.set_about_button);
        buttonClose=getActivity().findViewById(R.id.set_close_button);
        buttonRePassword=getActivity().findViewById(R.id.set_repassword_button);

        textUsername.setText(username);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "此版本更新于2022.08.26", Toast.LENGTH_SHORT).show();
            }
        });

        buttonRePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RePassword.class);
                startActivity(intent);
            }
        });
    }
}

