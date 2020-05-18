package com.example.wkftv.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wkftv.DataBean;
import com.example.wkftv.R;
import com.example.wkftv.databinding.ItemAr6Binding;
import com.example.wkftv.databinding.ItemAr7Binding;

import java.util.List;

public class Item6Adapter  extends RecyclerView.Adapter< Item6Adapter.VH>{
    ItemAr6Binding itemArBinding;
    @NonNull
    @Override
    public  Item6Adapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        itemArBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_ar6,viewGroup,false);

        return new  Item6Adapter.VH(itemArBinding.getRoot());
    }

    private List<DataBean> mDatas;
    public  Item6Adapter(List<DataBean> data) {
        this.mDatas = data;
    }

    @Override
    public void onBindViewHolder(@NonNull  Item6Adapter.VH vh, final int i) {
        ItemAr6Binding  binding= DataBindingUtil.getBinding(vh.itemView);


        if(i==0){
            binding.llLayout.setBackgroundResource(R.color.ar_title);
        }else {
            binding.llLayout.setBackgroundResource(R.color.ar_bg);
        }

        DataBean data=mDatas.get(i);
        binding.setData(data);
        binding.executePendingBindings();



    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
