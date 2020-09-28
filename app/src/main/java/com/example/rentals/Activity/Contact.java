package com.example.rentals.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.net.Uri;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.example.rentals.R;

import org.w3c.dom.Text;

public class Contact extends AppCompatActivity {
    //EditText Username = (EditText) findViewById(R.id.txtname);
    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Button sendMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        sendMsg = (Button) findViewById(R.id.btnsendsms);
        Button startBtn = (Button) findViewById(R.id.btnsendemail);
        final EditText msg = (EditText) findViewById(R.id.inputtextsms);
        final EditText username=(EditText) findViewById(R.id.txtname);
        final EditText userphone=(EditText) findViewById(R.id.userphone);
        ImageView imageCall = findViewById(R.id.imgcall);
        final TextView phone = (TextView) findViewById(R.id.txtphonenumber);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
        sendMsg.setEnabled(false);
        //check if permission is granted or not
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            sendMsg.setEnabled(true);
        }   //if permission is not granted then check if the user has denied the permission
            else{
                //a pop up will appear asking for required permission to send a sms
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
             }


        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = msg.getText().toString();
                String Phonenumber = phone.getText().toString();

                if (!TextUtils.isEmpty(message) && !TextUtils.isEmpty(Phonenumber))
                    if (checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(Phonenumber, null, message, null, null);
                        Toast.makeText(Contact.this,"Message Sent!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Contact.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                else {
                    Toast.makeText(Contact.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                }
            }
        });


        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }//end


    private boolean checkPermission(String Permission){
        int checkPermission= ContextCompat.checkSelfPermission(this,Permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Thanks for permitting ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Allow to send an sms!", Toast.LENGTH_LONG).show();
                }
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
        }//swtich end
    }//end

    private void makePhoneCall() {
        final TextView phone = (TextView) findViewById(R.id.txtphonenumber);
        String number = phone.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(Contact.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Contact.this,
                        new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(Contact.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Contact.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
