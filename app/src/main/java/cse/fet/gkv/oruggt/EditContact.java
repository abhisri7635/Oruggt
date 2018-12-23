package cse.fet.gkv.oruggt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContact extends Activity {
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    EditText [] name,number;
    Button [] bt;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        name=new EditText[]{null,
                findViewById(R.id.contact1),
                findViewById(R.id.contact2),
                findViewById(R.id.contact3),
                findViewById(R.id.contact4),
                findViewById(R.id.contact5)
        };
        number=new EditText[]{null,
                findViewById(R.id.contactnb1),
                findViewById(R.id.contactnb2),
                findViewById(R.id.contactnb3),
                findViewById(R.id.contactnb4),
                findViewById(R.id.contactnb5)
        };
        bt=new Button[]{null,
                findViewById(R.id.button10),
                findViewById(R.id.button11),
                findViewById(R.id.button12),
                findViewById(R.id.button13),
                findViewById(R.id.button14)
        };
        SharedPreferences sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        if (!sp.getBoolean("new",false)){
            for(i=1;i<=5;i++){
                name[i].setText(contactssql.getInstance(this).getname(i));
                number[i].setText(contactssql.getInstance(this).getnum(i));
            }
        }

            bt[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=1;
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
                }
            });
        bt[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=2;
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });
        bt[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=3;
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });
        bt[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=4;
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });
        bt[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=5;
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK){
            uriContact=data.getData();
            String contactNumber = null;
            Cursor cursorID = getContentResolver().query(uriContact,
                    new String[]{ContactsContract.Contacts._ID},
                    null, null, null);

            if (cursorID.moveToFirst()) {

                contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
            }

            cursorID.close();
            Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                            ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                    new String[]{contactID},
                    null);

            if (cursorPhone.moveToFirst()) {
                contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            cursorPhone.close();

            String contactName = null;
            Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

            if (cursor.moveToFirst()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
            cursor.close();
            name[i].setText(contactName);
            number[i].setText(contactNumber);
            contactssql.getInstance(this).insertdata(i,contactName,contactNumber);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void svgc(View v){
        Toast.makeText(this,"Contacts Saved Successfully",Toast.LENGTH_SHORT).show();
        SharedPreferences sp=getSharedPreferences("message", Context.MODE_PRIVATE);
        if (sp.getBoolean("new",false)){
            Intent i=new Intent(EditContact.this,Messages.class);
            startActivity(i);
            finish();
        }
    }
}
