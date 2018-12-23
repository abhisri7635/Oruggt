package cse.fet.gkv.oruggt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class developers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
