package com.example.varsayy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class action_siparis extends AppCompatActivity {
    Context context=this;
    RequestQueue mQueue =null;
    ListView siparislistview;
    List<String> liste;
    List<Siparis>siparisler=new ArrayList<Siparis>();
    List<Urun> urunler;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siparis_layout);
        mQueue = Volley.newRequestQueue(context);
        siparislistview=(ListView)findViewById(R.id.listemiz);

        parse();
        siparislistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            ArrayList<String> detay=new ArrayList<String>();
            String listvieweyaz="";
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int sayac=0;
                for (Siparis sipais:siparisler) {
                    if(position==sayac){
                        for (Urun urun:sipais.getSepet()){
                        listvieweyaz+="Urun Marka :"+urun.getUrunMarka()+"\n";
                        listvieweyaz+="Urun Model :"+urun.getUrunAdi()+"\n";
                        listvieweyaz+="Urun Fiyat :"+urun.getUrunFiyat()+"\n";
                        detay.add(listvieweyaz);
                        listvieweyaz="";
                        }
                    }
                    sayac++;
                }
                Intent intent=new Intent(context,action_siparisdetay.class);
                intent.putStringArrayListExtra("list",detay);
                startActivity(intent);
                detay=new ArrayList<String>();
            }
        });
    }
    private void parse(){
        String Url="http://soaproje.herokuapp.com/api/siparisler/siparislerim?token="+Kullanici.getToken();
        StringRequest request=new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Siparis siparis=null;
                            liste=new ArrayList<String>();
                            JSONArray Jsiparis=new JSONArray(response);
                            for(int i=0;i<Jsiparis.length();i++){
                                urunler=new ArrayList<Urun>();
                                siparis=new Siparis();
                                String Jsepet=Jsiparis.getJSONObject(i).getString("sepet");
                                JSONArray Jurun=new JSONArray(Jsepet);
                                JSONArray Jurunler=new JSONArray(Jurun.getString(0));
                                String listeyapistir="";
                                listeyapistir+=Jurunler.getJSONObject(0).getString("UrunSiteDomain")+"\n";
                                String []tarihler=null;
                                Date date=null;
                                try{
                                    tarihler=Jsiparis.getJSONObject(i).getString("SiparisTarihi").split("T");
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    date = formatter.parse(tarihler[0]);
                                }catch (Exception e){
                                    Toast.makeText(context,"Date Convert Hatası",Toast.LENGTH_LONG).show();
                                }
                                listeyapistir+=tarihler[0]+"\n";
                                int ToplamSatisTutar=0;
                                Urun urun=null;
                                for(int k=0;k<Jurunler.length();k++){
                                    urun=new Urun();
                                    urun.setUrunAdi(Jurunler.getJSONObject(k).getString("UrunAdi"));
                                    urun.setUrunMarka(Jurunler.getJSONObject(k).getString("UrunMarka"));
                                    urun.setUrunSiteDomain(Jurunler.getJSONObject(k).getString("UrunSiteDomain"));
                                    urun.setUrunFiyat(Integer.valueOf(Jurunler.getJSONObject(k).getString("UrunFiyat")));
                                    ToplamSatisTutar+=urun.getUrunFiyat();
                                    urunler.add(urun);
                                }

                                listeyapistir+=String.valueOf(ToplamSatisTutar)+" tl";
                                liste.add(listeyapistir);
                                siparis.setSiarisTarihi(date);
                                siparis.setsepet(urunler);
                                siparisler.add(siparis);

                            }
                            adapter=new ArrayAdapter<String>(context,R.layout.list_layout,liste);
                            siparislistview.setAdapter(adapter);
                        }
                        catch (Exception e){
                            Toast.makeText(context,"Parse Hatası",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"İstek hatası",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
}