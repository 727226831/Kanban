package com.example.wkftv;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class LinconActivity extends AppCompatActivity {
    List<DataBean> dataBeans=new ArrayList<>();
    RecyclerView recyclerView;
    Handler handler;
    TextView textViewTitle,textViewNickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lincon);
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
                    FunctionAdapter   functionAdapter=new FunctionAdapter(dataBeans,LinconActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(LinconActivity.this));
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
}
