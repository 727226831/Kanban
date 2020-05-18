package com.example.wkftv;

import android.content.Context;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wkftv.databinding.ItemLinconBinding;
import com.example.wkftv.databinding.ItemLinconcomponentsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinconComponentsActivity extends AppCompatActivity {
    List<DataBean> dataBeans=new ArrayList<>();
    RecyclerView recyclerView;
    Handler handler;
    TextView textViewTitle,textViewNickname;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lincon_components);
        recyclerView=findViewById(R.id.rv_list);
        textViewTitle=findViewById(R.id.tv_title);
        textViewNickname=findViewById(R.id.tv_nickname);

        if(getIntent().getStringExtra("title").equals("到货状态")){
            textViewTitle.setText("材料到货看板系统");
            textViewNickname.setText("Components Incoming Status Kanban");
        }else {
            textViewTitle.setText("产线材料补料看板系统");
            textViewNickname.setText("Components Supplement Kanban");
        }
        getData();

        handler=new Handler();
        handler.postDelayed(runnable,60000);

    }
    Runnable runnablePager=new Runnable() {
        @Override
        public void run() {
            if(isRun) {
                handler.postDelayed(this::run, 60000);
               i++;
            }
        }
    };
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(isRun) {
                handler.postDelayed(this::run, 60000);
                getData();
            }
        }
    };


    Boolean isRun=true;

    @Override
    protected void onDestroy() {
        isRun=false;
        super.onDestroy();

    }

    private void getData() {
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("acccode","");
            jsonObject.put("methodname","getReportData");
            jsonObject.put("usercode","");
            jsonObject.put("reportcode",getIntent().getStringExtra("title"));
            jsonObject.put("condition","");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        Call<ResponseBody> data = Request.getRequestbody(obj);

        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dataBeans=new ArrayList<>();
                try {

                    JsonArray arry = new JsonParser().parse(response.body().string()).getAsJsonArray();
                    for (JsonElement jsonElement : arry) {

                        dataBeans.add(new Gson().fromJson(jsonElement.toString(),DataBean.class));
                    }
                    FunctionAdapter   functionAdapter=new FunctionAdapter(dataBeans,LinconComponentsActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(LinconComponentsActivity.this));
                    //   recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
                    recyclerView.addItemDecoration(new SpacesItemDecoration(0));
                    recyclerView.setAdapter(functionAdapter);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH>{
        ItemLinconcomponentsBinding binding;
        @NonNull
        @Override
        public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.item_linconcomponents,viewGroup,false);

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
            binding=DataBindingUtil.getBinding(vh.itemView);

            if(mDatas.get(i).getItem0()!=0) {
                binding.llLayout.setBackgroundResource(R.color.yellow);

            }
            int color;
            if(i==0){
                binding.llLayout.setBackgroundResource(R.color.blue);
                color= ContextCompat.getColor(context,R.color.white);
            }else {
                color= ContextCompat.getColor(context,R.color.black);
                binding.tvRow4.setBackgroundResource(R.drawable.border_yellow);
            }

            DataBean data=mDatas.get(i);

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
}
