package com.example.wkftv;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class Request {

   //public  static String BASEURL="http://192.168.200.20:8881";
   // public  static String BASEURL="http://192.168.1.85:8881";

   public  static String URL_AR="http://ar_wmsapp.vaiwan.com";
    public  static String URL_WKF="http://47.100.172.243:8881";
    public  static String URL="http://skfwmsapp.vaiwan.com";
    public  static String URL_HS="http://pdhsservice.vaiwan.com";
    public  static  String URL_SKF="http://163.157.74.209:8881";
    public static Call<ResponseBody> getRequestbody(String obj) {

         OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS).build();
        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(URL).build();
        Log.i("url--->",URL);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        iUrl login = retrofit.create(iUrl.class);
        Call<ResponseBody> data = login.getMessage(body);

        return  data;
    }
}
