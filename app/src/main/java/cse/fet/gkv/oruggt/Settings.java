package cse.fet.gkv.oruggt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
Intent i;
    CheckBox cb;
    boolean cbb;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        e=sp.edit();
        cbb=sp.getBoolean("check",false);
        String []settings={"Edit Profile","Edit Messages To Be sent","Edit Emergency Contact list"};
        Integer []imagesid={
                R.drawable.profileicon2,
                R.drawable.messages,
                R.drawable.contacts
        };
        customadapter  myadapter = new customadapter(Settings.this,settings,imagesid);
        ListView mylistview= (ListView) findViewById(R.id.mylistview);
        mylistview.setAdapter(myadapter);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:i=new Intent(Settings.this,UserInfo.class);break;
                    case 1:i=new Intent(Settings.this,Messages.class);break;
                    case 2:i=new Intent(Settings.this,EditContact.class);break;
                }
                startActivity(i);
            }
        });
        cb=findViewById(R.id.womenfeat);
        cb.setChecked(cbb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.putBoolean("check",cb.isChecked());
                e.apply();
                i=new Intent(Settings.this,MyService.class);
                if (cb.isChecked()){
                    startService(i);
                }
                else stopService(i);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        cbb=sp.getBoolean("check",false);
        cb.setChecked(cbb);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
