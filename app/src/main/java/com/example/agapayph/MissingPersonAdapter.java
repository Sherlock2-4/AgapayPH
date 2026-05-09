package com.example.agapayph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.logging.Handler;

public class MissingPersonAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<ListMissingPerson> data;

    public MissingPersonAdapter(Context context, List<ListMissingPerson> data) {
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
        TextView tvName;
        TextView tvAge;
        TextView tvBarangay;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;
        if (view == null) {

            view = inflater.inflate(R.layout.missingvolunteer, viewGroup, false);
            vh = new ViewHolder();
            vh.tvAge = view.findViewById(R.id.tvAge);
            vh.tvName = view.findViewById(R.id.tvName);
            vh.tvBarangay = view.findViewById(R.id.tvBarangay);

            view.setTag(vh);

        } else {

            vh = (ViewHolder) view.getTag();

        }

        ListMissingPerson item = data.get(i);
        vh.tvBarangay.setText("Last location: " + item.last_location);
        vh.tvAge.setText(item.age+"");
        vh.tvName.setText(item.missing_person_name);

        return view;
    }
}
