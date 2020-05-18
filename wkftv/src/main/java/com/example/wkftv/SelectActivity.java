package com.example.wkftv;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wkftv.databinding.ActivitySelectBinding;
import com.example.wkftv.databinding.ItemListBinding;
import com.example.wkftv.databinding.ItemSelectorBinding;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    ActivitySelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_select);

        Request.URL=Request.URL_HS;

        if(Request.URL.equals(Request.URL_SKF)){
            binding.rgLincon.setVisibility(View.VISIBLE);
        }else  if(Request.URL.equals(Request.URL_HS)){
           binding.lHs.setVisibility(View.VISIBLE);
        }else  if(Request.URL.equals(Request.URL_WKF)){
            binding.rgWkf.setVisibility(View.VISIBLE);
        }else  if(Request.URL.equals(Request.URL_AR)){
            binding.rgAr.setVisibility(View.VISIBLE);
        }

        binding.rbWkf1.setOnClickListener(onClickListenerWkf);
        binding.rbWkf2.setOnClickListener(onClickListenerWkf);

        binding.rbAr0.setOnClickListener(onClickListenerAr);
        binding.rbAr1.setOnClickListener(onClickListenerAr);
        binding.rbAr2.setOnClickListener(onClickListenerAr);
        binding.rbAr3.setOnClickListener(onClickListenerAr);
        binding.rbAr4.setOnClickListener(onClickListenerAr);
        binding.rbAr5.setOnClickListener(onClickListenerAr);
        binding.rbAr6.setOnClickListener(onClickListenerAr);
        binding.rbAr7.setOnClickListener(onClickListenerAr);
        binding.rbAr8.setOnClickListener(onClickListenerAr);

        binding.rbLincon1.setOnClickListener(onClickListenerLincon);
        binding.rbLincon2.setOnClickListener(onClickListenerLincon);

       binding.rbHs1.setOnClickListener(hsClickListener);
       binding.rbHs2.setOnClickListener(hsClickListener);
        binding.etHs.requestFocus();
    }
    View.OnClickListener hsClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=null;
            switch (view.getId()){
                case R.id.rb_hs1:
                    intent=new Intent(SelectActivity.this,HsActivity.class);
                    break;
                case R.id.rb_hs2:
                    intent=new Intent(SelectActivity.this,Hs2Activity.class);
                    break;
            }
            RadioButton radioButton=findViewById(view.getId());
            intent.putExtra("title", radioButton.getText().toString());
            intent.putExtra("condition",binding.etHs.getText().toString());
            startActivity(intent);
        }
    };
    View.OnClickListener onClickListenerLincon=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton radioButton=findViewById(view.getId());

            Intent intent=null;

            switch (view.getId()){
                case R.id.rb_lincon1:
                    intent=new Intent(SelectActivity.this,LinconComponentsActivity.class);
                    intent.putExtra("title", radioButton.getText().toString());
                    break;
                case R.id.rb_lincon2:
                    intent=new Intent(SelectActivity.this,LinconActivity.class);
                    intent.putExtra("title", radioButton.getText().toString());
                    break;

            }
            startActivity(intent);
        }
    };
    View.OnClickListener onClickListenerWkf=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent=new Intent(SelectActivity.this,MainActivity.class);
            switch (view.getId()){
                case R.id.rb_wkf1:
                    intent.putExtra("type",1);
                    break;
                case R.id.rb_wkf2:
                    intent.putExtra("type",2);
                    break;
            }
            startActivity(intent);


        }
    };
    View.OnClickListener onClickListenerAr=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=null;
            if(view.getId()==binding.rbAr0.getId()){
                intent=new Intent(SelectActivity.this,ArChartActivity.class);

            }else {
                intent=new Intent(SelectActivity.this,ArActivity.class);
            }
            RadioButton radioButton=findViewById(view.getId());
            intent.putExtra("title", radioButton.getText().toString());
            startActivity(intent);
        }
    };




}
