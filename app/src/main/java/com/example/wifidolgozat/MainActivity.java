package com.example.wifidolgozat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView tvInfo;
    private BottomNavigationView bottom_navigation;

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;

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
                                WifiOn();
                                break;

                            case R.id.wifi_off:
                                WifiOff();
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


    private void WifiOn() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tvInfo.setText("Nincs jogosultság a wifi állapot módosítására");
            Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
            startActivityForResult(panelIntent, 0);
            return;
        }
        wifiManager.setWifiEnabled(true);
        tvInfo.setText("Wifi bekapcsolva");
    }


    private void WifiOff() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tvInfo.setText("Nincs jogosultság a wifi állapot módosítására");
            Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
            startActivityForResult(panelIntent, 0);
            return;
        }
        wifiManager.setWifiEnabled(false);
        tvInfo.setText("Wifi kikapcsolva");
    }


    private void WifiInfo() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (netInfo.isConnected()) {
            int ip_number = wifiInfo.getIpAddress();
            String ip = Formatter.formatIpAddress(ip_number);
            tvInfo.setText("IP: "+ip);
        }
        else {
            tvInfo.setText("Nem csatlakoztál wifi hálózatra");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED
                    || wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                tvInfo.setText("Wifi bekapcsolva");
            }
            else if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED
                    || wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING) {
                tvInfo.setText("Wifi kikapcsolva");
            }
        }
    }


    private void init() {
        tvInfo = findViewById(R.id.tvInfo);
        bottom_navigation = findViewById(R.id.bottom_navigation);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
    }
}