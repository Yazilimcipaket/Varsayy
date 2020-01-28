package com.example.varsayy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class action_kartlarim extends AppCompatActivity {
    Context context=this;
    RequestQueue mQueue=null;
    KrediKart kredikart;
    ListView liste;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kartlarim_layout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        liste=(ListView)findViewById(R.id.listemiz);
        mQueue = Volley.newRequestQueue(context);
        parse();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kart_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_kredikartekle:
                Intent intent=new Intent(context,actionkartekle.class);
                startActivity(intent);break;
            case R.id.action_kredikartsil:
                Toast.makeText(context,"Kart Silme İşlemi",Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }
    private void parse(){
        String Url="http://soaproje.herokuapp.com/api/kart/kartlarim?token="+Kullanici.getToken();
        StringRequest request=new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray objeler=new JSONArray(response);
                            List<KrediKart> kartlar=new ArrayList<>();
                            List<String> kartno=new ArrayList<>();
                            for(int i=0;i<objeler.length();i++){
                                kredikart=new KrediKart();
                                String Kartbilgileri=new String();
                                kredikart.setKartNumarasi((objeler.getJSONObject(i).getString("KartNumarasi")));
                                kredikart.setKartSonK((objeler.getJSONObject(i).getString("KartSonTarih")));
                                kredikart.setKartCSV((objeler.getJSONObject(i).getString("KartCSV")));
                                Kartbilgileri+="Kart No: "+kredikart.getKartNumarasi()+"\n";
                                Kartbilgileri+="Kart Son T: "+kredikart.getKartSonK()+"\n";
                                Kartbilgileri+="Kart CSV: "+kredikart.getKartCSV();
                                kartlar.add(kredikart);
                                kartno.add(Kartbilgileri);
                            }
                            adapter=new ArrayAdapter<String>(context,R.layout.list_layout,kartno);
                            liste.setAdapter(adapter);
                        }
                        catch (Exception e){
                            Toast.makeText(context,"İstek Hatası",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Hata",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
}
