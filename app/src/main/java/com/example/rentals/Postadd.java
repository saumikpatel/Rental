package com.example.rentals;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


public class Postadd extends AppCompatActivity {

    private TextInputLayout textInputLayout;

    private AutoCompleteTextView dropDownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd);

        textInputLayout = findViewById(R.id.textField3);
        dropDownText = findViewById(R.id.dropdown_text);

        String[] items = new String[] {
                "Fixed Rates",
                "Please Contact"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                items
        );

        dropDownText.setAdapter(adapter);



    }
}