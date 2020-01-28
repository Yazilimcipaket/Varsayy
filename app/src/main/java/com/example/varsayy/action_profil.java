package com.example.varsayy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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

import org.json.JSONObject;

public class action_profil extends AppCompatActivity{
    Context context=this;
    RequestQueue mQueue =null;
    Kullanici kullanici=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_layout);
        kullanici=new Kullanici();
        mQueue = Volley.newRequestQueue(context);
        parse();
    }
    private void parse(){
        String Url="http://soaproje.herokuapp.com/api/users/usergetir?token="+Kullanici.getToken();
        StringRequest request=new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obje=new JSONObject(response);
                            Log.i("Kullanici",obje.toString());
                            ((TextView)findViewById(R.id.textisim)).setText(obje.getString("Adi"));
                            ((TextView)findViewById(R.id.textsyisim)).setText(obje.getString("Soyadi"));
                            ((TextView)findViewById(R.id.textevadres)).setText(obje.getString("Adres"));
                            ((TextView)findViewById(R.id.texttel)).setText(obje.getString("Telefon"));
                            ((TextView)findViewById(R.id.texteposta)).setText(obje.getString("Eposta"));
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profil_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_kartlarim:
                Intent intent=new Intent(context,action_kartlarim.class);
                startActivity(intent);break;
            case R.id.action_siparisler:
                Intent intent2=new Intent(context,action_siparis.class);
                startActivity(intent2);break;
            case R.id.action_profilduzenle:
               Toast.makeText(context,"Daha Hazır değil",Toast.LENGTH_SHORT);
        }
        return super.onOptionsItemSelected(item);
    }
}
