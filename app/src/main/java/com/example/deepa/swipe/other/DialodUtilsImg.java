package com.swipe.shrinkcom.other;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;


public class DialodUtilsImg {
    public static final int REQUEST_CAMERA = 0;
    public static final int SELECT_FILE = 1;
    private Context context;

    public static void openDialogpickimg(final Activity context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Select your image");
        builder.setTitle("Gallery");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                context.startActivityForResult(intent, REQUEST_CAMERA);

            }

        });
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                context.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

            }
        });
        builder.create().show();
    }
}
