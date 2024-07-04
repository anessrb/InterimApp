package com.example.interimapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    private String phoneNumber = "+33760285998";
    private String email = "example@example.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        ImageView callImageView = findViewById(R.id.imageView14);
        ImageView smsImageView = findViewById(R.id.imageView15);
        ImageView emailImageView = findViewById(R.id.imageView19);

        callImageView.setOnClickListener(v -> makePhoneCall());
        smsImageView.setOnClickListener(v -> sendSms());
        emailImageView.setOnClickListener(v -> sendEmail());
    }

    private void makePhoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    private void sendSms() {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", "Hello, I am contacting you regarding...");
        startActivity(smsIntent);
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I am contacting you regarding...");
        startActivity(emailIntent);
    }
}
