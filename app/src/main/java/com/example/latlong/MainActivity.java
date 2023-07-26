package com.example.latlong;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView t; EditText la,lo; Button b;
    VideoView v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=findViewById(R.id.textView2);
        la = findViewById(R.id.editTextTextPersonName);
        lo = findViewById(R.id.editTextTextPersonName2);
        b = findViewById(R.id.button);
        getSupportActionBar().hide();
        t.setMovementMethod(new ScrollingMovementMethod());
        v = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.earth_spin);
        v.setVideoURI(uri);
        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        v.start();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address="";
                try {
                    double lat = Double.parseDouble(la.getText().toString());
                    double lon = Double.parseDouble(lo.getText().toString());
                    AddressGenerator a = new AddressGenerator();
                    address = a.getAddressFromLatLng(MainActivity.this, lat, lon);
                }
                catch (Exception e)
                {
                    address="Invalid Location :(";
                }
                t.setText(address);

            }
        });
    }




}

class AddressGenerator {

    public  String getAddressFromLatLng(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String addressString = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();

                // Get individual address lines and append them to the StringBuilder
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i));
                    if (i < address.getMaxAddressLineIndex()) {
                        sb.append(", ");
                    }
                }

                addressString = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressString;
    }
}