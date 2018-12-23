package cse.fet.gkv.oruggt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CrimeReporting extends AppCompatActivity {
    TextView pt;
    StorageReference st;
    ProgressDialog pp;
    String ftype;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reporting);
        pt=findViewById(R.id.pts);
        sp=findViewById(R.id.spinner);
        pp=new ProgressDialog(this);
        pp.setMessage("Loading your points...");
        pp.show();
        st= FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference pr= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("POINTS");
        pr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pts=(String) dataSnapshot.getValue();
                pp.dismiss();
                if (pts==null)
                    pt.setText("0");
                else
                    pt.setText(pts);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ll=findViewById(R.id.ll);
        btn=findViewById(R.id.btn);

    }
    String spf;
    Intent i;
    LinearLayout ll;
    Button btn;
    public void newf(View v){
        btn.setVisibility(View.INVISIBLE);
        ll.setVisibility(View.VISIBLE);

    }
    public void imgcam(View v){
        spf=sp.getSelectedItem().toString();

        if (spf.contentEquals("Image"))
            Toast.makeText(CrimeReporting.this,"Sorry,this feature is not available right now.\nPlease take photos separately from your camera app and upload it from gallery",Toast.LENGTH_LONG).show();

        else{
            i=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(i,2);}
    }
    public void imggal(View v){
        spf=sp.getSelectedItem().toString();
        if (spf.contentEquals("Image"))
            ftype="image/*";
        else
            ftype="video/*";
        i=new Intent(Intent.ACTION_PICK);
        i.setType(ftype);
        startActivityForResult(i,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&&resultCode==RESULT_OK){
            pp.setMessage("Uploading your FILE...");
            pp.show();
            Uri loc=data.getData();

            StorageReference str=st.child(spf+loc.getLastPathSegment());
            str.putFile(loc).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pp.dismiss();
                    Toast.makeText(CrimeReporting.this,"File Uploaded Successfully",Toast.LENGTH_LONG).show();
                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
