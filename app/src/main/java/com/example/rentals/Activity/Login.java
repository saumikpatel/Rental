package com.example.rentals.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    public TextInputLayout Email, Password;
    Button create, login, forgot;
    ProgressDialog pd;
    private FirebaseAuth auth;
    private FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        create = findViewById(R.id.create);
        login = findViewById(R.id.login);
        forgot = findViewById(R.id.forgotpass);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getEditText().getText().toString();
                String pwd = Password.getEditText().getText().toString();
                if (email.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(Login.this, "Please Fill The Form", Toast.LENGTH_SHORT).show();
                    return;
                }
                pd = new ProgressDialog(Login.this);
                pd.setMessage("Loading...");
                pd.show();
                auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            curUser = auth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            pd.dismiss();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(getApplicationContext(), "Email not exist!", Toast.LENGTH_LONG).show();
                                Email.getEditText().getText().clear();
                                Password.getEditText().getText().clear();
                                Email.setError("Email not exist!");
                                Email.requestFocus();
                                pd.dismiss();
                                return;
                            } catch (FirebaseAuthInvalidCredentialsException e) {


                                Toast.makeText(getApplicationContext(), "Wrong Credential!", Toast.LENGTH_LONG).show();
                                Password.getEditText().getText().clear();

                                Email.requestFocus();
                                pd.dismiss();
                                return;
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        }


                    }
                });


            }
        });

        /**
         *  go to Create ACC activity
         */


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(i);

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);

            }
        });
    }


}