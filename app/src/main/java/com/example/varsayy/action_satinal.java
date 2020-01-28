package com.example.varsayy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class action_satinal extends AppCompatActivity {
    ListView liste;
    String[] Urunler;
    String [] Detay;
    String []pretty;
    Button btnsatinal;
    Context context=this;
    RequestQueue mQueue =null;
    Urun urun;
    List<Urun> urunler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.satinallayout);
        Intent intent=getIntent();
        mQueue = Volley.newRequestQueue(context);
        final String Deger=intent.getStringExtra("sonuc");
        urunler=new ArrayList<>();
        Urunler=Deger.split("-->");
        pretty=new String[Urunler.length];
        for(int i=0;i<Urunler.length;i++){
            pretty[i]=Urunler[i].split("/&/")[1]+"-"+Urunler[i].split("/&/")[2];
            urun=new Urun();
            urun.setUrunAdi(Urunler[i].split("/&/")[2]);
            urun.setUrunMarka(Urunler[i].split("/&/")[1]);
            urun.setUrunFiyat(Integer.valueOf(Urunler[i].split("/&/")[3]));
            urun.setUrunSiteDomain(Urunler[i].split("/&/")[0]);
            urunler.add(urun);
        }
        final Context context=this;
        liste=(ListView)findViewById(R.id.list_profil);
        ArrayAdapter<String> adptor=new ArrayAdapter<String>(context,R.layout.list_layout,pretty);
        liste.setAdapter(adptor);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                Detay=Urunler[position].split("/&/");
                dialog.setTitle(Detay[0])
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("Marka:"+Detay[1]+"\n"+"Model: "+Detay[2]+"\n"+"Fiyat:"+Detay[3]).setCancelable(false)
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        btnsatinal=(Button)findViewById(R.id.btnsatinal);
        btnsatinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parse();
            }
        });
    }
    private void parse(){
        String Url="http://soaproje.herokuapp.com/api/siparisler?token="+Kullanici.getToken();
        StringRequest request=new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,"Sipariş Başarılı",Toast.LENGTH_LONG).show();
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
                String Jsonstring=new Gson().toJson(urunler);
                params.put("sepet",Jsonstring);
                return params;
            }
        };
        mQueue.add(request);
    }
}
