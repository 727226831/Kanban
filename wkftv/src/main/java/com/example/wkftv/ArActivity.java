package com.example.wkftv;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wkftv.adapter.ArAdapter;
import com.example.wkftv.adapter.Item4Adapter;
import com.example.wkftv.adapter.Item6Adapter;
import com.example.wkftv.adapter.Item7Adapter;
import com.example.wkftv.adapter.YearXAxisFormatter;
import com.example.wkftv.databinding.ActivityArBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArActivity extends AppCompatActivity {
    List<DataBean> dataBeans=new ArrayList<>();
    Handler handler;

    ActivityArBinding binding;
     int textsize=16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_ar);

        binding.tvTitle.setText(getIntent().getStringExtra("title"));

        initPieChart(binding.pcChart);
        initBarChart(binding.bcChart);
        initHorizontalBarChart(binding.hbcChart);


        if(getIntent().getStringExtra("title").equals("钎焊区生产看板")){
            binding.rlChart1.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("title").equals("测漏区域生产看板")){
            binding.rlChart1.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("title").equals("管端弯管区域生产看板")){
            binding.rlChart1.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("title").equals("材料仓库配送看板2")){
            binding.lChart.setVisibility(View.VISIBLE);
            binding.pcChart.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("title").equals("成品发货计划看板")){
            binding.lChart.setVisibility(View.VISIBLE);
            binding.bcChart.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("title").equals("质量异常看板")){
            binding.lChart.setVisibility(View.VISIBLE);
            binding.bcChart.setVisibility(View.VISIBLE);
            binding.pcChart.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("title").equals("库存水平")){
            binding.lChart.setVisibility(View.VISIBLE);
            binding.bcChart.setVisibility(View.VISIBLE);
        }


        handler=new Handler();
        handler.post(runnableUpdate);
        handler.post(runnableTime);
        handler.post(runnableChart);


    }

    private void getData() {

        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("acccode","036");
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


                    binding.rvList.setLayoutManager(new LinearLayoutManager(ArActivity.this));
                 //   binding.rvList.addItemDecoration(CommItemDecoration.createHorizontal(ArActivity.this, Color.WHITE,0));
                    if(getIntent().getStringExtra("title").equals("质量异常看板")) {
                        if(dataBeans.size()>1) {
                            getErrorCount();
                            setPieData(binding.pcChart, Integer.parseInt(dataBeans.get(1).getItem5()),
                                    Integer.parseInt(dataBeans.get(1).getItem6()));
                        }
                    }

                    i=0;
                    time=1000*10;
                    isStart=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("on fail",call.toString());
            }
        });
    }

    private void getErrorCount() {
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("acccode","");
            jsonObject.put("methodname","getReportData");
            jsonObject.put("usercode","");
            jsonObject.put("reportcode","质量异常看板2");
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
                JsonArray arry = null;
                try {
                    arry = new JsonParser().parse(response.body().string()).getAsJsonArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (JsonElement jsonElement : arry) {

                        dataBeans.add(new Gson().fromJson(jsonElement.toString(),DataBean.class));
                    }


                setBatDataError(binding.bcChart, dataBeans);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void setBatDataError(BarChart chart, List<DataBean> dataBeans) {




       ArrayList<String> arrayList=new ArrayList<>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(57,107,197));
        colors.add(Color.rgb(57,107,197));
        colors.add(Color.rgb(243,120,37));
        colors.add(Color.rgb(220,20,60));
        colors.add(Color.rgb(255,255,0));

        if(getIntent().getStringExtra("title").equals("质量异常看板")) {
            for (int i = 0; i <dataBeans.size() ; i++) {
                    arrayList.add(dataBeans.get(i).getItem1());

            }
            for (int i = 1; i <dataBeans.size() ; i++) {
                ArrayList<BarEntry> values = new ArrayList<>();
                values.add(new BarEntry(i, Integer.parseInt(dataBeans.get(i).getItem2())));
                BarDataSet barDataSet=new BarDataSet(values,arrayList.get(i));
                barDataSet.setColor(colors.get(i));
                dataSets.add(barDataSet);
            }
        }







        BarData data = new BarData(dataSets);
        data.setValueFormatter(new StackedValueFormatter(false, "", 1));
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(textsize);
        chart.setData(data);
        chart.setFitBars(true);
        chart.invalidate();
    }

    private void setHorizontalBarChart(HorizontalBarChart chart) {
        ArrayList<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(
                0,
                new float[]{187, 300}));
        values.add(new BarEntry(
                1,
                new float[]{87, 400}));


        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "");
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.rgb(57,107,197));
            colors.add(Color.rgb(243,120,37));
            set1.setColors(colors);
            set1.setStackLabels(new String[]{""});
            set1.setValueTextColor(Color.WHITE);
            set1.setValueTextSize(16f);

            BarData data = new BarData(set1);
            data.setValueTextColor(Color.WHITE);


            chart.setData(data);
        }

        chart.setFitBars(true);
        chart.invalidate();
    }


    private void initHorizontalBarChart(HorizontalBarChart chart) {
        chart.setBackgroundColor(Color.TRANSPARENT);


        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(13f);
        xAxis.setLabelCount(5);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);


        YAxis left = chart.getAxisLeft();

        left.setDrawLabels(false);
        left.setDrawAxisLine(false);
        left.setDrawGridLines(false);
        left.setDrawZeroLine(true); // draw a zero line
        left.setZeroLineColor(Color.GRAY);
        left.setZeroLineWidth(0.7f);
        chart.getAxisRight().setEnabled(false);


        final List<Data> data = new ArrayList<>();
        data.add(new Data(0f, -224.1f, ""));
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return data.get(Math.min(Math.max((int) value, 0), data.size()-1)).xAxisValue;
            }
        });


        // THIS IS THE ORIGINAL DATA YOU WANT TO PLOT


        setHorizontalBarChart(binding.hbcChart);
    }

    private void initBarChart(BarChart chart) {
        chart.setBackgroundColor(Color.TRANSPARENT);


        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);


        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        chart.getLegend().setEnabled(true);
        // THIS IS THE ORIGINAL DATA YOU WANT TO PLOT
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);

        chart.getAxisLeft().setAxisMaximum(100000);





       // chart.getXAxis().setEnabled(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTextColor(Color.WHITE);
        l.setTextSize(textsize);

    }
    private void setBatData(BarChart chart,DataBean dataBean) {

        ArrayList<BarEntry> values1 = new ArrayList<>();
        values1.add(new BarEntry(0, Integer.parseInt(dataBean.getItem8())));
        BarDataSet barDataSet1=new BarDataSet(values1,"成品库存不足");
        barDataSet1.setColor(Color.rgb(57,107,197));

        ArrayList<BarEntry> values2 = new ArrayList<>();
        values2.add(new BarEntry(1, Integer.parseInt(dataBean.getItem9())));
        BarDataSet barDataSet2=new BarDataSet(values2,"未完成发货");

        barDataSet2.setColor(Color.rgb(243,120,37));

        ArrayList<BarEntry> values3 = new ArrayList<>();
        values3.add(new BarEntry(2, Integer.parseInt(dataBean.getItem10())));
        BarDataSet barDataSet3=new BarDataSet(values3,"已经完成发货");
        barDataSet3.setColor(Color.rgb(220,20,60));


        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3);
        //    data.setValueFormatter(new StackedValueFormatter(false, "", 1));
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(textsize);
        chart.setData(data);
        chart.invalidate();

    }
    private class Data {

        final String xAxisValue;
        final float yValue;
        final float xValue;

        Data(float xValue, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xValue = xValue;
        }
    }



    private void initPieChart(PieChart chart) {
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
      //  chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        //chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        //是否画中间圆环
        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);


        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(50);

        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        //设置图例位置
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTextColor(Color.WHITE);
        l.setTextSize(textsize);
        l.setYOffset(10);




    }

    private void setPieData( PieChart chart,int done,int total) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        if(getIntent().getStringExtra("title").equals("质量异常看板")){
            entries.add(new PieEntry(total,"正常运行数量" ));
            entries.add(new PieEntry(done,"异常数量"));
        }else {
            entries.add(new PieEntry(total,"已完成数量" ));
            entries.add(new PieEntry(done,"未完成数量"));
        }


        PieDataSet dataSet = new PieDataSet(entries, "");


        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(237,125,49));
        colors.add(Color.rgb(91,155,213));

        dataSet.setColors(colors);
        dataSet.setValueTextSize(textsize);

        //
        dataSet.setUsingSliceColorAsValueLineColor(true);


        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        PieData data = new PieData(dataSet);
// 设置指示文字

        data.setValueTextSize(textsize);
        //
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);


        chart.invalidate();


    }

    Runnable runnableUpdate =new Runnable() {
        @Override
        public void run() {
            if(isRun) {
                handler.postDelayed(this::run, 1000*60*3);
                getData();
            }
        }
    };

    List<DataBean> dataBeansShow;
    Boolean isStart=false;
    int i=0;
    int time=1000;
    Runnable runnableChart =new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this::run, time);
            if(isStart) {
               Log.i("i",i+"");
                if(getIntent().getStringExtra("title").equals("钎焊区生产看板")){
                    if(dataBeans.size()>1) {
                        if(i==0){
                            initInfo(dataBeans.get(1));
                        }else {
                            initInfo(dataBeans.get(i));
                        }

                    }


                    if(i %8==0 && i!=dataBeans.size()-1){
                        ArAdapter functionAdapter = new ArAdapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();

                    }


                }else if(getIntent().getStringExtra("title").equals("测漏区域生产看板")){
                    if( dataBeans.size()>1) {

                        if(i==0){
                            initInfo(dataBeans.get(1));
                        }else {
                            initInfo(dataBeans.get(i));
                        }
                    }
                    if(i %8==0 && i!=dataBeans.size()-1){
                        ArAdapter functionAdapter = new ArAdapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();
                    }
                }else if(getIntent().getStringExtra("title").equals("管端弯管区域生产看板")){
                    if( dataBeans.size()>1) {

                        if(i==0){
                            initInfo(dataBeans.get(1));
                        }else {
                            initInfo(dataBeans.get(i));
                        }
                    }
                    if(i %8==0 && i!=dataBeans.size()-1){
                        ArAdapter functionAdapter = new ArAdapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();
                    }
                }else if(getIntent().getStringExtra("title").equals("材料仓库配送看板")){

                    if(i %8==0 && i!=dataBeans.size()-1){
                        Item7Adapter functionAdapter=new Item7Adapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();
                    }


                }else if(getIntent().getStringExtra("title").equals("材料仓库配送看板2")){

                    if( dataBeans.size()>1) {
                        if(i==0){
                            setPieData(binding.pcChart,Integer.parseInt(dataBeans.get(1).getItem7()),
                                    Integer.parseInt(dataBeans.get(1).getItem8()));
                        }else {
                            setPieData(binding.pcChart,Integer.parseInt(dataBeans.get(i).getItem7()),
                                    Integer.parseInt(dataBeans.get(i).getItem8()));
                        }

                    }
                    if(i %8==0 && i!=dataBeans.size()-1){
                        Item6Adapter functionAdapter=new Item6Adapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();
                    }


                }else if(getIntent().getStringExtra("title").equals("材料仓库库存水平看板")){
                    if(i %8==0 && i!=dataBeans.size()-1){
                        Item6Adapter functionAdapter=new Item6Adapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();
                    }

                }else if(getIntent().getStringExtra("title").equals("成品发货计划看板")){

                    if(dataBeans.size()>1) {
                        if(i==0){
                            setBatData(binding.bcChart,dataBeans.get(1));
                        }else {
                            setBatData(binding.bcChart,dataBeans.get(i));
                        }

                    }



                    if(i %8==0 && i!=dataBeans.size()-1){
                        Item7Adapter functionAdapter=new Item7Adapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();
                    }

                }else if(getIntent().getStringExtra("title").equals("质量异常看板")){


                    if(i %8==0 && i!=dataBeans.size()-1){
                        Item4Adapter functionAdapter=new Item4Adapter(getPageList());
                        binding.rvList.setAdapter(functionAdapter);
                        functionAdapter.notifyDataSetChanged();
                    }




                }

                if(i<dataBeans.size()-1) {
                    i++;
                }else {
                    i=0;
                }
            }
        }
    };

    private  List<DataBean>  getPageList() {



            dataBeansShow = new ArrayList<>();
                for (int j = i  + 1; j < dataBeans.size(); j++) {
                    dataBeansShow.add(dataBeans.get(j));
                }

                if (!dataBeansShow.get(0).getItem1().equals("No")) {
                    dataBeansShow.add(0, dataBeans.get(0));
                }


        return  dataBeansShow;
    }

    Runnable runnableTime=new Runnable() {
        @Override
        public void run() {
            if(isRun) {
                handler.postDelayed(this::run, 1000);
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                binding.tvTime.setText(dateFormat.format(currentTime));
            }
        }
    };
    Boolean isRun=true;

    @Override
    protected void onDestroy() {
        isRun=false;
        isStart=false;
        super.onDestroy();

    }





    private void initInfo(DataBean bean) {
        binding.tvLine.setText(bean.getItem2());
        binding.tvNumber.setText("加工图号："+bean.getItem3());
        binding.tvName.setText(bean.getItem4());
        binding.tvEfficiency.setText("产出效率："+bean.getItem9());
        binding.tvGood.setText("良品率："+bean.getItem10());
        binding.tvTotal.setText(" 总进度："+bean.getItem6()+"/"+bean.getItem5());
        binding.tvPlan.setText("计划进度："+bean.getItem11());
        binding.tvUser.setText(bean.getItem13());
        binding.tvId.setText(bean.getItem14());
        binding.tvTellphone.setText(bean.getItem15());
        Log.i("image-->",Request.URL_AR+"/pic/"+bean.getItem12());
        Picasso.get().load(Request.URL_AR+"/pic/"+bean.getItem12()).into(binding.ivInfo);


    }
}
