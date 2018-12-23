package cse.fet.gkv.oruggt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Select_Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__menu);

        String [] res ={
                String.valueOf(R.drawable.cyl),
                String.valueOf(R.drawable.drough),
                String.valueOf(R.drawable.earthquake),
                String.valueOf(R.drawable.flood),
                String.valueOf(R.drawable.landslide),
                String.valueOf(R.drawable.tsunami)
        };
        ListAdapter la=new customAdaptor(this,res);
        ListView f=findViewById(R.id.lv);
        f.setAdapter(la);
        f.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i=new Intent(Select_Menu.this,Earthquake.class);
                        i.putExtra("position",position);
                        startActivity(i);
                    }
                }


        );
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
