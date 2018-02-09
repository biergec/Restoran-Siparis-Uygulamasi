package com.example.mustafa.restorantsiparisv2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public FirebaseAuth auth = FirebaseAuth.getInstance();

    //geri tuşuna basılma durumunu yakalıyoruz
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this,AlertDialog.THEME_HOLO_DARK);
            alert.setTitle("Çıkmak İstediğinizden Eminmisiniz ?");
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            alert.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportParentActivityIntent();

        Button cikis=findViewById(R.id.button3_cikis);
        Button siparisAlNew=findViewById(R.id.button_AnaMenu_SiparisOlustur);
        TextView kullanici_mesaj=findViewById(R.id.textView_KullaniciMesajAnaEkran);

        // Değişkenlerde yer alan verileri sufırlıyoruz
        if (!Urunler.Siparis_Masa_No.equals("0") && Urunler.SiparisUrunListDBKayit.size()!=0 && Urunler.childUpdates.size()!=0){
            Urunler.Siparis_Masa_No="";
            Urunler.SiparisUrunListDBKayit.clear();
            Urunler.childUpdates.clear();
        }

        kullanici_mesaj.setText(auth.getCurrentUser().getEmail() + " Hoşgeldiniz");

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });

        siparisAlNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,YemekSiparisV2.class);
                startActivity(intent);
            }
        });


    }

}
