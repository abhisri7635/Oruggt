package cse.fet.gkv.oruggt;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class UserInfo extends AppCompatActivity {
TextView [] ti;
EditText [] ed;
String [] tis=new String[7];
String [] eds=new String[7],tt=new String[9];
Spinner bgs;
int day,month,year,i;
FirebaseDatabase db=FirebaseDatabase.getInstance();
DatabaseReference dr=db.getReference();
FirebaseUser user;
    String tr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        final ProgressDialog pp=new ProgressDialog(this);
        pp.setMessage("Loading any previously stored data");
        pp.show();
        user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dm=dr.child(user.getUid());
        ti= new TextView[]{
                findViewById(R.id.tvadd),
                findViewById(R.id.tvalg),
                findViewById(R.id.tvbg),
                findViewById(R.id.tvdob),
                findViewById(R.id.tvmed),
                findViewById(R.id.tvmob),
                findViewById(R.id.tvnm)
        };
        ed=new EditText[]{
                findViewById(R.id.etadd),
                findViewById(R.id.etalg),
                findViewById(R.id.etdob),
                findViewById(R.id.etmed),
                findViewById(R.id.etmob),
                findViewById(R.id.etnm)
        };
        bgs=findViewById(R.id.spbg);
        tt[0]=null;
        for (i=1;i<=8;i++)
            tt[i]=(String) bgs.getItemAtPosition(i);
        for (i=0;i<7;i++){
            tis[i]=ti[i].getText().toString();
        }
        dm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.getValue()!=null) {
                  pp.dismiss();
                Map<String,String> values=(Map)dataSnapshot.getValue();
                for (i=0;i<7;i++){
                    if (i==2){
                        tr=values.get(tis[i]);
                        for(int j=1;j<=8;j++)
                        {
                          if (tt[j].contentEquals(tr))
                              bgs.setSelection(j);
                        }
                    }
                    else if (i>2)
                        ed[i-1].setText(values.get(tis[i]));
                    else
                        ed[i].setText(values.get(tis[i]));}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ed[2].setHint("Double Tap to open Date Picker");
    }
  public void svdata(View v){
      String t=bgs.getSelectedItem().toString();
      for (i=0;i<6;i++){
         if (i>=2){
              if (i==2)
                  eds[i]=t;
              eds[i+1]=ed[i].getText().toString();}
          else
              eds[i]=ed[i].getText().toString();
      }
      String uid=user.getUid();
      DatabaseReference tm=dr.child(uid),td;
      for (i=0;i<7;i++){
        td=tm.child(tis[i]);
        td.setValue(eds[i]);
      }
      SharedPreferences sp=getSharedPreferences("message", Context.MODE_PRIVATE);
      if (sp.getBoolean("new",false)){
          Intent i=new Intent(this,EditContact.class);
          startActivity(i);
      }
  }
  public void cldata(View v){
      for (i=0;i<6;i++)
          ed[i].setText("");
      bgs.setSelection(0);
  }
  public void dtset(View v) {
    showDialog(99);
  }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 99) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        ed[2].setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

