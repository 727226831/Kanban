package com.example.wkftv.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wkftv.DataBean;
import com.example.wkftv.FunctionAdapter;
import com.example.wkftv.R;
import com.example.wkftv.databinding.ItemAr7Binding;
import com.example.wkftv.databinding.ItemItem9Binding;
import com.example.wkftv.databinding.ItemLinconBinding;

import java.util.List;

public class Item9Adapter  extends RecyclerView.Adapter< Item9Adapter.VH>{
    ItemItem9Binding binding;
    @NonNull
    @Override
    public Item9Adapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    binding  = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.item_item9,viewGroup,false);

        return new Item9Adapter.VH(binding.getRoot());
    }

    private List<DataBean> mDatas;
    private Context context;
    public Item9Adapter(List<DataBean> data, Context context) {
        this.mDatas = data;
        this.context=context;

    }

    @Override
    public void onBindViewHolder(@NonNull Item9Adapter.VH vh, final int i) {
        binding=DataBindingUtil.getBinding(vh.itemView);





        DataBean data=mDatas.get(i);
        if(i==0){
            binding.llLayout.setBackgroundResource(R.color.ar_title);
        }else {
            binding.llLayout.setBackgroundResource(R.color.ar_bg);
        }


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
