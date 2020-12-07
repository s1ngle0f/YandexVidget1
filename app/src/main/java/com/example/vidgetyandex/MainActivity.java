package com.example.vidgetyandex;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static String TAG = "PARSEDG";
    private String x;// = "55.638317";
    private String y;// = "37.327492";
    private String testUrl = "https://yandex.ru/maps/213/moscow/search/%D0%BE%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0/?from=tabbar&ll=37.327855%2C55.638185&sll=37.327948%2C55.638169&source=serp_navig&sspn=0.001392%2C0.000460&z=21";
    private String url = "https://yandex.ru/maps/213/moscow/search/%D0%BE%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0/?from=tabbar&ll="+ y +"%2C"+ x +"&sll=37.327948%2C55.638169&source=serp_navig&sspn=0.001392%2C0.000460&z=21";
    private String urlwithsctx = "https://yandex.ru/maps/213/moscow/search/%D0%BE%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0/?ll=" + y +"%2C" + x + "&sctx=ZAAAAAgBEAAaKAoSCacz4mT2qUJAEaE5C5us0UtAEhIJAAAYFblnYT8RAABUzT8HRz8oCkC7%2FwZIAVXNzMw%2BWABqAnJ1cACdAc3MTD2gAQCoAQC9AVthKHzCAZQBqL%2FE6MgGqKr%2Fi5MC7uX7io0Fxv2WnpAD1czLjPMBxYWQvM8Fit6Z9bwCk4%2Fj4%2FcDxsb6%2FcECisrk2o8FjZzatq0FgvrW3Y4C0Pm0ubgG4pmdmlKGrd2E6AOq7dONkQaU4vemjAbqo7OMwwaz4bC83QOlvpn5ygLxmPz5owaK%2FvO%2FMJyH1%2BLKA6fLncaEBYmcgK7DAQ%3D%3D&sll="+ y +"%2C" + x + "&sspn=0.001915%2C0.000633&z=21";
    private ArrayList<String> Busses = new ArrayList<>();
    private ArrayList<String> timeofBusses = new ArrayList<>();
    private TextView textView, textView1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Parse parser = new Parse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "START");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView2);
        getPos();
        Log.d(TAG, "X");
        urlwithsctx = "https://yandex.ru/maps/213/moscow/search/%D0%BE%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0/?ll=" + y +"%2C" + x + "&sctx=ZAAAAAgBEAAaKAoSCacz4mT2qUJAEaE5C5us0UtAEhIJAAAYFblnYT8RAABUzT8HRz8oCkC7%2FwZIAVXNzMw%2BWABqAnJ1cACdAc3MTD2gAQCoAQC9AVthKHzCAZQBqL%2FE6MgGqKr%2Fi5MC7uX7io0Fxv2WnpAD1czLjPMBxYWQvM8Fit6Z9bwCk4%2Fj4%2FcDxsb6%2FcECisrk2o8FjZzatq0FgvrW3Y4C0Pm0ubgG4pmdmlKGrd2E6AOq7dONkQaU4vemjAbqo7OMwwaz4bC83QOlvpn5ygLxmPz5owaK%2FvO%2FMJyH1%2BLKA6fLncaEBYmcgK7DAQ%3D%3D&sll="+ y +"%2C" + x + "&sspn=0.001915%2C0.000633&z=21";
        parser.parse(urlwithsctx);
        Busses = parser.getBusses();
        timeofBusses = parser.getTimeofBusses();
        Log.d(TAG,x);
        Log.d(TAG,Busses.toString());
        Log.d(TAG,timeofBusses.toString());

    }

    private void getPos(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 77);
        }else{
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location  != null){
                        x = String.valueOf(location.getLongitude());
                        y = String.valueOf(location.getLatitude());
                        textView.setText(x);
                        textView1.setText(y);
                        Toast.makeText( MainActivity.this, "Success", Toast.LENGTH_SHORT ).show();
                    }
                }
            });
        }
    }

}