package com.example.rentals.Activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.rentals.Adapters.ViewImagePagerAdapter;
import com.example.rentals.Adapters.ViewPagerAdapter;
import com.example.rentals.Fragments.accessibilityfragment;
import com.example.rentals.Fragments.overviewFragment;
import com.example.rentals.Fragments.thebuildingFragment;
import com.example.rentals.Fragments.theunitFragment;
import com.example.rentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class ApartmentDetails extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView water;
    TextView apartmentName, price, address, description, overviewFragmentApartment;
    Button btnbuyApartment;

    ViewPager imageViewPager;

    FirebaseFirestore fstore;

    ProgressDialog pd;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        apartmentName = (TextView) findViewById(R.id.apartmentName);
        price = (TextView) findViewById(R.id.price);
        address = (TextView) findViewById(R.id.address);
        description = (TextView) findViewById(R.id.description);
        btnbuyApartment = (Button) findViewById(R.id.buyapartment);

        imageViewPager = findViewById(R.id.imageslider);
        WebView browser = (WebView) findViewById(R.id.browser2);
        browser.getSettings().setJavaScriptEnabled(true); //Yes you have to do it
        browser.loadUrl("file:///android_asset/local.html");
        ArrayList<Uri> images = new ArrayList<Uri>();
        getImages(images);


        pd = new ProgressDialog(ApartmentDetails.this);
        pd.setMessage("Loading...");
        pd.show();

        getTabs();
        getdata();
        pd.dismiss();

        btnbuyApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void getImages(final ArrayList<Uri> images) {
        final String id = "NoF5tUfg1f0HM4vZM2RJ";

        StorageReference listRef = storage.getInstance().getReference().child("images/" + id);

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            storageReference = storage.getInstance().getReference();
                            String location = item.toString();
                            String image = location.substring(location.length() - 1);
                            System.out.println(image);
                            storageReference = storage.getInstance().getReference();
                            storageReference.child("images/" + id + "/" + image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    //  ImageView image = (ImageView) dialog.findViewById(R.id.dialogimage);
                                    // Picasso.get().load(uri).resize(120, 120).into(image);
                                    System.out.println(images.size() + "maa");
                                    images.add(uri);
                                    ViewImagePagerAdapter viewImagePagerAdapter = new ViewImagePagerAdapter(getApplicationContext(), images);
                                    viewImagePagerAdapter.notifyDataSetChanged();
                                    imageViewPager.setAdapter(viewImagePagerAdapter);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });

                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
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

                        String aptname = data1.get("Title").toString();
                        String pr = data1.get("Amount").toString();
                        String des = data1.get("Description").toString();

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


    public void getTabs() {
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {


                viewPagerAdapter.addFragment(overviewFragment.getInstance(), "OVERVIEW");
                viewPagerAdapter.addFragment(theunitFragment.getInstance(), "THE UNIT");
                viewPagerAdapter.addFragment(thebuildingFragment.getInstance(), "THE BUILDING");
                viewPagerAdapter.addFragment(accessibilityfragment.getInstance(), "ACCESSIBILITY");

                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}