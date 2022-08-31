package com.example.cloudsave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.MyViewHolder> {
    private Context myContext;
    private LayoutInflater layoutInflater;
    private List<relation> myData;
    private OnItemClickListener myClickListener;
    public FamilyAdapter(Context mContext, List<relation> mData) {
        myData = mData;
        myContext = mContext;
        layoutInflater = LayoutInflater.from(myContext);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.family_single, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.getObject(myData.get(position).getDown(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if(e==null){
                    holder.name.setText(object.getName());
                }else{
                    Toast.makeText(myContext, "查询失败： "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(v -> {
            myClickListener.OnClick(position);
        });
    }
    @Override
    public int getItemCount() {
        return myData.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.family_name_edit);
        }
    }
    public void setOnItemClickListener(OnItemClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    interface OnItemClickListener{
        void OnClick(int position);
    }
}