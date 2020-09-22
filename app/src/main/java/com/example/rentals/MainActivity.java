package com.example.rentals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
    final Fragment wishlist = new WishlistFragment();
    final Fragment profile = new ProfileFragment();
    final Fragment map = new MapFragment();
    Fragment active = map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
//        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Toast.makeText(MainActivity.this, "bhaag", Toast.LENGTH_LONG).show();
//                        switch (item.getItemId()) {
//                            case R.id.navigation_map:
//                                Toast.makeText(MainActivity.this, "user not found", Toast.LENGTH_LONG).show();
//                                Fragment fragment = new MapFragment();
//
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.nav_host_fragment, fragment)
//                                        .commit();
//                                return true;
//                            case R.id.navigation_wishlist:
//                                Fragment fr = new WishlistFragment();
//// Insert the fragment by replacing any existing fragment
//
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.nav_host_fragment, fr)
//                                        .commit();
//                                return true;
//                            case R.id.navigation_profile:
//                                Fragment f = new ProfileFragment();
//// Insert the fragment by replacing any existing fragment
//
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.nav_host_fragment, f)
//                                        .commit();
//                                return true;
//                        }
//                        return false;
//                    }
//                };


        final FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame, profile, "3").hide(profile).commit();
        fm.beginTransaction().add(R.id.frame, wishlist, "2").hide(wishlist).commit();
        fm.beginTransaction().add(R.id.frame,map, "1").commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                if (item.getItemId() == R.id.navigation_wishlist) {
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frame, wishlist).commit();
                    fm.beginTransaction().hide(active).show(wishlist).commit();
                    active = wishlist;
                } else if (item.getItemId() == R.id.navigation_map) {
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frame, map).commit();
                    fm.beginTransaction().hide(active).show(map).commit();
                    active = map;
                } else if (item.getItemId() == R.id.navigation_profile) {
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frame, profile).commit();
                    fm.beginTransaction().hide(active).show(profile).commit();
                    active = profile;
                }
//                } else if (item.getItemId() == R.id.favoritosItem) {
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentContainer, favoritesFragment).commit();
//                } else if (item.getItemId() == R.id.perfilItem) {
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentContainer, profileFragment).commit();
//                }
                return true;
            }
        });
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_map); // change to whichever id should be default
        }


    }


}