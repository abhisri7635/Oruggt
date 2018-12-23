package cse.fet.gkv.oruggt;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.net.Uri;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Disaster_Management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster__management);


    }
    public void selectpage(View v)
    {
        Intent i=new Intent(this,Select_Menu.class);
        startActivity(i);
    }
    public void onclickxyz(View v) {
        String uri = "http://www.pmnrf.gov.in";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
    public void sgnout(View v) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            if (FirebaseAuth.getInstance().getCurrentUser() == null)
                Toast.makeText(this, "Successfully Signed Out", Toast.LENGTH_LONG).show();
            else
                Log.v("Error Sign Out", "Wrong conditions");
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
