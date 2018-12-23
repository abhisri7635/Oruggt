package cse.fet.gkv.oruggt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
    public void onclick(View v)
    {
        Intent i = new  Intent(this , developers.class);
        startActivity(i);}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
