package com.example.varsayy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class action_girsyap extends AppCompatActivity {
    Context context=this;
    Button btngiris;
    RequestQueue mQueue=null;
    Kullanici kullanici;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girisyap);
        mQueue = Volley.newRequestQueue(context);
        btngiris=(Button)findViewById(R.id.btngiris);
        btngiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 kullanici=new Kullanici();
                 kullanici.setEposta(((EditText)findViewById(R.id.eposta)).getText().toString());
                 kullanici.setParola(((EditText)findViewById(R.id.parola)).getText().toString());
                 parse();

            }
        });
    }
    private void parse(){
        String Url="http://soaproje.herokuapp.com/girisyap";
        StringRequest request=new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obje=new JSONObject(response);
                            Kullanici.setToken(obje.getString("token"));
                            Intent intent=new Intent(context,MainActivity.class);
                            startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(context,"Eposta veya Parola Yanlış",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Eposta veya Parola Yanlış",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("Eposta",kullanici.getEposta());
                params.put("Parola",kullanici.getParola());
                return params;
            }
        };
        mQueue.add(request);
    }
}
