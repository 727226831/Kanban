package com.example.wkftv;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wkftv.databinding.ItemHs2Binding;
import com.example.wkftv.databinding.ItemHsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hs2Activity extends AppCompatActivity {

    List<DataBean> dataBeans=new ArrayList<>();
    RecyclerView recyclerView;
    Handler handler;
    TextView textViewTime,textViewCompany,textViewTitle;
    List<DataBean> dataBeansShow;
    Boolean isStart=false;
    int i=0;
    int time=1000;
    private SpeechSynthesizer mTts;
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    public static String voicerCloud="xiaoyan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hs2);

        requestPermissions();

        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);

        recyclerView=findViewById(R.id.rv_list);
        textViewTime=findViewById(R.id.tv_time);
        textViewCompany=findViewById(R.id.tv_company);
        textViewTitle=findViewById(R.id.tv_title);


        getData();
        setParam();
        handler=new Handler();
        handler.postDelayed(runnableRefresh,60000*5);
        handler.post(runnableTime);
        handler.post(runnableChart);
        handler.post(runnableTts);


    }
    int tagTts=0;
    Runnable runnableTts=new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this::run, 10 * 1000);
            if(isStart) {



                if(mTts!=null){
                    mTts.startSpeaking(dataBeans.get(tagTts).getItem110(), mTtsListener);
                }

                if(tagTts<dataBeans.size()-1){
                    tagTts++;
                }else {
                    i=0;
                }

            }
        }
    };


    Runnable runnableChart =new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this::run, time);
            if(isStart) {


                if(i %9==0 && i!=dataBeans.size()-1){

                    FunctionAdapter functionAdapter=new FunctionAdapter(getPageList(),Hs2Activity.this);
                    recyclerView.setAdapter(functionAdapter);
                    functionAdapter.notifyDataSetChanged();
                }

            }

            if(i<dataBeans.size()-1) {
                i++;
            }else {
                i=0;
            }
        }

    };
    private  List<DataBean>  getPageList() {



        dataBeansShow = new ArrayList<>();
        for (int j = i  + 1; j < dataBeans.size(); j++) {
            dataBeansShow.add(dataBeans.get(j));
        }

        if (!dataBeansShow.get(0).getItem1().equals("仓库")) {
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
                textViewTime.setText(dateFormat.format(currentTime));
            }
        }
    };
    Runnable runnableRefresh =new Runnable() {
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
        if(mTts!=null){
            mTts.stopSpeaking();
        }

        isStart=false;
    }

    private void getData() {
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("acccode","036");
            jsonObject.put("methodname","getReportData");
            jsonObject.put("usercode","");
            jsonObject.put("reportcode",getIntent().getStringExtra("title"));
            jsonObject.put("condition",getIntent().getStringExtra("condition"));


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
                    FunctionAdapter functionAdapter=new FunctionAdapter(dataBeans,Hs2Activity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Hs2Activity.this));
                    //   recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
                    recyclerView.addItemDecoration(new SpacesItemDecoration(0));
                    recyclerView.setAdapter(functionAdapter);
                    textViewCompany.setText(dataBeans.get(0).getItem100());
                    textViewTitle.setText(dataBeans.get(0).getItem99());

                    i=0;
                    time=1000*5;
                    isStart=true;
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
        ItemHs2Binding binding;
        @NonNull
        @Override
        public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            binding= DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.item_hs2,viewGroup,false);

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
    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setParam(){
        if(mTts==null){
            Log.i("isrun","null");
            return;
        }
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        //设置合成
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD))
        {
            //设置使用云端引擎
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME,voicerCloud);

        }
        //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY,"1");//支持实时音频流抛出，仅在synthesizeToUri条件下支持
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE,"3");
        //	mTts.setParameter(SpeechConstant.STREAM_TYPE, AudioManager.STREAM_MUSIC+"");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");

        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");


    }
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            //showTip("开始播放");

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度

        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {

            } else if (error != null) {

            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_AUDIO_URL);

            }

            //实时音频流输出参考
			/*if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
				byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
				Log.e("MscSpeechLog", "buf is =" + buf);
			}*/
        }
    };

    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {
                if (code != ErrorCode.SUCCESS) {


                } else {
                    // 初始化成功，之后可以调用startSpeaking方法
                    // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                    // 正确的做法是将onCreate中的startSpeaking调用移至这里
                }

            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };
}
