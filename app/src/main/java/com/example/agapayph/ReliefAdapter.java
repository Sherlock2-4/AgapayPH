package com.example.agapayph;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReliefAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<ListReliefRecord> data;

    public ReliefAdapter(Context context, List<ListReliefRecord> data) {
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
        TextView tvBeneficiary;
        TextView tvBarangay;
        TextView tvRelief;
        TextView tvQuantity;
        TextView tvDateTime;
        TextView tvVolunteer;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;
        if (view == null) {

            view = inflater.inflate(R.layout.editrelief, viewGroup, false);
            vh = new ViewHolder();
            vh.tvBeneficiary = view.findViewById(R.id.tvBeneficiary);
            vh.tvBarangay = view.findViewById(R.id.tvBarangay);
            vh.tvRelief = view.findViewById(R.id.tvRelief);
            vh.tvQuantity = view.findViewById(R.id.tvQuantity);
            vh.tvDateTime = view.findViewById(R.id.tvDateTime);
            vh.tvVolunteer = view.findViewById(R.id.tvVolunteer);

            view.setTag(vh);

        } else {

            vh = (ViewHolder) view.getTag();

        }

        ListReliefRecord item = data.get(i);
        vh.tvBeneficiary.setText(item.beneficiary_name+"");
        vh.tvBarangay.setText(item.barangay+"");
        vh.tvRelief.setText(item.relief_type+"");
        vh.tvQuantity.setText(item.quantity+"");
        vh.tvDateTime.setText(item.distribution_date+"");

        DatabaseHelper dh = new DatabaseHelper(view.getContext());
        List<ListVolunteerNames> names = dh.listVolunteerNames();
        for (ListVolunteerNames lvn: names) {
            if (lvn.volunteer_id == item.volunteer_id) {

                vh.tvVolunteer.setText(lvn.volunteer_username);
                break;
            }
        }


        return view;
    }

}
