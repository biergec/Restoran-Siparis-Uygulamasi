package com.example.mustafa.restorantsiparisv2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public  class YemekSiparisV2 extends AppCompatActivity {

    // Action Bar tanımlamada kullanıldı
    private EditText editTextActionBar;
    private ProgressBar progressBar;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("YemekList");

    final String[] Yemekler = new String[1];
    final String[] Icecekler = new String[1];
    final String[] Tatlilar = new String[1];

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onStart() {
        super.onStart();

        try {
            // İlk olarak daha önce yazılmış veri var mı bakıyoruz. Varsa değişkenlere aktarılıyor değerler.
            VeriOkumaSharedFarkliVeriKontrol();

            // Veri tabanından veri okuma işlemi firebase
            DBVeriOkuma();
        }
        catch (Exception e){
            Log.d("Veri Okunamadı",e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemek_siparis_v2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setIndeterminate(true);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        editTextActionBar=findViewById(R.id.editText2_masano_action_bar);
        editTextActionBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String x = s.toString();
                if(x.startsWith("0"))
                {
                    editTextActionBar.setText("");
                    Toast.makeText(getApplicationContext(),"Masa Numarası 0 İle Başlayamaz!",Toast.LENGTH_LONG).show();
                }else {
                    Urunler.Siparis_Masa_No=editTextActionBar.getText().toString();
                    Log.d("A_Degisen Sayimiz",Urunler.Siparis_Masa_No);
                }

            }
        });

    }


    public void DBVeriOkuma() {
        // veri tabanında veri değişimi olduğunda bu metot değişikliği algılıyor.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Yemekler[0] =dataSnapshot.child("Yemekler").getValue().toString();
                Icecekler[0] =dataSnapshot.child("Icecekler").getValue().toString();
                Tatlilar[0] =dataSnapshot.child("Tatlilar").getValue().toString();

                if (Yemekler.length<0){
                    progressBar.setVisibility(View.VISIBLE);
                    DBVeriOkuma();
                }else
                    progressBar.setVisibility(View.GONE);


                Log.d("asdasd", String.valueOf(Yemekler.length));
                Urunler.A_yemekler= Yemekler;
                Urunler.A_icecekler= Icecekler;
                Urunler.A_tatlilar= Tatlilar;

                String icecek_kayit_kontrol;
                icecek_kayit_kontrol = preferences.getString("Icecekler", "Null");
                // Veri varsa ve yeni okunduysa eskisini sil
                if (!icecek_kayit_kontrol.equals("Null")){
                    VeriSil();
                }
                VeriKayitTekrar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("İptal Edildi ",databaseError.getMessage());
            }
        });
    }

    public void VeriKayitTekrar(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

            editor.putString("Yemekler",Yemekler[0]);
            editor.putString("Icecekler", Icecekler[0]);
            editor.putString("Tatlilar", Tatlilar[0]);

            editor.commit();
    }

    public void VeriSil(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        try {
                editor.remove("Yemekler");
                editor.remove("Icecekler");
                editor.remove("Tatlilar");
            editor.commit();
        }
        catch (Exception e){
            Log.d("Hata",e.getMessage());
        }
    }

    // Daha önce yazılmış veri varsa değişkenlere aktarılıyor.
    private void VeriOkumaSharedFarkliVeriKontrol() {

        String yemek,icecek,tatli;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        try {
            yemek = preferences.getString("Yemekler","Null");
            icecek = preferences.getString("Icecekler", "Null");
            tatli = preferences.getString("Tatlilar", "Null");

            if (yemek.equals("Null") || icecek.equals("Null") || tatli.equals("Null") )
                return;

            Urunler.A_icecekler[0]=icecek;
            Urunler.A_yemekler[0]=yemek;
            Urunler.A_tatlilar[0]=tatli;
        }
        catch (Exception e){
            Log.d("Hata",e.getMessage());
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Siparis_yemek();

                case 1:
                    return new Siparis_icecek();

                case 2:
                    return new Siparis_tatli();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
