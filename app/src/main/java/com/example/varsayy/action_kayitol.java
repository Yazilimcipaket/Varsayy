package com.example.varsayy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;


public class action_kayitol extends AppCompatActivity {
    Button btnkaydet;
    Kullanici kullanici=null;
    Context context=this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayitol_layout);
        kullanici=new Kullanici();
        btnkaydet=(Button)findViewById(R.id.btnkaydet);
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullanici.setAdi(((EditText)findViewById(R.id.editisim)).getText().toString());
                kullanici.setSoyadi(((EditText)findViewById(R.id.editsyisim)).getText().toString());
                kullanici.setTelefon((((EditText)findViewById(R.id.edittel)).getText().toString()));
                kullanici.setAdresi(((EditText)findViewById(R.id.editevadres)).getText().toString());
                kullanici.setEposta(((EditText)findViewById(R.id.eposta)).getText().toString());
                kullanici.setParola(((EditText)findViewById(R.id.parola)).getText().toString());
                if(kullanici.KullaniciEkle(kullanici)){
                    Intent intent=new Intent(context,action_girsyap.class);
                    startActivity(intent);
                }
                else
                Toast.makeText(context,"Kayıt Başarısız",Toast.LENGTH_LONG).show();
            }
        });
    }

}
