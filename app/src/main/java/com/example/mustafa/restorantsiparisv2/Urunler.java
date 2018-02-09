package com.example.mustafa.restorantsiparisv2;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.mustafa.restorantsiparisv2.Siparis_icecek.SiparisVerIcecek;
import static com.example.mustafa.restorantsiparisv2.Siparis_tatli.SiparisVerTatli;
import static com.example.mustafa.restorantsiparisv2.Siparis_yemek.SiparisVerYemek;

public class Urunler {

    public String urun_name;
    public int urun_siparis_miktar=0;
    public static String Siparis_Masa_No="";
    public static String KayitGelenCevap = "";
    public static ArrayList<Urunler> SiparisUrunListDBKayit=new ArrayList<>();
    public static Map<String, Object> childUpdates;
    public static String[] A_tatlilar={"Baklava,Kunefe,Meyve Tabagi,Revani,Waffle"};
    public static String[] A_icecekler={"Ayran,Fanta,Kola,Meyve Suyu,Salgam,Su"};
    public static String[] A_yemekler={"Doner,Durum,Et Sis,Iskender,Kofte,Tantuni,Tavuk Sis"};

    public Urunler(String urun_name, int urun_siparis_miktar) {
        this.urun_name=urun_name;
        this.urun_siparis_miktar=urun_siparis_miktar;
    }

    public Urunler(String urun_name) {
        this.urun_name = urun_name;
    }

    public static void SiparisDatabaseKayit(){

        try {
            DatabaseReference databaseReference;
            String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference();


            childUpdates.put("User ID",userID);
            databaseReference.child("SiparisList").child(Siparis_Masa_No).updateChildren(childUpdates);

            Urunler.Siparis_Masa_No="";
            SiparisUrunListDBKayit.clear();

            Log.d("A_SiparisDatabaseKayit","Başarılı");
        }catch (Exception e){
            Log.d("A_SiparisDatabaseKayit","Hata : "+e.getMessage());
            return;
        }
    };

    public static String UrunKayitKontrol(){
        childUpdates = new HashMap<>();

        if (!Urunler.Siparis_Masa_No.equals("") && !Urunler.Siparis_Masa_No.equals("0")){
            SiparisVerYemek();
            SiparisVerIcecek();
            SiparisVerTatli();

            if (Urunler.SiparisUrunListDBKayit.size()>0){
                // SiparisUrunListDBKayit listesindeki elemanları HashMap ile dizin haline getiriyoruz ve Database kaydediyoruz.
                // Güncelleme için yine masa no ve sipariş girilmesi yeterli
                for(Urunler d : SiparisUrunListDBKayit ){
                    childUpdates.put(d.urun_name,d.urun_siparis_miktar);
                }
                return "3";
            }else {
                return "1";
            }
        }
            return "2";
    }

}
