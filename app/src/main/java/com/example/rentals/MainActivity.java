package com.example.rentals;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    final Fragment wishlist = new WishlistFragment();
    final Fragment profile = new ProfileFragment();
    final Fragment map = new MapFragment();
    BottomNavigationView bottomNavigationView;
    Fragment active = map;
    private FirebaseUser curUser;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        auth=FirebaseAuth.getInstance();

        final FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame, profile, "3").hide(profile).commit();
        fm.beginTransaction().add(R.id.frame, wishlist, "2").hide(wishlist).commit();
        fm.beginTransaction().add(R.id.frame, map, "1").commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.navigation_wishlist) {

                    fm.beginTransaction().hide(active).show(wishlist).commit();
                    active = wishlist;
                } else if (item.getItemId() == R.id.navigation_map) {

                    fm.beginTransaction().hide(active).show(map).commit();
                    active = map;
                } else if (item.getItemId() == R.id.navigation_profile) {
//                    curUser=auth.getCurrentUser();
//                       if(curUser!=null){
//                       }

                    fm.beginTransaction().hide(active).show(profile).commit();
                    active = profile;
                }

                return true;
            }
        });
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_map); // change to whichever id should be default
        }


    }


}