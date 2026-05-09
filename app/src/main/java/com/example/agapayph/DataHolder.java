package com.example.agapayph;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import java.util.List;

public class DataHolder {

    public static String username;
    public static String role;
    public static List<ListEvacuationCenter> evacList;
    public static boolean isEditEvacSession;
    public static int volunteerId;

    // DATA FOR FILLING EVACUATION INFO WHEN EDITING

    public static String evacName;
    public static int evacCapacity;
    public static int evacOccupancy;
    public static String evacAddress;
    public static String evacFood;
    public static String evacWater;
    public static String evacMedicine;

    /// ///////

    public static boolean buildMyDialog(String title, String message, Context context) {

        final boolean[] result = new boolean[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                result[0] = true;
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                result[0] = false;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return result[0];


    }


}
