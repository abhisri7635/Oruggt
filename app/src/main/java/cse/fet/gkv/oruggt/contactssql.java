package cse.fet.gkv.oruggt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JAPNEET SINGH on 16-Jan-18.
 */

public class contactssql extends SQLiteOpenHelper {
    public static final int SQLITE_DATABASE_VERSION = 1;

    private static contactssql instance;

    public static contactssql getInstance(Context ctx) {
        if (instance == null) {
            instance = new contactssql(ctx);
        }
        return instance;
    }
    private contactssql(Context context) {
        super(context, "Contacts", null, SQLITE_DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists contacts (id integer,name text,contacts text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");

        onCreate(db);
    }
    public void insertdata(int i,String nm,String nmb){
    SQLiteDatabase db=this.getWritableDatabase();
    db.execSQL("delete from contacts where id="+i);
    ContentValues v=new ContentValues();
    v.put("id",i);
    v.put("name",nm);
    v.put("contacts",nmb);
    db.insert("contacts",null,v);
    }
    public String getnum(int i){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.query("contacts",new String[]{"id","name","contacts"},"id=?",new String[]{""+i},null,null,null);
        c.moveToFirst();
        String nm=c.getString(c.getColumnIndex("contacts"));
        return nm;
    }
    public String getname(int i){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.query("contacts",new String[]{"id","name","contacts"},"id=?",new String[]{""+i},null,null,null);
        c.moveToFirst();
        String nm=c.getString(c.getColumnIndex("name"));
        return nm;
    }



}
