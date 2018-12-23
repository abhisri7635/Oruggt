package cse.fet.gkv.oruggt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Location_Service extends IntentService {
    LocationManager lm;
    LocationListener ll;

    public Location_Service() {
        super("Location_Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        lm = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        ll = new loclistener();
        lm.requestLocationUpdates(lm.GPS_PROVIDER, 5000, 10, ll);
    }

    public class loclistener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            double lat=location.getLatitude();
            double lng=location.getLongitude();
            DatabaseReference dr= FirebaseDatabase.getInstance().getReference().child("Locations of Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            DatabaseReference dlt=dr.child("Latitude");
            dlt.setValue(""+lat);
            DatabaseReference dlg=dr.child("Longitude");
            dlg.setValue(""+lng);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
