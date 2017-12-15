package com.example.flo.gestionduparcvert.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.flo.gestionduparcvert.R;
import com.example.flo.gestionduparcvert.database.DatabaseHelper;
import com.example.flo.gestionduparcvert.database.Probleme;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AjouterActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager​;

    private Spinner spinner;
    private Geocoder geocoder;
    private EditText adresseText;
    private EditText descriptionText;
    private double longitude = 0;
    private double latitude = 0;
    private DatabaseHelper databaseHelper = null;

    private DatabaseHelper getHelper(){
        if(this.databaseHelper == null){
            this.databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return this.databaseHelper;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        this.geocoder = new Geocoder(this, Locale.getDefault());
        this.adresseText = (EditText) findViewById(R.id.adresseText);
        this.descriptionText = (EditText) findViewById(R.id.descriptionText);
        //Ajout des items dans le Spinner
         spinner = (Spinner) findViewById(R.id.typespinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        this.locationManager​ = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setLocationManager();
    }

    private void setLocationManager() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            }
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
            return;
        }

        if (this.locationManager​.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            this.locationManager​.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0 * 100, this);
            Context context = getApplicationContext();
            CharSequence text = "GPS PROVIDER!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "network PROVIDER!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            this.locationManager​.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0 * 100, this);
        }
    }

public void locateMe(View v){

    try {
        List<Address> results = geocoder.getFromLocation(this.latitude,this.longitude,1);
        if(results.size() > 0){
            Address result = results.get(0);
            String address = "";
            ArrayList<String> addressFragments = new ArrayList<String>();
            for(int i = 0; i <= result.getMaxAddressLineIndex(); i++) {
                address += result.getAddressLine(i) + System.getProperty("line.separator");
            }
            adresseText.setText(address);
        }
        else adresseText.setText("Aucune adresse trouvé. Merci d'essayer a nouveau");
    } catch (IOException e) {
        e.printStackTrace();
        Log.d("GPS",e.getMessage());
        adresseText.setText("Aucune adresse trouvé ou GPS desactivé. Merci d'essayer a nouveau");
    }
}
    public void ajouter_un_probleme(View v) {
        if (this.longitude != 0 && this.latitude != 0 && this.spinner.getSelectedItem().toString() != "") {
            try {
                final Dao<Probleme, Integer> problemeDao = getHelper().getProblemeDao();
                Probleme probleme = new Probleme(this.spinner.getSelectedItem().toString(), this.adresseText.getText().toString(), this.longitude, this.latitude, this.descriptionText.getText().toString());
                problemeDao.create(probleme);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            setResult(1);
            finish();
        }else{
            String msg = "activer votre GPS";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        this.latitude=location.getLatitude();
        this.longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        String msg = String.format(getResources().getString(R.string.provider_enabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        String msg = String.format(getResources().getString(R.string.provider_disabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
