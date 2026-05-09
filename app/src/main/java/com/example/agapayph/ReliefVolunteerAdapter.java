package com.example.agapayph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReliefVolunteerAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<ListReliefRecord> data;

    public ReliefVolunteerAdapter(Context context, List<ListReliefRecord> data) {
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
        TextView tvVolunteer;
        TextView tvRelief;
        TextView tvQuantity;
        TextView tvBarangay;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;
        if (view == null) {

            view = inflater.inflate(R.layout.reliefassignment, viewGroup, false);
            vh = new ViewHolder();
            vh.tvBarangay = view.findViewById(R.id.tvBarangay);
            vh.tvRelief = view.findViewById(R.id.tvRelief);
            vh.tvQuantity = view.findViewById(R.id.tvQuantity);
            vh.tvVolunteer = view.findViewById(R.id.tvVolunteer);

            view.setTag(vh);

        } else {

            vh = (ViewHolder) view.getTag();

        }

        ListReliefRecord item = data.get(i);
        vh.tvBarangay.setText(item.barangay+"");
        vh.tvRelief.setText(item.relief_type+"");
        vh.tvQuantity.setText(item.quantity+"");
        vh.tvVolunteer.setText(DataHolder.realName);



        return view;

    }

}
