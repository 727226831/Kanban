package com.example.wkftv;

import android.content.ClipData;

import android.graphics.Rect;
import android.os.Handler;

import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wkftv.adapter.Item6Adapter;
import com.example.wkftv.adapter.Item9Adapter;
import com.example.wkftv.databinding.ItemListBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tencent.bugly.beta.Beta;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.Html.FROM_HTML_MODE_LEGACY;


public class MainActivity extends AppCompatActivity {
    List<String> stringList=new ArrayList<>();
    RecyclerView recyclerView;
    RadioGroup radioGroup;
    String cdepname="CNC机加工";
    List<DataBean> dataBeans=new ArrayList<>();
    LinearLayout linearLayout;
    Handler handler;
    Item9Adapter   functionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout=findViewById(R.id.ll_layout);
        recyclerView=findViewById(R.id.rv_list);

        radioGroup=findViewById(R.id.rg_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case  R.id.rb_class1:
                        cdepname="CNC机加工";
                        break;
                    case  R.id.rb_class2:
                        cdepname="冲压车间";
                        break;
                    case  R.id.rb_class3:
                        cdepname="模压&烧结";
                        break;
                    case  R.id.rb_class4:
                        cdepname="组装&包装";
                        break;

                }

                getData(cdepname);
            }
        });

        getData(cdepname);

        handler=new Handler();
        handler.postDelayed(runnable,60000*5);

    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(isRun) {
                handler.postDelayed(this::run, 5000);
                getData(cdepname);
            }
        }
    };
    Boolean isRun=true;

    @Override
    protected void onDestroy() {
         isRun=false;
        super.onDestroy();

    }

    private void getData(String cdepname) {
        JSONObject jsonObject=new JSONObject();
        try {

         jsonObject.put("acccode","001");

            switch (getIntent().getIntExtra("type",-1)){
                case 1:
                    jsonObject.put("methodname","GetMoRpt");
                    jsonObject.put("cdepname",cdepname);
                    break;
                case 2:
                    jsonObject.put("methodname","GetInvRpt");
                    jsonObject.put("cinvcode","");
                    linearLayout.setVisibility(View.GONE);
                    break;
            }
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
                    if(response.code()!=200){
                        return;
                    }
                    JsonArray arry = new JsonParser().parse(response.body().string()).getAsJsonArray();
                    for (JsonElement jsonElement : arry) {

                      dataBeans.add(new Gson().fromJson(jsonElement.toString(),DataBean.class));
                    }
                    switch (getIntent().getIntExtra("type",-1)){
                        case 1:
                            functionAdapter=new Item9Adapter(dataBeans,MainActivity.this);
                            recyclerView.setAdapter(functionAdapter);
                            functionAdapter.notifyDataSetChanged();
                            break;
                        case 2:
                            Item6Adapter item6Adapter=new Item6Adapter(dataBeans);
                            recyclerView.setAdapter(item6Adapter);
                            item6Adapter.notifyDataSetChanged();
                            break;
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.addItemDecoration(new SpacesItemDecoration(0));



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                  Log.i("onfail",t.toString());
            }
        });
    }






}
