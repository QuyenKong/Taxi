package com.example.taxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_01 extends ArrayAdapter<Taxi_01> {
    private Context context;
    private List<Taxi_01> arrTaxi;
    public Adapter_01(@NonNull Context context, int resource, @NonNull List<Taxi_01> objects) {
        super(context, resource, objects);
        this.context=context;
        this.arrTaxi=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            // convertView = LayoutInflater.from(context).inflate(R.layout.collab, parent, false);
            convertView = LayoutInflater.from(context).inflate(R.layout.taxi_item, parent, false);

            viewHolder.txt_soxe = convertView.findViewById(R.id.txt_soxe);
            viewHolder.txt_quangduong = convertView.findViewById(R.id.txt_quangduong);
            viewHolder.txt_tongtien = convertView.findViewById(R.id.txt_tongtien);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Taxi_01 taxi= arrTaxi.get(position);
        float tongtien= taxi.getQuangDuong()+taxi.getDonGia()*(100-taxi.getKhuyenMai())/100;

        viewHolder.txt_soxe.setText(taxi.soXe);
        viewHolder.txt_quangduong.setText(""+taxi.quangDuong);
        viewHolder.txt_tongtien.setText(""+tongtien);

        return convertView;
    }

    class ViewHolder {
        TextView txt_soxe;
        TextView txt_quangduong;
        TextView txt_tongtien;
    }

}
