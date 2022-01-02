
package com.example.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class MainActivity<LocationRequest> extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private TextView lat1, long1, lat2, long2;
    private Button get_gps, get_coord;
    private EditText place;

    FusedLocationProviderClient fusedLocationProviderClient;

    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat1 = findViewById(R.id.lat1);
        long1 = findViewById(R.id.long1);
        get_gps = findViewById(R.id.btn_gps);
        lat2 = findViewById(R.id.lat2);
        long2 = findViewById(R.id.long2);
        place = findViewById(R.id.place);
        get_coord = findViewById(R.id.btn_get_coord);

        locationRequest = LocationRequest.create().setInterval(30 * 1000).setFastestInterval(5 * 1000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        get_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGPS();
            }
        });

        get_coord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCoord(place.getText().toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showGPS();
                } else {
                    Toast.makeText(MainActivity.this, "Provide GPS Permissions", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void showGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        Toast.makeText(MainActivity.this, "Getting GPS Location...", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    try {
                        lat1.setText(String.valueOf(location.getLatitude()));
                        long1.setText(String.valueOf(location.getLongitude()));
                        System.out.println("lat 1"+lat1);
                        System.out.println("long 1"+long1);
                    } catch (Exception e) {
                        lat1.setText("null");
                        long1.setText("null");
                        Toast.makeText(MainActivity.this, "Error in getting GPS location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void showCoord(String place) {
        Toast.makeText(MainActivity.this, "Finding coordinates...", Toast.LENGTH_SHORT).show();
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        System.out.println(place);

        try {
            List<Address> address;
            address = geocoder.getFromLocationName(place, 1);
            if (address.size() > 0) {

                lat2.setText(String.valueOf(address.get(0).getLatitude()));
                long2.setText(String.valueOf(address.get(0).getLongitude()));
                System.out.println(address.get(0).getLatitude());
                System.out.println(address.get(0).getLongitude());
            } else {
                Toast.makeText(MainActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                lat2.setText("null");
                long2.setText("null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
