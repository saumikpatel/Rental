package com.example.rentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Contact extends AppCompatActivity {
    //EditText Username = (EditText) findViewById(R.id.txtname);
    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private Button sendMsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        sendMsg = (Button) findViewById(R.id.btnsendsms);
        final TextView phone = (TextView) findViewById(R.id.txtphonenumber);
        final EditText msg = (EditText) findViewById(R.id.inputtextsms);

        sendMsg.setEnabled(false);
        //check if permission is not granted
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
                    } else {
                        Toast.makeText(Contact.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                else {
                    Toast.makeText(Contact.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                }
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
                break;
        }//swtich end
    }//end
}