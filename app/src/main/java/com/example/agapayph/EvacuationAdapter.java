package com.example.agapayph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EvacuationAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<ListEvacuationCenter> data;

    public EvacuationAdapter(Context context, List<ListEvacuationCenter> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView tvEvacName;
        TextView tvCapacity;
        TextView tvOccupancy;
        TextView tvAddress;
        TextView tvFood;
        TextView tvWater;
        TextView tvMedicine;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;
        if (view == null) {

            view = inflater.inflate(R.layout.listevacuation, viewGroup, false);
            vh = new ViewHolder();
            vh.tvEvacName = view.findViewById(R.id.tvEvacName);
            vh.tvCapacity = view.findViewById(R.id.tvCapacity);
            vh.tvAddress = view.findViewById(R.id.tvEvacAddress);
            vh.tvOccupancy = view.findViewById(R.id.tvOccupancy);
            vh.tvFood = view.findViewById(R.id.tvFood);
            vh.tvWater = view.findViewById(R.id.tvWater);
            vh.tvMedicine = view.findViewById(R.id.tvMedicine);

            view.setTag(vh);

        } else {

            vh = (ViewHolder) view.getTag();

        }

        ListEvacuationCenter item = data.get(i);
        vh.tvEvacName.setText(item.evaucation_name);
        vh.tvCapacity.setText(item.capacity+"");
        vh.tvOccupancy.setText(item.current_occupancy+"");
        vh.tvAddress.setText(item.address);
        vh.tvFood.setText("Food: " + item.food_packs);
        vh.tvWater.setText("Water: " + item.water + "L");
        vh.tvMedicine.setText("Meds: " + item.medicine_kit);

        return view;
    }
}
