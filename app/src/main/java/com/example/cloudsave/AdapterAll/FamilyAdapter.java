package com.example.cloudsave.AdapterAll;

import static com.example.cloudsave.BottomNavigationActivity.username;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudsave.LoginActivity;
import com.example.cloudsave.R;
import com.example.cloudsave.Receive;
import com.example.cloudsave.todo;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class FamilyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<todo> data;
    private final int N_TYPE=0;
    private final int F_TYPE=1;
    private int Max_num=15;
    private boolean isfootview=true;
    private OnItemClickListener myClickListener;
    public FamilyAdapter(Context context, List<todo> data) {
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_single,viewGroup,false);
        View footview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if (i==F_TYPE){
            return new RecyclerViewHolder(footview,F_TYPE);
        }else {
            return new RecyclerViewHolder(view,N_TYPE);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isfootview && getItemViewType(i)==F_TYPE){
            final RecyclerViewHolder recyclerViewHolder=(RecyclerViewHolder) viewHolder;
            recyclerViewHolder.Loading.setText("加载中...");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Max_num+=8;
                    notifyDataSetChanged();
                }
            },2000);
        }else {
            final RecyclerViewHolder recyclerViewHolder=(RecyclerViewHolder) viewHolder;
            todo temp=data.get(i);
            recyclerViewHolder.to.setText("发送给");
            recyclerViewHolder.title.setText(temp.getTitle());
            recyclerViewHolder.place.setText(temp.getPlace());
            recyclerViewHolder.month.setText(temp.getMonth());
            recyclerViewHolder.day.setText(temp.getDay());
            recyclerViewHolder.hour.setText(temp.getHour());
            recyclerViewHolder.minute.setText(temp.getMinute());
            recyclerViewHolder.from.setText(temp.getTo());
            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerViewHolder.getAdapterPosition();
                    Intent in=new Intent(context, Receive.class);
                    in.putExtra("todoId",data.get(position).getObjectId());
                    in.putExtra("username",username);
                    in.putExtra("mode","1");
                    context.startActivity(in);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position){
      if (position==Max_num-1){
          return F_TYPE;
      }else {
          return N_TYPE;
      }
    };

    @Override
    public int getItemCount() {
        if (data.size()<Max_num) {
            return data.size();
        }else {
            return Max_num;
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView title,place,month,day,hour,minute,from,Loading,to;
        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type==N_TYPE) {
                title = itemView.findViewById(R.id.todo_title_edit);
                place = itemView.findViewById(R.id.todo_place_edit);
                month = itemView.findViewById(R.id.todo_month_edit);
                day = itemView.findViewById(R.id.todo_day_edit);
                hour = itemView.findViewById(R.id.todo_hour_edit);
                minute = itemView.findViewById(R.id.todo_minute_edit);
                from = itemView.findViewById(R.id.todo_from_edit);
                to=itemview.findViewById(R.id.todo_from_text);
            }else if (view_type==F_TYPE){
                Loading=itemview.findViewById(R.id.footText);
            }
        }
    }
    interface OnItemClickListener{
        void OnClick(int position);
    }
}