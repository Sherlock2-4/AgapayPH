package com.example.agapayph;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EvacuationManagementAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<ListEvacuationCenter> data;

    public EvacuationManagementAdapter(Context context, List<ListEvacuationCenter> data) {
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
        ImageView ivEdit;
        ImageView ivDelete;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;
        if (view == null) {

            view = inflater.inflate(R.layout.editevacuation, viewGroup, false);
            vh = new ViewHolder();
            vh.tvEvacName = view.findViewById(R.id.tvEvacName);
            vh.tvCapacity = view.findViewById(R.id.tvCapacity);
            vh.tvAddress = view.findViewById(R.id.tvEvacAddress);
            vh.tvOccupancy = view.findViewById(R.id.tvOccupancy);
            vh.tvFood = view.findViewById(R.id.tvFood);
            vh.tvWater = view.findViewById(R.id.tvWater);
            vh.tvMedicine = view.findViewById(R.id.tvMedicine);
            vh.ivEdit = view.findViewById(R.id.ivEdit);
            vh.ivDelete = view.findViewById(R.id.ivDelete);

            view.setTag(vh);

        } else {

            vh = (ViewHolder) view.getTag();

        }

        ListEvacuationCenter item = data.get(i);
        vh.tvEvacName.setText(item.evaucation_name);
        vh.tvCapacity.setText(item.capacity + "");
        vh.tvOccupancy.setText(item.current_occupancy + "");
        vh.tvAddress.setText(item.address);
        vh.tvFood.setText("Food: " + item.food_packs);
        vh.tvWater.setText("Water: " + item.water + "L");
        vh.tvMedicine.setText("Meds: " + item.medicine_kit);

        vh.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataHolder.isEditEvacSession = true;
                DataHolder.evacName = item.evaucation_name;
                DataHolder.evacCapacity = item.capacity;
                DataHolder.evacOccupancy = item.current_occupancy;
                DataHolder.evacAddress = item.address;
                DataHolder.evacWater = item.water+"";
                DataHolder.evacFood = item.food_packs+"";
                DataHolder.evacMedicine = item.medicine_kit+"";


                Intent i = new Intent(view.getContext(), AddEvaculation.class);
                view.getContext().startActivity(i);

            }
        });

        vh.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete Evacutaion Center?");
                builder.setMessage("This action cannot be undone");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        DatabaseHelper dh = new DatabaseHelper(view.getContext());

                        boolean result = dh.deleteEvacuationCenter(item.evaucation_name);
                        if (result) {
                            data.remove(i);
                            notifyDataSetChanged();


                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return view;
    }

}
