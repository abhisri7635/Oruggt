package cse.fet.gkv.oruggt;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase y=FirebaseDatabase.getInstance();
    DatabaseReference main= FirebaseDatabase.getInstance().getReference();
    DatabaseReference us=main.child("userlist");
    String [] users;
    String uname ;
    int e,j,m=1;
    alert [] a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a=new alert[50];
        final NotificationCompat.Builder ap=new NotificationCompat.Builder(this);
        final NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        final int q=5675;
        a[0]=null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final Uri ou=Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.emergency_alarm);
        SharedPreferences sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        final String my=sp.getString("userid",null);
        users=new String[50];
        users[0]=null;
        final String tyr=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference r=main.child("users");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String p=(String)dataSnapshot.getValue();
                e=Integer.parseInt(p);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        us.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> val=(Map)dataSnapshot.getValue();
                for (j=1;j<=e;j++){
                    String v="user_"+i;
                    if (v.contentEquals(my))
                        continue;
                    users[j]=val.get(v);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference px=main.child(tyr).child("alerts");
        px.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for (j=1;j<e;j++){
                 DataSnapshot p=dataSnapshot.child(users[j]);
                 if (!p.exists())
                     continue;
                 else {
                     Map<String,String> val=(Map)p.getValue();
                     a[m]=new alert(val.get("NAME"),val.get("MESSAGE"),val.get("CONTACT NUMBER"),users[j]);
                     ap.setSound(ou).setSmallIcon(R.drawable.alert).setContentTitle("Help Alert").setContentText("Open App to continue");
                     nm.notify(q,ap.build());
                     m++;
                 }
             }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hv=navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        displayRightnavigation();
        TextView n=hv.findViewById(R.id.nme);
        TextView em=hv.findViewById(R.id.emailnm);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uemail=user.getEmail();
        if (user.getDisplayName()==null){
            DatabaseReference uame=main.child(user.getUid());
            uame.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,String> v =(Map) dataSnapshot.getValue();
                    uname=v.get("NAME");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else uname=user.getDisplayName();
        Toast.makeText(MainActivity.this,uname,Toast.LENGTH_SHORT).show();
        n.setText(uname);
            em.setText(uemail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            displayRightnavigation();
        }

        return super.onOptionsItemSelected(item);
    }
Intent i;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.disaster) {
            i=new Intent(this,Disaster_Management.class);
            startActivity(i);
        } else if (id == R.id.accident) {
            i=new Intent(this,accident.class);
            startActivity(i);
        } else if (id == R.id.crime) {
            i=new Intent(this,CrimeReporting.class);
            startActivity(i);
        }else if (id == R.id.settings) {
            i=new Intent(this,Settings.class);
            startActivity(i);
        }
        else if (id == R.id.info) {
            i=new Intent(this,info.class);
            startActivity(i);
        }
        if (id==R.id.sgnout){
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    Toast.makeText(this, "Successfully Signed Out", Toast.LENGTH_LONG).show();
                else
                    Log.v("Error Sign Out", "Wrong conditions");
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
private void displayRightnavigation(){
    final NavigationView navigationViewRight = (NavigationView) findViewById(R.id.nav_view_right);
    navigationViewRight.inflateMenu(R.menu.navigation_drawer_right);
    Menu r=navigationViewRight.getMenu();
    for (j=1;j<m;j++){
        r.add(0,j,j,"ALERT");
    }
    navigationViewRight.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            for (j=1;j<=m;j++){
                if (id==j)
                {
                    alertbox(a[j].getMsg(),a[j].getname(),a[j].getNmb(),a[j].getUid());
                }
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.END);
            return true;
        }
    });
}
public void news(View v){
    BottomSheetDialog btd=new BottomSheetDialog(MainActivity.this);
    View vw=getLayoutInflater().inflate(R.layout.activity_webview,null);
    btd.setContentView(vw);
    BottomSheetBehavior bsb=BottomSheetBehavior.from((View)vw.getParent());
    bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()));
    btd.show();
    WebView wb=btd.findViewById(R.id.wv);
    wb.setWebViewClient(new WebViewClient());
    wb.loadUrl("https://www.google.co.in/search?q=disaster+and+women+safety+india&safe=active&tbm=nws&prmd=niv&source=lnms");
}
public void amt(View v){
    /*Toast.makeText(this,"SOS alert has been sent successfully",Toast.LENGTH_SHORT).show();
    extras e=new extras();
    SharedPreferences sp=getSharedPreferences("message", Context.MODE_PRIVATE);
    String t=sp.getString("SOS",null);
    //String l=sp.getString("userid",null);
    e.msg(t,this);*/
    Intent h=new Intent("android.intent.action.MAIN");
    h.setAction(Intent.ACTION_SEND);
    h.setType("text/plain");
    h.setPackage("com.whatsapp");
    h.putExtra("jid","918874809810"+"@s.whatsapp.net");
    h.putExtra(Intent.EXTRA_TEXT,"Reply me yes if you recieve this");
    startActivity(h);
    //Cursor c=getContentResolver().query();
}

protected void alertbox(String msg, String nm, String nb, final String uid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg+"\nNAME: "+nm+"\nCONTACT NUMBER: "+nb)
                .setCancelable(false)
                .setTitle("** I need Your Help **")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference mm=main.child("alerts").child(uid);
                                mm.getRef().removeValue();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public  class alert{
        String name,msg,nmb,uid;

        public alert(String name, String msg, String nmb,String uid) {
            this.name = name;
            this.msg = msg;
            this.nmb = nmb;
            this.uid=uid;
        }
        public String getname(){return name;}
        public String getMsg(){return msg;}
        public String getNmb(){return nmb;}
        public String getUid(){return uid;}

    }
}
