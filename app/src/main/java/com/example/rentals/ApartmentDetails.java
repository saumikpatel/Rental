package com.example.rentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ApartmentDetails extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView water;
    TextView apartmentName,price,address,description,overviewFragmentApartment;
    Button btnbuyApartment;

    ViewPager imageViewPager;

    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        water = (TextView) findViewById(R.id.overviewWater);
        apartmentName = (TextView) findViewById(R.id.apartmentName);
        price = (TextView) findViewById(R.id.price);
        address = (TextView) findViewById(R.id.address);
        description = (TextView) findViewById(R.id.description);
        btnbuyApartment = (Button) findViewById(R.id.buyapartment);

        imageViewPager = findViewById(R.id.imageslider);

        ViewImagePagerAdapter viewImagePagerAdapter = new ViewImagePagerAdapter(this);
        imageViewPager.setAdapter(viewImagePagerAdapter);

        getTabs();
        getdata();

        btnbuyApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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

                        String aptname= data1.get("Title").toString();
                        String pr=data1.get("Amount").toString();
                        String des=data1.get("Description").toString();

                        // String data2 = data1.toString().trim();
                        //String aptname = data2.substring(data2.indexOf("Title") + 6, data2.indexOf(", Braille_Labels="));
                        apartmentName.setText(aptname);
                        price.setText(pr);
                        description.setText(des);





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


    public void getTabs(){
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {


                viewPagerAdapter.addFragment(overviewFragment.getInstance(),"OVERVIEW");
                viewPagerAdapter.addFragment(theunitFragment.getInstance(),"THE UNIT");
                viewPagerAdapter.addFragment(thebuildingFragment.getInstance(),"THE BUILDING");
                viewPagerAdapter.addFragment(accessibilityfragment.getInstance(),"ACCESSIBILITY");

                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}