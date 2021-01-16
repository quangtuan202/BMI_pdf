package com.tuan.exportpdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerAdapterViewHolder> {
    Context context;
    List<model> dataList=new ArrayList<>();
    private LayoutInflater RecyclerViewLayoutInflater;
    //modelRoomDB db;
    //Constructor
    public RecyclerAdapter(Context context, List<model> dataList){
        this.context = context;
        this.dataList = dataList;
        RecyclerViewLayoutInflater = LayoutInflater.from(context);

    }


    class RecyclerAdapterViewHolder extends RecyclerView.ViewHolder{
        // to refer to recycler items list
        private TextView txt_no,txt_change,txt_weight,txt_date,txt_bmi,txt_note,txt_picture;
        public RecyclerAdapterViewHolder(View itemView) {
            super(itemView);
            txt_no = itemView.findViewById(R.id.no_value);
            txt_change = itemView.findViewById(R.id.change_value);
            txt_weight = itemView.findViewById(R.id.weight_value);
            txt_date = itemView.findViewById(R.id.date_value);
            txt_bmi = itemView.findViewById(R.id.bmi_value);
            txt_note = itemView.findViewById(R.id.note_value);
            txt_picture = itemView.findViewById(R.id.picture_value);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycler_items_linear,parent,false);
        return new RecyclerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterViewHolder viewHolder, int position) {
        model data=dataList.get(position);
        viewHolder.txt_no.setText(String.valueOf(data.getId()));
        viewHolder.txt_change.setText("0");
        viewHolder.txt_weight.setText(String.valueOf(data.getWeight()));
        viewHolder.txt_date.setText(String.valueOf(data.getDate()));
        viewHolder.txt_bmi.setText(String.valueOf(data.getBmi()));
        viewHolder.txt_note.setText(data.getNote());
        viewHolder.txt_picture.setText(data.getPicture());
        //viewHolder.txt_picture.setText("00");
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
