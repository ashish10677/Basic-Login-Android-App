package com.example.ashish.mysqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class SignUpDB {

    //public static final String KEY_ROWID = "_rowid_";
    public static final String KEY_EMAIL = "_email";
    public static final String KEY_NAME = "_name";
    public static final String KEY_PASS = "_pass";

    private final String DATABASE_NAME = "SignUpDB";
    private final String DATABASE_TABLE = "SignUp";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public SignUpDB (Context context){
        ourContext=context;
    }

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlcode = "CREATE TABLE " + DATABASE_TABLE + " ("
                    + KEY_NAME + " TEXT NOT NULL, "
                    + KEY_EMAIL + " TEXT PRIMARY KEY, "
                    + KEY_PASS + " TEXT NOT NULL);";

            db.execSQL(sqlcode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }

    public SignUpDB open() throws SQLiteException{
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long insertDB(String name, String email, String pass){

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_EMAIL,email);
        cv.put(KEY_PASS,pass);

        return ourDatabase.insert(DATABASE_TABLE,null, cv);
    }

    public String getData(){

        String [] columns = new String [] {KEY_NAME,KEY_EMAIL,KEY_PASS};

        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);

        String result = "";

        //int iROWID = c.getColumnIndex(KEY_ROWID);
        int iNAME = c.getColumnIndex(KEY_NAME);
        int iEMAIL = c.getColumnIndex(KEY_EMAIL);
        int iPASS = c.getColumnIndex(KEY_PASS);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = result + c.getString(iNAME) + ": " + c.getString(iEMAIL) + ": " + c.getString(iPASS)+"\n";
        }

        c.close();
        return result;

    }

    public String matchData(String email, String passwd){
        String [] columns = new String [] {KEY_NAME,KEY_EMAIL,KEY_PASS};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        int iNAME = c.getColumnIndex(KEY_NAME);
        int iEMAIL = c.getColumnIndex(KEY_EMAIL);
        int iPASS = c.getColumnIndex(KEY_PASS);
        String res = "";
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                if(c.getString(iEMAIL).equals(email) && c.getString(iPASS).equals(passwd)){
                res = c.getString(iNAME);
                return res;
            }
        }

        return null;
    }

    public Boolean matchEmail(String email){
        String [] columns = new String [] {KEY_NAME,KEY_EMAIL,KEY_PASS};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        int iEMAIL = c.getColumnIndex(KEY_EMAIL);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if(c.getString(iEMAIL).equals(email)){
                return false;
            }
        }
        return true;
    }

}
