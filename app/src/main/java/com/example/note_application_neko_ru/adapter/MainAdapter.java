package com.example.note_application_neko_ru.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_application_neko_ru.EditActivity;
import com.example.note_application_neko_ru.R;
import com.example.note_application_neko_ru.db.MyConstants;
import com.example.note_application_neko_ru.db.MyDbManager;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;
    private List<RcvItemList> mainArray;

    public MainAdapter(Context context){

        this.context = context;
        mainArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new MyViewHolder(view, context, mainArray);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.SetData(mainArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mainArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView RVitem;
        private Context mvhcontext;
        private List<RcvItemList> MainList;

        public MyViewHolder(@NonNull View itemView,Context context,List<RcvItemList> MList) {

            super(itemView);
            this.MainList = MList;
            RVitem = itemView.findViewById(R.id.RVitem);
            itemView.setOnClickListener(this);
            this.mvhcontext = context;

        }

        public void SetData(String data){
            RVitem.setText(data);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(mvhcontext, EditActivity.class);
            i.putExtra(MyConstants.LIST_ITEM_INTENT, MainList.get(getAdapterPosition()));
            i.putExtra(MyConstants.EDIT_STATE, false);
            mvhcontext.startActivity(i);

        }
    }

    public void updateAdapter(List<RcvItemList> newList){

        mainArray.clear();
        mainArray.addAll(newList);
        notifyDataSetChanged();
    }

    public void DeleteItem(int pos, MyDbManager dbManager){
        dbManager.DeleteFromDb(mainArray.get(pos).getId());
        mainArray.remove(pos);
        notifyItemChanged(0,mainArray.size());
        notifyItemRemoved(pos);
    }
}
