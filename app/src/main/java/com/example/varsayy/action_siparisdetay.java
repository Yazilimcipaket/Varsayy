package com.example.varsayy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;

public class action_siparisdetay extends AppCompatActivity {
    ListView siparisdetaylistview;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siparis_detay_layout);
        siparisdetaylistview=(ListView)findViewById(R.id.listemiz2);
        Context context=this;
        Intent intent=getIntent();
        adapter=new ArrayAdapter<String>(context,R.layout.list_layout,intent.getStringArrayListExtra("list"));
        siparisdetaylistview.setAdapter(adapter);
    }
}
