package com.example.varsayy;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;


public class Kullanici {
    private  String Adi;

    public String getAdi() {
        return Adi;
    }

    public void setAdi(String adi) {
        this.Adi = adi;
    }
    private String Soyadi;

    public String getSoyadi() {
        return Soyadi;
    }

    public void setSoyadi(String soyadi) {
        Soyadi = soyadi;
    }
    private String Adresi;

    public String getAdresi() {
        return Adresi;
    }

    public void setAdresi(String adresi) {
        Adresi = adresi;
    }
    private String Telefon;

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    private String Eposta;

    public String getEposta() {
        return Eposta;
    }

    public void setEposta(String eposta) {
        Eposta = eposta;
    }
    private String Parola;

    public String getParola() {
        return Parola;
    }

    public void setParola(String parola) {
        Parola = parola;
    }

    public static String getToken() {
        return Token;
    }

    public static void setToken(String token) {
        Token = token;
    }

    private static String Token;



    public Boolean KullaniciEkle(Kullanici kullanici){
        try {
            URL url = new URL("http://soaproje.herokuapp.com/register"); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("Adi", kullanici.getAdi());
            jsonParam.put("Soyadi", kullanici.getSoyadi());
            jsonParam.put("Adres", kullanici.getAdresi());
            jsonParam.put("Telefon",kullanici.getTelefon());
            jsonParam.put("Eposta", kullanici.getEposta());
            jsonParam.put("Parola", kullanici.getParola());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());
            conn.disconnect();
            return true;
        } catch (Exception e) {
            Log.i("Hata" , e.toString());
            return false;
        }
    }
    public void KullaniciDuzenle(Kullanici kullanici){

    }
    public boolean KullaniciGiris(Kullanici kullanici){
        try {
            URL url = new URL("http://soaproje.herokuapp.com/girisyap");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("Eposta", kullanici.getEposta());
            jsonParam.put("Parola", kullanici.getParola());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();


            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
        } catch (Exception e) {
            Log.i("Hata" , e.toString());
            return false;
        }
        return true;
    }


}
