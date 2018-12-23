package cse.fet.gkv.oruggt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {

    private ImageView iv;
    Intent i;
    String uname;
    SharedPreferences sp;
    SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iv=(ImageView) findViewById(R.id.imganim);
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.myanimation);
        iv.startAnimation(myanim);
        sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        e=sp.edit();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null) {
            if (user.getDisplayName()==null){
                DatabaseReference uame= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("NAME");
                uame.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uname=(String) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(this,"Welcome Mr."+uname,Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"Welcome Mr."+user.getDisplayName(),Toast.LENGTH_SHORT).show();
            i = new Intent(Main2Activity.this, MainActivity.class);
            startService(new Intent(Main2Activity.this,LocationStarter.class));
        }
            else{
            i=new Intent(Main2Activity.this,LoginPage.class);
        e.putBoolean("new",true);
        e.apply();}
        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally
                {
                    startActivity(i);
                    finish();
                }
            }};
            timer.start();
    }

}
