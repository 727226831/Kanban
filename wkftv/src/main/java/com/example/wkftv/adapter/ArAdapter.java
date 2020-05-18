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
import com.example.wkftv.databinding.ItemArBinding;
import com.example.wkftv.databinding.ItemLinconBinding;
import com.google.gson.Gson;

import java.util.List;

public class ArAdapter  extends RecyclerView.Adapter<ArAdapter.VH>{
    ItemArBinding itemArBinding;
    @NonNull
    @Override
    public ArAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemArBinding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_ar,viewGroup,false);

        return new ArAdapter.VH(itemArBinding.getRoot());
    }

    private List<DataBean> mDatas;
    public ArAdapter(List<DataBean> data) {
        this.mDatas = data;
    }

    @Override
    public void onBindViewHolder(@NonNull ArAdapter.VH vh, final int i) {
        ItemArBinding binding=DataBindingUtil.getBinding(vh.itemView);

        DataBean data=mDatas.get(i);
        if(i==0){
            binding.llLayout.setBackgroundResource(R.color.ar_title);
            binding.tvAr8.setBackgroundResource(R.drawable.bg_border_trans);
        }else {
            binding.llLayout.setBackgroundResource(R.color.ar_bg);
            if(data.getItem8().equals("未开始")){
                binding.tvAr8.setBackgroundResource(R.drawable.bg_border_red);
            }else if(data.getItem8().equals("待加工")){
                binding.tvAr8.setBackgroundResource(R.drawable.bg_border_green);
            }else {
                binding.tvAr8.setBackgroundResource(R.drawable.bg_border_white);
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
