package com.example.wifidolgozat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView tvInfo;
    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();

        bottom_navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.wifi_on:
                                tvInfo.setText("Wifi bekapcsolva");
                                break;

                            case R.id.wifi_off:
                                tvInfo.setText("Wifi kikapcsolva");
                                break;

                            case R.id.wifi_info:
                                WifiInfo();
                                break;

                            default:
                                break;
                        }
                        return true;
                    }
                });
    }

    private void WifiInfo() {
        tvInfo.setText("Wifi infó");
    }

    private void init() {
        tvInfo = findViewById(R.id.tvInfo);
        bottom_navigation = findViewById(R.id.bottom_navigation);
    }
}