package com.example.rentals;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Postadd extends AppCompatActivity {


    private AutoCompleteTextView price, unit, bedroom, bathroom, pet , smoke , parking;

     Button  btn_postad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd);

        price = findViewById(R.id.price);
        unit = findViewById(R.id.unit);
        bedroom = findViewById(R.id.bedroom);
        bathroom = findViewById(R.id.bathroom);
        pet = findViewById(R.id.pet);
        smoke = findViewById(R.id.smoke);
        parking = findViewById(R.id.parking);

        /* -------*/
        String[] items = new String[] {
                "Fixed Rates",
                "Please Contact"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                items
        );

        price.setAdapter(adapter);

        /* -------*/

        String[] units = new String[] {
                "Apartment",
                "Room",
                "House",
                "Condo"
        };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                units
        );

        unit.setAdapter(adapter1);

        /* -------*/

        String[] Bedrooms = new String[] {
                "Studio",
                "1",
                "1 + Den",
                "2",
                "2 + Den",
                "3",
                "3 + Den",
                "4",
                "4 + Den",
                "5+"

        };

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                Bedrooms
        );

        bedroom.setAdapter(adapter2);

        /* -------*/


        String[] bathrooms = new String[] {
                "1",
                "1.5",
                "2",
                "2.5",
                "3",
                "3.5",
                "4",
                "4.5",
                "5",
                "5.5",
                "6"
        };

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                bathrooms
        );

        bathroom.setAdapter(adapter3);

        /* -------*/


        String[] pets = new String[] {
                "Yes",
                "No",
                "Limited "
        };

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                pets
        );

        pet.setAdapter(adapter4);

        /* -------*/


        String[] smokes = new String[] {
                "Yes",
                "No",
                "Outdoors only "
        };

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                smokes
        );

        smoke.setAdapter(adapter5);

        /* -------*/


        String[] parkings = new String[] {
                "0",
                "1",
                "2",
                "3+"
        };

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                parkings
        );

        parking.setAdapter(adapter6);


    }
}