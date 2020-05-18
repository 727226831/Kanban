package com.example.wkftv;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wkftv.bean.TaskBean;
import com.example.wkftv.databinding.ActivityArChartBinding;
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

public class ArChartActivity extends AppCompatActivity {
    List<TaskBean> dataBeans=new ArrayList<>();

    Handler handler;
   ActivityArChartBinding binding;
    List<TextView> textViewList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_ar_chart);

        addView();
        getData();

        handler=new Handler();
        handler.postDelayed(runnable,60000*5);


    }

    private void addView() {
        textViewList.add(binding.tvKey1);
        textViewList.add(binding.tvKey2);
        textViewList.add(binding.tvKey3);
        textViewList.add(binding.tvKey4);
        textViewList.add(binding.tvKey5);
        textViewList.add(binding.tvKey6);
        textViewList.add(binding.tvKey7);
        textViewList.add(binding.tvKey8);
        textViewList.add(binding.tvKey9);
        textViewList.add(binding.tvKey10);
        textViewList.add(binding.tvKey11);
        textViewList.add(binding.tvKey12);
        textViewList.add(binding.tvKey13);
        textViewList.add(binding.tvKey14);
        textViewList.add(binding.tvKey15);
        textViewList.add(binding.tvKey16);
        textViewList.add(binding.tvKey17);
        textViewList.add(binding.tvKey18);
        textViewList.add(binding.tvKey19);
        textViewList.add(binding.tvKey20);
        textViewList.add(binding.tvKey21);
        textViewList.add(binding.tvKey22);
        textViewList.add(binding.tvKey23);
        textViewList.add(binding.tvKey24);
        textViewList.add(binding.tvKey25);
        textViewList.add(binding.tvKey26);
        textViewList.add(binding.tvKey27);
        textViewList.add(binding.tvKey28);
        textViewList.add(binding.tvKey29);
        textViewList.add(binding.tvKey30);
        textViewList.add(binding.tvKey31);
        textViewList.add(binding.tvKey32);
        textViewList.add(binding.tvKey33);
        textViewList.add(binding.tvKey34);
        textViewList.add(binding.tvKey35);
        textViewList.add(binding.tvKey36);
        textViewList.add(binding.tvKey37);
        textViewList.add(binding.tvKey38);
        textViewList.add(binding.tvKey39);
        textViewList.add(binding.tvKey40);
        textViewList.add(binding.tvKey41);
        textViewList.add(binding.tvKey42);
        textViewList.add(binding.tvKey43);
        textViewList.add(binding.tvKey44);
        textViewList.add(binding.tvKey45);
        textViewList.add(binding.tvKey46);
        textViewList.add(binding.tvKey47);
        textViewList.add(binding.tvKey48);
        textViewList.add(binding.tvKey49);
        textViewList.add(binding.tvKey50);
        textViewList.add(binding.tvKey51);
        textViewList.add(binding.tvKey52);
        textViewList.add(binding.tvKey53);
        textViewList.add(binding.tvKey54);
        textViewList.add(binding.tvKey55);
        textViewList.add(binding.tvKey56);
        textViewList.add(binding.tvKey57);
        textViewList.add(binding.tvKey58);
        textViewList.add(binding.tvKey59);
        textViewList.add(binding.tvKey60);
        textViewList.add(binding.tvKey61);
        textViewList.add(binding.tvKey62);
        textViewList.add(binding.tvKey63);
        textViewList.add(binding.tvKey64);
        textViewList.add(binding.tvKey65);
        textViewList.add(binding.tvKey66);
        textViewList.add(binding.tvKey67);
        textViewList.add(binding.tvKey68);
        textViewList.add(binding.tvKey69);
        textViewList.add(binding.tvKey70);
        textViewList.add(binding.tvKey71);
        textViewList.add(binding.tvKey72);
        textViewList.add(binding.tvKey73);
        textViewList.add(binding.tvKey74);
        textViewList.add(binding.tvKey75);
        textViewList.add(binding.tvKey76);
        textViewList.add(binding.tvKey77);
        textViewList.add(binding.tvKey78);
        textViewList.add(binding.tvKey79);
        textViewList.add(binding.tvKey80);

    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(isRun) {
                handler.postDelayed(this::run, 60000*5);
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
                        dataBeans.add(new Gson().fromJson(jsonElement.toString(),TaskBean.class));
                    }
                    for (int i = 0; i <dataBeans.size() ; i++) {
                        TextView textView=textViewList.get(i);
                        if(dataBeans.get(i).getId().equals("79")) {
                            textView.setText(dataBeans.get(i).getText());
                        }else {
                            textView.setText(Html.fromHtml(dataBeans.get(i).getText()));
                        }

                        textView.setTextSize(Integer.parseInt(dataBeans.get(i).getTextsize()));
                        textView.setBackgroundColor(Color.parseColor(dataBeans.get(i).getBackground()));


                    }


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
