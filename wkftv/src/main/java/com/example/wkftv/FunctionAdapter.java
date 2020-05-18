package com.example.wkftv;

import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wkftv.databinding.ItemLinconBinding;
import com.example.wkftv.databinding.ItemListBinding;
import com.google.gson.Gson;

import java.util.List;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH>{
    @NonNull
    @Override
    public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemLinconBinding binding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.item_lincon,viewGroup,false);

        return new FunctionAdapter.VH(binding.getRoot());
    }

    private List<DataBean> mDatas;
    private Context context;
    public FunctionAdapter(List<DataBean> data, Context context) {
        this.mDatas = data;
        this.context=context;

    }

    @Override
    public void onBindViewHolder(@NonNull FunctionAdapter.VH vh, final int i) {
        ItemLinconBinding binding=DataBindingUtil.getBinding(vh.itemView);

        if(mDatas.get(i).getItem0()!=0) {
            binding.llLayout.setBackgroundColor(Color.YELLOW);
        }
        if(i==0){
            binding.llLayout.setBackgroundResource(R.color.blue);

        }

        DataBean data=mDatas.get(i);
        int color;
        if(i==0){
             color= ContextCompat.getColor(context,R.color.white);
        }else {
           color= ContextCompat.getColor(context,R.color.black);
        }
        data.setTextColor(color);

        binding.setData(data);
        binding.executePendingBindings();



    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    class  VH extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        public VH(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.ll_layout);


        }
    }
}
