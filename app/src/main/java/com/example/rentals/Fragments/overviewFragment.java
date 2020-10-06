package com.example.rentals.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import com.example.rentals.R;

public class overviewFragment extends Fragment {

    TextView apartment,sizebedroom,bathrm,hydro,heat,water,cabletv,internet,parking,agreementtype;
    ImageView hydrotick,heattick,watertick,cabletick,internettick;
    FirebaseFirestore fstore;

    public static overviewFragment getInstance(){
        overviewFragment OverviewFragment = new overviewFragment();
        return OverviewFragment;
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_overview,container,false);

        apartment=view.findViewById(R.id.overviewFragmentApartment);
        sizebedroom=view.findViewById(R.id.sizebedroom);
        bathrm=view.findViewById(R.id.bathrm);
        hydro=view.findViewById(R.id.hydro);
        hydrotick=view.findViewById(R.id.hydrotick);
        heattick=view.findViewById(R.id.heattick);
        heat= view.findViewById(R.id.heat);
        water=view.findViewById(R.id.water);
        watertick=view.findViewById(R.id.watertick);
        cabletv=view.findViewById(R.id.cabletv);
        cabletick=view.findViewById(R.id.cabletick);
        internet=view.findViewById(R.id.internet);
        internettick=view.findViewById(R.id.internettick);
        parking=view.findViewById(R.id.parking);


        getdata();

        return view;

    }

    private void getdata() {
        fstore = FirebaseFirestore.getInstance();
        DocumentReference docRef = fstore.collection("Apartment").document("s4f7fpuSstdKi0nqleoF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Map<String, Object> data1 = document.getData();

                        String ofapt= data1.get("Unit").toString();
                        String szbr= data1.get("Bedroom").toString();
                        String btrm= data1.get("Bathroom").toString();
                        String hdro= data1.get("Hydro").toString();
                        String ht= data1.get("Heat").toString();
                        String wt=data1.get("Water").toString();
                        String ct=data1.get("Tv").toString();
                        String itnt=data1.get("Internet").toString();
                        String pk=data1.get("ParkingIncluded").toString();

                        apartment.setText(ofapt);
                        sizebedroom.setText(szbr);
                        bathrm.setText(btrm);
                        parking.setText(pk);
                        if (hdro.equals("Yes")){
                            hydrotick.setImageResource(R.drawable.rightmark);
                        }
                        else{
                            hydrotick.setImageResource(R.drawable.wrongmark);
                        }
                        if (ht.equals("Yes")){
                            heattick.setImageResource(R.drawable.rightmark);
                        }
                        else {
                            heattick.setImageResource(R.drawable.wrongmark);
                        }
                        if (wt.equals("Yes")){
                            watertick.setImageResource(R.drawable.rightmark);
                        }
                        else {
                            watertick.setImageResource(R.drawable.wrongmark);
                        }
                        if (ct.equals("Yes")){
                            cabletick.setImageResource(R.drawable.rightmark);
                        }
                        else {
                            cabletick.setImageResource(R.drawable.wrongmark);
                        }
                        if (itnt.equals("Yes")){
                            internettick.setImageResource(R.drawable.rightmark);
                        }
                        else {
                            internettick.setImageResource(R.drawable.wrongmark);
                        }

                        // String data2 = data1.toString().trim();
                        //String aptname = data2.substring(data2.indexOf("Title") + 6, data2.indexOf(", Braille_Labels="));






                        Log.d("tagvv", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("tagvv", "No such document");
                    }
                } else {
                    Log.d("tagvv", "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



}
