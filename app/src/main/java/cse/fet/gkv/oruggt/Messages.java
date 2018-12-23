package cse.fet.gkv.oruggt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Messages extends AppCompatActivity {
SharedPreferences sp;
SharedPreferences.Editor et;
EditText sos,acc,wom;
String ms,ma,mw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        sos=findViewById(R.id.msgsos);
        acc=findViewById(R.id.msgacc);
        wom=findViewById(R.id.msgwom);
        sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        et=sp.edit();
        ms=sp.getString("SOS",null);
        ma=sp.getString("Accident",null);
        mw=sp.getString("Women",null);
        if (ms==null||mw==null||ma==null){
            ms="Help me! I need your help.";
            ma="Help me! I met with an accident.";
            mw="Help me! I feel insecure.";
            et.putString("SOS",ms);
            et.putString("Accident",ma);
            et.putString("Women",mw);
            et.apply();
        }
        sos.setText(ms);
        acc.setText(ma);
        wom.setText(mw);
    }
    public void save(View v){
        ms=sos.getText().toString();
        ma=acc.getText().toString();
        mw=wom.getText().toString();
        et.putString("SOS",ms);
        et.putString("Accident",ma);
        et.putString("Women",mw);
        et.apply();
        if (sp.getBoolean("new",false)){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
