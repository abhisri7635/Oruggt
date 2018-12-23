package cse.fet.gkv.oruggt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class accident extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident);
    } public void onclickabc(View v) {
        String uri = "http://maps.google.com/maps?q=nearby+hospitals";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
    @SuppressLint("MissingPermission")
    public void onclickxyz(View v){
        String number="102";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        startActivity(callIntent);
    }
    public void onc(View v){
        extras e=new extras();
        SharedPreferences sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        String t=sp.getString("Accident",null);
        e.msg(t,accident.this);
       // String l=sp.getString("userid",null);
       // e.alert(l,t,this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
