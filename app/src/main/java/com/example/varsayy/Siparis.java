package com.example.varsayy;
import java.util.Date;
import java.util.List;

public class Siparis {
    private List<Urun> sepet;

    public List<Urun> getSepet() {
        return sepet;
    }

    public void setsepet(List<Urun> urunler) {
        this.sepet = urunler;
    }
    private Date SiparisTarihi;

    public Date getSiarisTarihi() {
        return SiparisTarihi;
    }

    public void setSiarisTarihi(Date siarisTarihi) {
        SiparisTarihi = siarisTarihi;
    }
    public void SiparisEkle(Urun urun,Siparis siparis){

    }
    public void SiparisSil(Siparis siparis){

    }
}
