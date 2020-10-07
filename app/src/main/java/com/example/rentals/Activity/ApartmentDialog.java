package com.example.rentals.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ApartmentDialog extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_dialog);



    }
    public void showDialog(final Activity activity, String ApartmentId){
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

        getApartmentData(dialog,ApartmentId);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent i =new Intent(activity,ApartmentDetails.class) ;
            activity.startActivity(i);
            finish();}
        });
        dialog.show();




    }

    private void getApartmentData(final Dialog dialog, String apartmentId) {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Apartment").document(apartmentId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       // Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        TextView title = (TextView) dialog.findViewById(R.id.title);
                        TextView type = (TextView) dialog.findViewById(R.id.type);
                        TextView bedroom = (TextView)dialog.findViewById(R.id.bedroom);
                        TextView price =(TextView)dialog.findViewById(R.id.price);
                        title.setText(""+document.getData().get("Title"));
                        type.setText("Type:- "+document.getData().get("Unit")+", ");
                        bedroom.setText("Bedroom:- "+document.getData().get("Bedroom"));
                        price.setText("Price "+document.getData().get("Amount")+"$");

                        getImage(dialog,document.getId());



                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });


    }

    private void getImage(final Dialog dialog, String id) {
        id="NoF5tUfg1f0HM4vZM2RJ";
        storageReference = storage.getInstance().getReference();
        storageReference.child("images/"+id+"/0").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                ImageView image = (ImageView) dialog.findViewById(R.id.dialogimage);
                Picasso.get().load(uri).resize(120, 120).into(image);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}