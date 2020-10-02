package com.example.rentals.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rentals.R;

public class ApartmentDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_dialog);

    }
    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_apartment_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.height= WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.width= WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.y=150;
        window.setAttributes(wlp);
        ImageView next = (ImageView) dialog.findViewById(R.id.btn_dialog);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(activity,ApartmentDetails.class) ;
            activity.startActivity(i);
            finish();}
        });
        dialog.show();



    }

}