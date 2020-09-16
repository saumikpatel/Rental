package com.example.rentals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;

public class ApartmentDetails extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewPager);

        getTabs();
    }

    public void getTabs(){
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {


                viewPagerAdapter.addFragment(overviewFragment.getInstance(),"OVERVIEW");
                viewPagerAdapter.addFragment(theunitFragment.getInstance(),"THEUNIT");
                viewPagerAdapter.addFragment(thebuildingFragment.getInstance(),"THEBUILDING");
                viewPagerAdapter.addFragment(accessibilityfragment.getInstance(),"ACCESSIBILITY");

                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}