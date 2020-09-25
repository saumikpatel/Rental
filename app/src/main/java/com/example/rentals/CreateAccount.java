package com.example.rentals;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    EditText create_name, create_phone, create_email, create_password, create_confirmPassword;
    Button create_btn, login_btn;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mFirebaseAuth = FirebaseAuth.getInstance();
        create_name = findViewById(R.id.create_name);
        create_phone = findViewById(R.id.create_phone);
        create_email = findViewById(R.id.create_email);
        create_password = findViewById(R.id.create_password);
        create_confirmPassword = findViewById(R.id.create_confirmPassword);
        create_btn = findViewById(R.id.create_btn);
        login_btn = findViewById(R.id.login_btn);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                String Name = create_name.getText().toString();
                String Phone = create_phone.getText().toString();
                String Email = create_email.getText().toString();
                String Password = create_password.getText().toString();
                String ConfirmPassword = create_confirmPassword.getText().toString();
                if (Name.isEmpty() || Phone.isEmpty() || Email.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty()) {
                    Toast.makeText(CreateAccount.this, "Please Fill The Form", Toast.LENGTH_SHORT).show();

                    return;

                }
                if (Password.length() < 6) {
                    Toast.makeText(CreateAccount.this, "Password should be 6 characters or long", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Phone.length() < 10) {
                    Toast.makeText(CreateAccount.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Password.equals(ConfirmPassword)) {
                    Toast.makeText(CreateAccount.this, "Confirm password doesn't match with password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isEmailValid(Email)){
                    Toast.makeText(CreateAccount.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Map<String,Object> usermap=new HashMap<>();
                usermap.put("Name",Name);
                usermap.put("Phone",Phone);
                usermap.put("Email",Email);

                mFirebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            db.collection("User").document(mFirebaseAuth.getCurrentUser().getUid()).set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext().getApplicationContext(),"Register Success!",Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                        }



                        else {
                            Toast.makeText(CreateAccount.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }
        });


            }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]+$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}