package com.br.projekt.internshipproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class UserDetails extends AppCompatActivity {
    TextView id;
    TextView firstName;
    TextView lastName;
    TextView email;
    ImageView userAvatar;
    PhotoViewAttacher mAttacher;
    PhotoView photoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        id = findViewById(R.id.id);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        //userAvatar = findViewById(R.id.userAvatar);
        userAvatar = findViewById(R.id.userAvatar);

        mAttacher = new PhotoViewAttacher(userAvatar);


        firstName.setText(getIntent().getStringExtra("Emri"));
        lastName.setText(getIntent().getStringExtra("Mbiemri"));
        email.setText(getIntent().getStringExtra("Email"));
        String s = getIntent().getStringExtra("Id");
        id.setText(s);

        Picasso.get().load(getIntent().getStringExtra("Avatar")).into(userAvatar);


    }
}
