package com.swipe.shrinkcom.swipe.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.activity.HomeActivity;
import com.swipe.shrinkcom.swipe.activity.MessageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialoge_history, container, false);
        view.findViewById(R.id.linearThumb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "You just send a Thumbs-Up!", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.linearSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MessageActivity.class));
            }
        });
        view.findViewById(R.id.imageQur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogeExampkle();
            }
        });
        view.findViewById(R.id.imagerepost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogeReostExampkle();
            }
        });

        return view;
        //linearSend
    }

    private void alertDialogeReostExampkle() {

        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialoge_report);
        dialog.setTitle("Title...");

        TextView dialogReport = dialog.findViewById(R.id.dialogReport);
        TextView dialogCancel = dialog.findViewById(R.id.dialogCancel);
        // if button is clicked, close the custom dialog
        dialogCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogReport.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void alertDialogeExampkle() {

        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialoge_delete);
        dialog.setTitle("Title...");

        TextView dialogReport = dialog.findViewById(R.id.dialogOK);
        TextView dialogCancel = dialog.findViewById(R.id.dialogCancell);
        // if button is clicked, close the custom dialog
        dialogCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogReport.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
