package com.example.mustafa.restorantsiparisv2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import static com.example.mustafa.restorantsiparisv2.Urunler.A_yemekler;
import static com.example.mustafa.restorantsiparisv2.Urunler.SiparisUrunListDBKayit;
import static com.example.mustafa.restorantsiparisv2.Urunler.childUpdates;

public class Siparis_icecek extends android.support.v4.app.Fragment{

    public ListView listView_icecek;
    public ArrayList<Urunler> products = new ArrayList<>();
    Button siparis_ver;
    public static CustomAdapterUrunler customAdapterUrunler;
    Siparis_icecek context=this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.siparis_icecek, container, false);

        Urunler.A_icecekler=Urunler.A_icecekler[0].split(",");

        if (products.isEmpty()){
            for (int i=0;i<Urunler.A_icecekler.length;i++){
                products.add(new Urunler(Urunler.A_icecekler[i]));
            }
        }

        listView_icecek = (ListView)rootView.findViewById(R.id.listview_icecekler);
        customAdapterUrunler =new CustomAdapterUrunler(this,products);
        listView_icecek.setAdapter(customAdapterUrunler);

        siparis_ver=(Button)rootView.findViewById(R.id.button_SiparisVer_Icecek);

        siparis_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Urunler.KayitGelenCevap=Urunler.UrunKayitKontrol();

                if (Urunler.KayitGelenCevap.equals("1"))
                    Toast.makeText(getContext(),"En Az Bir Sipariş Girilmelidir",Toast.LENGTH_LONG).show();

                if (Urunler.KayitGelenCevap.equals("2"))
                    Toast.makeText(getContext(),"Lütfen Masa Numarası Girin",Toast.LENGTH_LONG).show();

                if (Urunler.KayitGelenCevap.equals("3")){
                    AlertDialogSiparisIcecekEkrani();
                }
            }
        });

        return rootView;
    }

    public void AlertDialogSiparisIcecekEkrani(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        // Girilen siparişler değişkene yazılıyor ekranda gösterebilmek için.
        String x ="";
        for(Map.Entry<String, Object> pairs : childUpdates.entrySet()) {
            if (!pairs.getKey().equals("User ID")){
                x+=pairs+"\n";
            }
        }

        builder.setMessage("Girilen Siparişler \n"+x);
        builder.setTitle("Sipariş Onay - "+"Masa :"+Urunler.Siparis_Masa_No);

        builder.setCancelable(true);

        builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    Urunler.SiparisDatabaseKayit();
                    Toast.makeText(getContext(),"Sipariş Başarılı Bir Şekilde Alındı",Toast.LENGTH_LONG).show();
                    Thread.sleep(1000);
                    Intent intent=new Intent(context.getContext(),MainActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d("A_Kayit Hatasi",e.getMessage());
                }
            }
        });

        builder.setNegativeButton("Geri Dön", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    public static void SiparisVerIcecek(){
       try {
           for (int i=0;i<customAdapterUrunler.listProducts.size();i++){
               if (customAdapterUrunler.listProducts.get(i).urun_siparis_miktar>0){
                   Urunler urunler=new Urunler(customAdapterUrunler.listProducts.get(i).urun_name,customAdapterUrunler.listProducts.get(i).urun_siparis_miktar);

                   SiparisUrunListDBKayit.add(urunler);
               }
           }
           Log.d("A_Icecek MİKTAR",SiparisUrunListDBKayit.size()+"");
           Log.d("A_Icecek",SiparisUrunListDBKayit.size()+"");
       }catch (Exception e){
           return;
       }


    }


}
