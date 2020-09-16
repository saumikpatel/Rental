package com.example.rentals;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Postadd extends AppCompatActivity {


    private AutoCompleteTextView unit, bedroom, bathroom, pet, smoke, parking;
    private TextInputLayout et_title, et_des, et_amt, et_unit, et_pnum, et_date, et_bath, et_bed, et_pet, et_size, et_smoke, et_parking;
    private RadioGroup rbfurnished, rbflaundry, rbLaundryb, rbdishwasher, rbfridge, rbair_conditioning, rbyard, rbbalcony, rbramp, rbaids, rbsuite, rbhydro, rbheat, rbwater, rbtv, rbinternet;
    private RadioButton btn_flaundry, btn_furnished, btn_Laundryb, btn_dishwasher, btn_fridge, btn_air_conditioning, btn_yard, btn_balcony, btn_ramp, btn_aids, btn_suite, btn_hydro, btn_heat, btn_water, btn_tv, btn_internet;
    private Button btn_postad, btn_calender;

    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd);

        et_title = findViewById(R.id.title);
        et_des = findViewById(R.id.des);
        et_amt = findViewById(R.id.amount);
        et_unit = findViewById(R.id.unit1);
        et_pnum = findViewById(R.id.pnum);
        et_date = findViewById(R.id.date);
        et_bath = findViewById(R.id.bathroom1);
        et_bed = findViewById(R.id.bedroom1);
        et_pet = findViewById(R.id.pet1);
        et_size = findViewById(R.id.size);
        et_smoke = findViewById(R.id.smoke1);
        et_parking = findViewById(R.id.parking1);
        btn_calender = findViewById(R.id.calender);


        btn_postad = findViewById(R.id.post_ad);

        rbfurnished = findViewById(R.id.rbfurnished);
        rbflaundry = findViewById(R.id.rbflaundry);
        rbLaundryb = findViewById(R.id.rbLaundryb);
        rbdishwasher = findViewById(R.id.rbdishwasher);
        rbfridge = findViewById(R.id.rbfridge);
        rbair_conditioning = findViewById(R.id.rbair_conditioning);
        rbyard = findViewById(R.id.rbyard);
        rbbalcony = findViewById(R.id.rbbalcony);
        rbramp = findViewById(R.id.rbramp);
        rbaids = findViewById(R.id.rbaids);
        rbsuite = findViewById(R.id.rbsuite);
        rbhydro = findViewById(R.id.rbhydro);
        rbheat = findViewById(R.id.rbheat);
        rbwater = findViewById(R.id.rbwater);
        rbtv = findViewById(R.id.rbtv);
        rbinternet = findViewById(R.id.rbinternet);


        unit = findViewById(R.id.unit);
        bedroom = findViewById(R.id.bedroom);
        bathroom = findViewById(R.id.bathroom);
        pet = findViewById(R.id.pet);
        smoke = findViewById(R.id.smoke);
        parking = findViewById(R.id.parking);
        /* -------*/

        String[] units = new String[]{"Apartment", "Room", "House", "Condo"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                units
        );

        unit.setAdapter(adapter1);

        /* -------*/

        String[] Bedrooms = new String[]{"Studio", "1", "1 + Den", "2", "2 + Den", "3", "3 + Den", "4", "4 + Den", "5+"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                Bedrooms
        );

        bedroom.setAdapter(adapter2);

        /* -------*/


        String[] bathrooms = new String[]{"1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6"};

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                bathrooms
        );

        bathroom.setAdapter(adapter3);

        /* -------*/


        String[] pets = new String[]{"Yes", "No", "Limited"};

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                pets
        );

        pet.setAdapter(adapter4);

        /* -------*/


        String[] smokes = new String[]{"Yes", "No", "Outdoors only"};

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                smokes
        );

        smoke.setAdapter(adapter5);

        /* -------*/


        String[] parkings = new String[]{"0", "1", "2", "3+"};

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(
                Postadd.this,
                R.layout.dropdown_item,
                parkings
        );

        parking.setAdapter(adapter6);


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();

        builder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = builder.build();


        btn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                et_date.getEditText().setText(materialDatePicker.getHeaderText());

            }
        });


        btn_postad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fstore = FirebaseFirestore.getInstance();

                final String Title = et_title.getEditText().getText().toString().trim();
                final String Description = et_des.getEditText().getText().toString().trim();
                final String Amount = et_amt.getEditText().getText().toString().trim();
                String Unit = et_unit.getEditText().getText().toString().trim();
                String PhoneNumber = et_pnum.getEditText().getText().toString().trim();
                String Bathroom = et_bath.getEditText().getText().toString().trim();
                String Bedroom = et_bed.getEditText().getText().toString().trim();
                String PetFriendly = et_pet.getEditText().getText().toString().trim();
                String Size = et_size.getEditText().getText().toString().trim();
                String MoveInDate = et_date.getEditText().getText().toString().trim();
                String SmokePermitted = et_smoke.getEditText().getText().toString().trim();
                String ParkingIncluded = et_parking.getEditText().getText().toString().trim();


               /* Log.v("tagvv", " " + Title);
                Log.v("tagvv", " " + Description);
                Log.v("tagvv", " " + Amount);
                Log.v("tagvv", " " + Unit);
                Log.v("tagvv", " " + PhoneNumber);
                Log.v("tagvv", " " + Bathroom);
                Log.v("tagvv", " " + Bedroom);
                Log.v("tagvv", " " + PetFriendly);
                Log.v("tagvv", " " + Size);
                Log.v("tagvv", " " + MoveInDate);
                Log.v("tagvv", " " + SmokePermitted);
                Log.v("tagvv", " " + ParkingIncluded);*/


                if (Title.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please Enter Title", Toast.LENGTH_LONG).show();
                } else if (Title.length() > 65) {
                    Toast.makeText(Postadd.this, "Title should be 64 letters in length", Toast.LENGTH_LONG).show();
                } else if (Description.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please Enter Description", Toast.LENGTH_LONG).show();
                } else if (Description.length() > 10000) {
                    Toast.makeText(Postadd.this, "Title should be 100000 letters in length", Toast.LENGTH_LONG).show();
                } else if (Amount.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please enter Amount ", Toast.LENGTH_LONG).show();
                } else if (Amount.matches(".*[a-zA-Z]+.*")) {
                    Toast.makeText(Postadd.this, "Please Enter Amount in Digit", Toast.LENGTH_LONG).show();
                } else if (Unit.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please select Unit", Toast.LENGTH_LONG).show();
                } else if (!(Unit.equals("Apartment") || Unit.equals("Room") || Unit.equals("House") || Unit.equals("Condo"))) {
                    Toast.makeText(Postadd.this, "Please Select Unit from DropDown", Toast.LENGTH_LONG).show();
                    et_unit.getEditText().getText().clear();
                } else if (PhoneNumber.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please enter PhoneNumber ", Toast.LENGTH_LONG).show();
                } else if (PhoneNumber.matches(".*[a-zA-Z]+.*")) {
                    Toast.makeText(Postadd.this, "Please Enter PhoneNumber in Digit", Toast.LENGTH_LONG).show();
                } else if (PhoneNumber.length() < 10 || PhoneNumber.length() > 12) {
                    Toast.makeText(Postadd.this, "Please enter 10 to 12 digit PhoneNumber", Toast.LENGTH_LONG).show();
                } else if (MoveInDate.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please enter MoveInDate ", Toast.LENGTH_LONG).show();
                } else if (Bathroom.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please select Bathroom", Toast.LENGTH_LONG).show();
                } else if (!(Bathroom.equals("1") || Bathroom.equals("1.5") || Bathroom.equals("2") || Bathroom.equals("2.5") || Bathroom.equals("3") || Bathroom.equals("3.5") || Bathroom.equals("4") || Bathroom.equals("4.5") || Bathroom.equals("5") || Bathroom.equals("5.5") || Bathroom.equals("6"))) {
                    Toast.makeText(Postadd.this, "Please Select Bathroom from DropDown", Toast.LENGTH_LONG).show();
                    et_bath.getEditText().getText().clear();
                } else if (Bedroom.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please select Bedroom", Toast.LENGTH_LONG).show();
                } else if (!(Bedroom.equals("Studio") || Bedroom.equals("1") || Bedroom.equals("1 + Den") || Bedroom.equals("2") || Bedroom.equals("2 + Den") || Bedroom.equals("3") || Bedroom.equals("3 + Den") || Bedroom.equals("4") || Bedroom.equals("4 + Den") || Bedroom.equals("5+"))) {
                    Toast.makeText(Postadd.this, "Please Select Bedroom from DropDown", Toast.LENGTH_LONG).show();
                    et_bed.getEditText().getText().clear();
                } else if (PetFriendly.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please select Pet Friendly", Toast.LENGTH_LONG).show();
                } else if (!(PetFriendly.equals("Yes") || PetFriendly.equals("No") || PetFriendly.equals("Limited"))) {
                    Toast.makeText(Postadd.this, "Please Select Pet Friendly from DropDown", Toast.LENGTH_LONG).show();
                    et_pet.getEditText().getText().clear();
                } else if (Size.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please enter Size ", Toast.LENGTH_LONG).show();
                } else if (Size.matches(".*[a-zA-Z]+.*")) {
                    Toast.makeText(Postadd.this, "Please Enter Size in Digit", Toast.LENGTH_LONG).show();
                } else if (SmokePermitted.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please select Smoke Permitted", Toast.LENGTH_LONG).show();
                } else if (!(SmokePermitted.equals("Yes") || SmokePermitted.equals("No") || SmokePermitted.equals("Outdoors only"))) {
                    Toast.makeText(Postadd.this, "Please Select Smoke Permitted from DropDown", Toast.LENGTH_LONG).show();
                    et_smoke.getEditText().getText().clear();
                } else if (ParkingIncluded.isEmpty()) {
                    Toast.makeText(Postadd.this, "Please select Parking Included", Toast.LENGTH_LONG).show();
                } else if (!(ParkingIncluded.equals("0") || ParkingIncluded.equals("1") || ParkingIncluded.equals("2") || ParkingIncluded.equals("3+"))) {
                    Toast.makeText(Postadd.this, "Please Select Parking Included from DropDown", Toast.LENGTH_LONG).show();
                    et_parking.getEditText().getText().clear();
                } else {

                    int selectedId1 = rbfurnished.getCheckedRadioButtonId();
                    btn_furnished = findViewById(selectedId1);
                    //Toast.makeText(Postadd.this, btn_furnished.getText(), Toast.LENGTH_SHORT).show();
                    String Furnished = btn_furnished.getText().toString().trim();
                    Log.v("tagvv", " " + Furnished);

                    int selectedId2 = rbflaundry.getCheckedRadioButtonId();
                    btn_flaundry = findViewById(selectedId2);
                    //Toast.makeText(Postadd.this, btn_flaundry.getText(), Toast.LENGTH_SHORT).show();
                    String UnitLaundry = btn_flaundry.getText().toString().trim();
                    Log.v("tagvv", " " + UnitLaundry);

                    int selectedId3 = rbLaundryb.getCheckedRadioButtonId();
                    btn_Laundryb = findViewById(selectedId3);
                    //Toast.makeText(Postadd.this, btn_Laundryb.getText(), Toast.LENGTH_SHORT).show();
                    String BuildingLaundry = btn_Laundryb.getText().toString().trim();
                    Log.v("tagvv", " " + BuildingLaundry);

                    int selectedId4 = rbdishwasher.getCheckedRadioButtonId();
                    btn_dishwasher = findViewById(selectedId4);
                    //Toast.makeText(Postadd.this, btn_dishwasher.getText(), Toast.LENGTH_SHORT).show();
                    String Dishwasher = btn_dishwasher.getText().toString().trim();
                    Log.v("tagvv", " " + Dishwasher);

                    int selectedId5 = rbfridge.getCheckedRadioButtonId();
                    btn_fridge = findViewById(selectedId5);
                    //Toast.makeText(Postadd.this, btn_fridge.getText(), Toast.LENGTH_SHORT).show();
                    String Fridge = btn_fridge.getText().toString().trim();
                    Log.v("tagvv", " " + Fridge);

                    int selectedId6 = rbair_conditioning.getCheckedRadioButtonId();
                    btn_air_conditioning = findViewById(selectedId6);
                    //Toast.makeText(Postadd.this, btn_air_conditioning.getText(), Toast.LENGTH_SHORT).show();
                    String AirConditioning = btn_air_conditioning.getText().toString().trim();
                    Log.v("tagvv", " " + AirConditioning);

                    int selectedId7 = rbyard.getCheckedRadioButtonId();
                    btn_yard = findViewById(selectedId7);
                    //Toast.makeText(Postadd.this, btn_yard.getText(), Toast.LENGTH_SHORT).show();
                    String Yard = btn_yard.getText().toString().trim();
                    Log.v("tagvv", " " + Yard);

                    int selectedId8 = rbbalcony.getCheckedRadioButtonId();
                    btn_balcony = findViewById(selectedId8);
                    //Toast.makeText(Postadd.this, btn_balcony.getText(), Toast.LENGTH_SHORT).show();
                    String Balcony = btn_balcony.getText().toString().trim();
                    Log.v("tagvv", " " + Balcony);

                    int selectedId9 = rbramp.getCheckedRadioButtonId();
                    btn_ramp = findViewById(selectedId9);
                    //Toast.makeText(Postadd.this, btn_ramp.getText(), Toast.LENGTH_SHORT).show();
                    String Barrier_free_Entrance_Ramps = btn_ramp.getText().toString().trim();
                    Log.v("tagvv", " " + Barrier_free_Entrance_Ramps);

                    int selectedId10 = rbaids.getCheckedRadioButtonId();
                    btn_aids = findViewById(selectedId10);
                    //Toast.makeText(Postadd.this, btn_aids.getText(), Toast.LENGTH_SHORT).show();
                    String VisualAids = btn_aids.getText().toString().trim();
                    Log.v("tagvv", " " + VisualAids);

                    int selectedId11 = rbsuite.getCheckedRadioButtonId();
                    btn_suite = findViewById(selectedId11);
                    //Toast.makeText(Postadd.this, btn_suite.getText(), Toast.LENGTH_SHORT).show();
                    String Accessible_Washrooms_in_suite = btn_suite.getText().toString().trim();
                    Log.v("tagvv", " " + Accessible_Washrooms_in_suite);

                    int selectedId12 = rbhydro.getCheckedRadioButtonId();
                    btn_hydro = findViewById(selectedId12);
                    //Toast.makeText(Postadd.this, btn_hydro.getText(), Toast.LENGTH_SHORT).show();
                    String Hydro = btn_hydro.getText().toString().trim();
                    Log.v("tagvv", " " + Hydro);

                    int selectedId13 = rbheat.getCheckedRadioButtonId();
                    btn_heat = findViewById(selectedId13);
                    //Toast.makeText(Postadd.this, btn_heat.getText(), Toast.LENGTH_SHORT).show();
                    String Heat = btn_heat.getText().toString().trim();
                    Log.v("tagvv", " " + Heat);

                    int selectedId14 = rbwater.getCheckedRadioButtonId();
                    btn_water = findViewById(selectedId14);
                    //Toast.makeText(Postadd.this, btn_water.getText(), Toast.LENGTH_SHORT).show();
                    String Water = btn_water.getText().toString().trim();
                    Log.v("tagvv", " " + Water);

                    int selectedId15 = rbtv.getCheckedRadioButtonId();
                    btn_tv = findViewById(selectedId15);
                    //Toast.makeText(Postadd.this, btn_tv.getText(), Toast.LENGTH_SHORT).show();
                    String Tv = btn_tv.getText().toString().trim();
                    Log.v("tagvv", " " + Tv);

                    int selectedId16 = rbinternet.getCheckedRadioButtonId();
                    btn_internet = findViewById(selectedId16);
                    //Toast.makeText(Postadd.this, btn_internet.getText(), Toast.LENGTH_SHORT).show();
                    String Internet = btn_internet.getText().toString().trim();
                    Log.v("tagvv", " " + Internet);


                    Map<String, Object> userMap = new HashMap<>();
                    //  userMap.put("UserID",userid);
                    userMap.put("Title", Title);
                    userMap.put("Description", Description);
                    userMap.put("Amount", Amount);
                    userMap.put("Unit", Unit);
                    userMap.put("PhoneNumber", PhoneNumber);
                    userMap.put("MoveInDate", MoveInDate);
                    userMap.put("Furnished", Furnished);
                    userMap.put("UnitLaundry", UnitLaundry);
                    userMap.put("BuildingLaundry", BuildingLaundry);
                    userMap.put("Bathroom", Bathroom);
                    userMap.put("Dishwasher", Dishwasher);
                    userMap.put("Fridge", Fridge);
                    userMap.put("AirConditioning", AirConditioning);
                    userMap.put("Bedroom", Bedroom);
                    userMap.put("Yard", Yard);
                    userMap.put("PetFriendly", PetFriendly);
                    userMap.put("Balcony", Balcony);
                    userMap.put("Size", Size);
                    userMap.put("Barrier_free_Entrance_Ramps", Barrier_free_Entrance_Ramps);
                    userMap.put("VisualAids", VisualAids);
                    userMap.put("Accessible_Washrooms_in_suite", Accessible_Washrooms_in_suite);
                    userMap.put("SmokePermitted", SmokePermitted);
                    userMap.put("Hydro", Hydro);
                    userMap.put("Heat", Heat);
                    userMap.put("Water", Water);
                    userMap.put("Tv", Tv);
                    userMap.put("Internet", Internet);
                    userMap.put("ParkingIncluded", ParkingIncluded);

                    fstore.collection("Apartment").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Postadd.this, " Data Added in DB ", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            String Error = e.getMessage();
                            Toast.makeText(Postadd.this, " Error:" + Error, Toast.LENGTH_SHORT).show();

                        }
                    });


                }



              /*      if (ag.isEmpty()) {
                    Toast.makeText(dataActivity.this, "Please enter Age ", Toast.LENGTH_LONG).show();
                } else if (ag.matches(".*[a-zA-Z]+.*")) {
                    Toast.makeText(dataActivity.this, "Please Enter Age in Digit", Toast.LENGTH_LONG).show();
                } else if (num.isEmpty()) {
                    Toast.makeText(dataActivity.this, "Please Enter Your Phone Number", Toast.LENGTH_LONG).show();
                } else if (num.length() < 10 || num.length() > 12) {
                    Toast.makeText(dataActivity.this, "Please enter 10 to 12 digit Phone number", Toast.LENGTH_LONG).show();
                    Log.v("tagv", "" + num.length());
                } else if (num.matches(".*[a-zA-Z]+.*")) {
                    Toast.makeText(dataActivity.this, "Please enter proper phone number", Toast.LENGTH_LONG).show();
                } else if (coun.isEmpty()) {
                    Toast.makeText(dataActivity.this, "Please enter your Country", Toast.LENGTH_LONG).show();
                } else if (coun.contains("0") || coun.contains("1") || coun.contains("2") || coun.contains("3") || coun.contains("4") || coun.contains("5") || coun.contains("6") || coun.contains("7") || coun.contains("8") || coun.contains("9")) {
                    Toast.makeText(dataActivity.this, "Please enter your Country , Not digit!!", Toast.LENGTH_LONG).show();
                } else {
*/

                //  if (!fyes.isChecked() && !fno.isChecked()) {

            }
        });


    }
}