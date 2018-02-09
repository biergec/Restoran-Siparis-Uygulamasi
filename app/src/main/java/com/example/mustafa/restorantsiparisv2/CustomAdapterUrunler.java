package com.example.mustafa.restorantsiparisv2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterUrunler extends ArrayAdapter<Urunler> implements View.OnClickListener {

    public ArrayList<Urunler> listProducts;
    private Context mContext;

    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public Urunler getItem(int position) {
        return listProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView siparis_isim;
        EditText siparis_miktar;
        Button buton_arti,buton_eksi;
    }

    public CustomAdapterUrunler(Siparis_icecek siparis_icecek, ArrayList<Urunler> products2) {
        super(siparis_icecek.getContext(), R.layout.icerikler, products2 );
        this.listProducts = products2;
        this.mContext=siparis_icecek.getContext();
    }

    public CustomAdapterUrunler(Siparis_yemek context, ArrayList<Urunler> listProducts)  {
        super(context.getContext(), R.layout.icerikler, listProducts );
        this.listProducts = listProducts;
        this.mContext=context.getContext();
    }

    public CustomAdapterUrunler(Siparis_tatli siparis_tatli, ArrayList<Urunler> products) {
        super(siparis_tatli.getContext(), R.layout.icerikler, products );
        this.listProducts = products;
        this.mContext=siparis_tatli.getContext();
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();

        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        viewHolder.siparis_miktar=(EditText) v.findViewById(R.id.editText2_urun_miktari);

        Log.d("Miktar",viewHolder.siparis_miktar.getText().toString());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View row;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row=layoutInflater.inflate(R.layout.icerikler,parent,false);
            viewHolder.siparis_isim = row.findViewById(R.id.textview_Urun_Isim);
            viewHolder.siparis_miktar= row.findViewById(R.id.editText2_urun_miktari);
            viewHolder.buton_arti=row.findViewById(R.id.buton_arti_icerik);
            viewHolder.buton_eksi=row.findViewById(R.id.buton_eksi_icerik);

            row.setTag(viewHolder);
        }
        else {
            row=convertView;
            viewHolder = (ViewHolder) row.getTag();
        }

        final Urunler products = getItem(position);
        viewHolder.siparis_isim.setText(products.urun_name);
        viewHolder.siparis_miktar.setText(products.urun_siparis_miktar+"");

        viewHolder.buton_arti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEdittext(position,viewHolder.siparis_miktar,1);
            }
        });

        viewHolder.buton_eksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEdittext(position,viewHolder.siparis_miktar,-1);
            }
        });

        return row;
    }

    public void updateEdittext(int position, EditText edTextQuantity, int value){

        Urunler urunler = getItem(position);
        if(value > 0)
        {
            urunler.urun_siparis_miktar = urunler.urun_siparis_miktar + 1;
        }
        else
        {
            if(urunler.urun_siparis_miktar > 0)
            {
                urunler.urun_siparis_miktar = urunler.urun_siparis_miktar - 1;
            }

        }
        edTextQuantity.setText(urunler.urun_siparis_miktar+"");
    }

}
