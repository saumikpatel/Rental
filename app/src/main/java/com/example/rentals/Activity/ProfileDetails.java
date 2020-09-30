package com.example.rentals.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.rentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileDetails extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser curUser;
    ProgressDialog pd;
    FirebaseFirestore db;
    TextInputLayout name,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        name=findViewById(R.id.upadtename);
        email=findViewById(R.id.updateemail);
        phone=findViewById(R.id.updatephone);
        getUserData();

    }

    private void getUserData() {
        pd = new ProgressDialog(ProfileDetails.this);
        pd.setMessage("Loading...");
        pd.show();
        curUser = auth.getCurrentUser();
        String id= curUser.getUid();
        DocumentReference docRef = db.collection("User").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        name.getEditText().setText(""+document.getData().get("Name"));
                        email.getEditText().setText(""+document.getData().get("Email"));
                        phone.getEditText().setText(""+document.getData().get("Phone"));
                        pd.dismiss();

                    } else {
                        Log.d("TAG", "No such document");
                        pd.dismiss();
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    pd.dismiss();
                }
            }
        });

    }
}