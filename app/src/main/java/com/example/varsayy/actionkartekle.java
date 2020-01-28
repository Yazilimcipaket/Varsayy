package com.example.varsayy;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class actionkartekle extends AppCompatActivity {
    Button btnekle;
    Context context=this;
    RequestQueue mQueue =null;
    Kullanici kullanici=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kartekle_layout);
        kullanici=new Kullanici();
        mQueue = Volley.newRequestQueue(context);
        btnekle=(Button)findViewById(R.id.btnkartekle);
        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parse();
            }
        });
    }
    private void parse(){
        String Url="http://soaproje.herokuapp.com/api/kart?token="+Kullanici.getToken();
        StringRequest request=new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,"Kart Ekleme Başarılı",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"İstek hatası",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("KartNumarasi",((TextView)findViewById(R.id.editkartno)).getText().toString());
                params.put("KartSonTarih",((TextView)findViewById(R.id.editkartsonkullanma)).getText().toString());
                params.put("KartCSV",((TextView)findViewById(R.id.editkartcsv)).getText().toString());
                return params;
            }
        };
        mQueue.add(request);
    }
}
