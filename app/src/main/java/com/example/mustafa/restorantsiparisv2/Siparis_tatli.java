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

import static com.example.mustafa.restorantsiparisv2.Urunler.SiparisUrunListDBKayit;
import static com.example.mustafa.restorantsiparisv2.Urunler.childUpdates;

public class Siparis_tatli extends android.support.v4.app.Fragment {

    private ListView listView_tatli;
    ArrayList<Urunler> products = new ArrayList<>();
    Button siparis_ver;
    public static CustomAdapterUrunler customAdapterUrunlerTatli;
    Siparis_tatli context=this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.siparis_tatli, container, false);

        Urunler.A_tatlilar=Urunler.A_tatlilar[0].split(",");

        if (products.isEmpty()){
            for (int i=0;i<Urunler.A_tatlilar.length;i++){
                products.add(new Urunler(Urunler.A_tatlilar[i]));
            }
        }

            listView_tatli = (ListView)rootView.findViewById(R.id.listview_tatli);
            customAdapterUrunlerTatli =new CustomAdapterUrunler(this,products);
            listView_tatli.setAdapter(customAdapterUrunlerTatli);

        siparis_ver=(Button)rootView.findViewById(R.id.button_SiparisVer_tatli);

        siparis_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Urunler.KayitGelenCevap=Urunler.UrunKayitKontrol();

                if (Urunler.KayitGelenCevap.equals("1"))
                    Toast.makeText(getContext(),"En Az Bir Sipariş Girilmelidir",Toast.LENGTH_LONG).show();

                if (Urunler.KayitGelenCevap.equals("2"))
                    Toast.makeText(getContext(),"Lütfen Masa Numarası Girin",Toast.LENGTH_LONG).show();

                if (Urunler.KayitGelenCevap.equals("3")){
                    AlertDialogSiparisTatliEkrani();
                }
            }
        });

        return rootView;
    }

    public void AlertDialogSiparisTatliEkrani(){
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

    public static void SiparisVerTatli(){
        try {
            for (int i=0;i<customAdapterUrunlerTatli.listProducts.size();i++){
                if (customAdapterUrunlerTatli.listProducts.get(i).urun_siparis_miktar>0){
                    Urunler urunler=new Urunler(customAdapterUrunlerTatli.listProducts.get(i).urun_name,customAdapterUrunlerTatli.listProducts.get(i).urun_siparis_miktar);

                    SiparisUrunListDBKayit.add(urunler);
                }
            }
            Log.d("A_Tatli MİKTAR",SiparisUrunListDBKayit.size()+"");
            Log.d("A_Tatli",SiparisUrunListDBKayit.size()+"");
        }catch (Exception e){
            return;
        }

    }

}
