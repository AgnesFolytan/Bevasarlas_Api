package com.example.bevasarlas_oraimunka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<Termekek> termekekList;
    private Context context;

    public ListAdapter(List<Termekek> termekekList, Context context) {
        this.termekekList = termekekList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return termekekList.size();
    }

    @Override
    public Object getItem(int position) {
        return termekekList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        Termekek termek = termekekList.get(position);

        TextView nevTextView = convertView.findViewById(R.id.nevTextView);
        TextView egysegArTextView = convertView.findViewById(R.id.egysegArTextView);
        TextView mennyisegTextView = convertView.findViewById(R.id.mennyisegTextView);
        TextView mertekegysegTextView = convertView.findViewById(R.id.mertekegysegTextView);
        TextView bruttoArTextView = convertView.findViewById(R.id.bruttoArTextView);

        nevTextView.setText(termek.getNev());
        egysegArTextView.setText(String.valueOf(termek.getEgysegar()));
        mennyisegTextView.setText(String.valueOf(termek.getMennyiseg()));
        mertekegysegTextView.setText(termek.getMertekegyseg());
        bruttoArTextView.setText(String.valueOf(termek.getBrutto_ar()));

        return convertView;
    }
}
