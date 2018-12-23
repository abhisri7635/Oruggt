package cse.fet.gkv.oruggt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class Earthquake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        Toolbar tb=findViewById(R.id.nm);
        ImageView img=findViewById(R.id.imear);
        TextView des=findViewById(R.id.desc);
        Bundle bh=getIntent().getExtras();
        int i=bh.getInt("position");
        int rsi=0;
        String rsd="",rst="",rsde="";
        switch (i)
        {
            case 0:rsi=R.drawable.cyclonex;
            rsd=getString(R.string.cyclone);
                rsde=getString(R.string.cyclonee);
            rst="CYCLONE";
            break;
            case 1:rsi=R.drawable.draughtx;
                rsd=getString(R.string.draught);
                rst="DRAUGHT";
                break;
            case 2:rsi=R.drawable.earthquakex;
                rsd=getString(R.string.earthquake);
                rsde=getString(R.string.earthquakee);
                rst="EARTHQUAKE";
                break;
            case 3:rsi=R.drawable.floodx;
                rsd=getString(R.string.flood);
                rst="FLOOD";
                break;
            case 4:rsi=R.drawable.landslidex;
                rsd=getString(R.string.landslide);
                rsde=getString(R.string.landslidee);
                rst="LANDSLIDE";
                break;
            case 5:rsi=R.drawable.tsunamix;
                rsd=getString(R.string.tsunami);
                rst="TSUNAMI";
                break;
        }
        img.setImageResource(rsi);
        des.setText(rsd);
        des.append(rsde);
        tb.setTitle(rst);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
