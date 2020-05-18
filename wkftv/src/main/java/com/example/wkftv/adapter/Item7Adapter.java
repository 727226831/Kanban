package com.example.wkftv.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wkftv.DataBean;
import com.example.wkftv.R;
import com.example.wkftv.databinding.ItemAr7Binding;
import com.example.wkftv.databinding.ItemArBinding;

import java.util.List;

public class Item7Adapter  extends RecyclerView.Adapter< Item7Adapter.VH>{
    ItemAr7Binding itemArBinding;
    @NonNull
    @Override
    public  Item7Adapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        itemArBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_ar7,viewGroup,false);

        return new  Item7Adapter.VH(itemArBinding.getRoot());
    }

    private List<DataBean> mDatas;
    public  Item7Adapter(List<DataBean> data) {
        this.mDatas = data;
    }

    @Override
    public void onBindViewHolder(@NonNull  Item7Adapter.VH vh, final int i) {
        ItemAr7Binding  binding= DataBindingUtil.getBinding(vh.itemView);


        if(i==0){
            binding.llLayout.setBackgroundResource(R.color.ar_title);
        }else {
            binding.llLayout.setBackgroundResource(R.color.ar_bg);
        }



        DataBean data=mDatas.get(i);
        if(mDatas.get(0).getItem5().equals("计划发货数")){
            if(i>0){

                if(Integer.parseInt(mDatas.get(i).getItem5())<Integer.parseInt(mDatas.get(i).getItem6())){
                    binding.llLayout.setBackgroundResource(R.color.red);
                }else {
                    binding.llLayout.setBackgroundResource(R.color.ar_bg);
                }
            }

        }
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
