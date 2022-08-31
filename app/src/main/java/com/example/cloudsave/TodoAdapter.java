package com.example.cloudsave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    private Context myContext;
    private LayoutInflater layoutInflater;
    private List<todo> myData;
    private OnItemClickListener myClickListener;
    public TodoAdapter(Context mContext, List<todo> mData) {
        myData = mData;
        myContext = mContext;
        layoutInflater = LayoutInflater.from(myContext);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.todo_single, null));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(myData.get(position).getTitle());
        holder.place.setText(myData.get(position).getPlace());
        holder.month.setText(myData.get(position).getMonth());
        holder.day.setText(myData.get(position).getDay());
        holder.hour.setText(myData.get(position).getHour());
        holder.minute.setText(myData.get(position).getMinute());
        holder.from.setText(myData.get(position).getFrom());
        holder.itemView.setOnClickListener(v -> {
            myClickListener.OnClick(position);
        });
    }
    @Override
    public int getItemCount() {
        return myData.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,place,month,day,hour,minute,from;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.todo_title_edit);
            place= itemView.findViewById(R.id.todo_place_edit);
            month=itemView.findViewById(R.id.todo_month_edit);
            day=itemView.findViewById(R.id.todo_day_edit);
            hour=itemView.findViewById(R.id.todo_hour_edit);
            minute=itemView.findViewById(R.id.todo_minute_edit);
            from=itemView.findViewById(R.id.todo_from_edit);
        }
    }
    public void setOnItemClickListener(OnItemClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    interface OnItemClickListener{
        void OnClick(int position);
    }
}