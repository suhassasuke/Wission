package com.example.wission;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class UserProfile extends AppCompatActivity {

    TextView id, name, familyname, email, givenname;
    ImageView profile;
    Button youtubebutton;
    Uri mProfile;
    String mId, mName, mFamilyName, mEmail, mGivenName;
    Bitmap bitmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        Bundle extras = getIntent().getExtras();
        mId = (String) extras.get("id");
        mName = (String) extras.get("name");
        mFamilyName = (String) extras.get("familyname");
        mProfile = (Uri) extras.get("photo");
        mEmail = (String) extras.get("email");
        mGivenName = (String) extras.get("givenname");

        id = (TextView) findViewById(R.id.userid);
        name = (TextView) findViewById(R.id.name);
        familyname = (TextView) findViewById(R.id.familyname);
        profile = (ImageView) findViewById(R.id.profile);
        email = (TextView) findViewById(R.id.email);
        givenname = (TextView) findViewById(R.id.givenname);
        youtubebutton = (Button) findViewById(R.id.youtubebutton);

        id.setText(mId);
        name.setText(mName);
        familyname.setText(mFamilyName);
        email.setText(mEmail);
        givenname.setText(mGivenName);



        youtubebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), YouTube.class);
                startActivity(intent);
                finish();
            }
        });

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here
                    try {
                        URL url = new URL(mProfile.toString());
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        profile.setImageBitmap(bitmap);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

}
