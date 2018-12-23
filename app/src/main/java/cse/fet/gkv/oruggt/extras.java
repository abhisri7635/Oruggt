package cse.fet.gkv.oruggt;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by JAPNEET SINGH on 12-Jan-18.
 */

public class extras{
    int e,i;
    String nmb;
    DatabaseReference main=FirebaseDatabase.getInstance().getReference();
    DatabaseReference us=main.child("userlist");
    DatabaseReference loc=main.child("Locations of Users");
    String [] users;
    String name,number;
   public void msg(String msgs,Context c){
       for (i=1;i<=5;i++){
           nmb=contactssql.getInstance(c).getnum(i);
           SmsManager md=SmsManager.getDefault();
           md.sendTextMessage(nmb,null,msgs,null,null);
       }
   }
   public void alert(final String my, final String msgs, final Context c){
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
               for (i=1;i<=e;i++){
                   String v="user_"+i;
                   if (v.contentEquals(my))
                       continue;
                   users[i]=val.get(v);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       loc.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
            DataSnapshot md=dataSnapshot.child(tyr);
            Map<String,String> mm=(Map)md.getValue();
            double mlat=Double.parseDouble(mm.get("Latitude"));
            double mlong=Double.parseDouble(mm.get("Longitude"));
            for (i=1;i<=e;i++){
                DataSnapshot ud=dataSnapshot.child(users[i]);
                Map<String,String> mu=(Map)md.getValue();
                double ulat=Double.parseDouble(mu.get("Latitude"));
                double ulong=Double.parseDouble(mu.get("Longitude"));
                boolean b=range(mlat,mlong,ulat,ulong);
                if(b){
                    DatabaseReference al=main.child(users[i]);
                    al.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String,String> p=(Map)dataSnapshot.getValue();
                            name=p.get("NAME");
                            number=p.get("MOBILE NUMBER");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    DatabaseReference p=al.child("alerts");
                    p.child(tyr).child("NAME").setValue(name);
                    p.child(tyr).child("MESSAGE").setValue(msgs);
                    p.child(tyr).child("CONTACT NUMBER").setValue(number);
                }
            }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
   }
   public boolean range(double lat1, double lon1, double lat2, double lon2){
    double d=distance(lat1,lon1,lat2,lon2);
    if (d<=1.0)
        return true;
    else return false;
   }
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);}

}
