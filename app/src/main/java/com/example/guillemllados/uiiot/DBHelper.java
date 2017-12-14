package com.example.guillemllados.uiiot;

/**
 * Created by guillemllados on 30/11/17.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PAE.db";

    public static final String DATA_TABLE_NAME = "data";
    public static final String DATA_COLUMN_ID = "device_id";
    public static final String DATA_COLUMN_TEMPERATURE = "temperature";
    public static final String DATA_COLUMN_HUMIDITY = "humidity";
    public static final String DATA_COLUMN_CALENDAR = "calendar";
    public static final String DATA_COLUMN_ISLAST = "isLast";

    public static final String DEVICE_TABLE_NAME = "device";
    public static final String DEVICE_COLUMN_ID = "device_id";
    public static final String DEVICE_COLUMN_LATITUDE = "latitude";
    public static final String DEVICE_COLUMN_LONGITUDE = "longitude";
    public static final String DEVICE_COLUMN_ALARM = "alarm"; // hh:mm dl-dt-dm-dj-dv-ds-dg

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+DATA_TABLE_NAME+ "("+DATA_COLUMN_ID+" text, "+DATA_COLUMN_TEMPERATURE+
                " text, "+DATA_COLUMN_HUMIDITY+" text, " + DATA_COLUMN_CALENDAR+" text, "+DATA_COLUMN_ISLAST+" text)");
        db.execSQL("create table "+DEVICE_TABLE_NAME+ "("+DEVICE_COLUMN_ID+" text, "+DEVICE_COLUMN_LATITUDE+
                " text, "+DEVICE_COLUMN_LONGITUDE+" text, " + DEVICE_COLUMN_ALARM+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+DATA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DEVICE_TABLE_NAME);
        onCreate(db);
    }

    public boolean addAtribute (String id,Atributs a) {
        if(!isAtributSaved(id,a)){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DATA_COLUMN_ID, id);
            contentValues.put(DATA_COLUMN_TEMPERATURE, a.getAtrib1());
            contentValues.put(DATA_COLUMN_HUMIDITY, a.getAtrib2());
            contentValues.put(DATA_COLUMN_CALENDAR, a.getDate().toStringSQL());
            contentValues.put(DATA_COLUMN_ISLAST, "1");
            changeLast(id);
            db.insert(DATABASE_NAME, null, contentValues);
            return true;
        }
        return false;
    }

    public boolean addDevice (Item i) {
        if(!isDeviceSaved(i.getId())){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DEVICE_COLUMN_ID, i.getId());
            contentValues.put(DEVICE_TABLE_NAME, i.getNom());
            contentValues.put(DEVICE_COLUMN_LONGITUDE, i.getLongitude());
            contentValues.put(DEVICE_COLUMN_LATITUDE, i.getLatitude());
            contentValues.put(DEVICE_COLUMN_ALARM,i.getAlarm());
            db.insert(DATABASE_NAME, null, contentValues);
            return true;
        }
        return false;
    }

    public Cursor changeLast(String device_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "Update "+DATA_TABLE_NAME+" SET"+DATA_COLUMN_ISLAST+"='0' WHERE "+DATA_COLUMN_ID+"="+device_id+" AND "+DATA_COLUMN_ISLAST+"='1'", null );
        return res;

    }

    public boolean isAtributSaved(String id,Atributs a){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+DATA_TABLE_NAME+ " WHERE "+DATA_COLUMN_ID+"= "+id+" AND "+DATA_COLUMN_CALENDAR+"= "+a.getDate().toStringSQL(),null);
        if(res.getColumnCount()>0){
            return true;
        }
        return false;

    }
    public boolean isDeviceSaved(String id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+DEVICE_TABLE_NAME+ " WHERE "+DEVICE_COLUMN_ID+"= "+id,null);
        if(res.getColumnCount()>0){
            return true;
        }
        return false;

    }

    public Item getBasicDataDevice(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+DEVICE_TABLE_NAME+" WHERE "+DEVICE_COLUMN_ID+"="+id,null);
        String name = res.getString(res.getColumnIndex(DEVICE_TABLE_NAME));
        String lat = res.getString(res.getColumnIndex(DEVICE_COLUMN_LATITUDE));
        String lon = res.getString(res.getColumnIndex(DEVICE_COLUMN_LONGITUDE));

        Item i = new Item(name,id,"IMAGE",lat,lon);
        return i;

    }

    public Item getAllDataDevice(String id){
        Item i = getBasicDataDevice(id);
        ArrayList<Atributs> atributs = getAtributsFromDeviceId(id);
        for(Atributs a : atributs){
            i.addAtribute(a);
        }
        return i;
    }

    public int numberOfDevices(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DEVICE_TABLE_NAME);
        return numRows;
    }
    public int numberOfData(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DATA_TABLE_NAME);
        return numRows;
    }

    public boolean updateDevice (String deviceId, String latitude, String longitude, String alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(latitude!=""){
            contentValues.put(DEVICE_COLUMN_LATITUDE, latitude);
        }
        if(longitude!=""){
            contentValues.put(DEVICE_COLUMN_LONGITUDE, longitude);
        }
        if(alarm!=""){
            contentValues.put(DEVICE_COLUMN_ALARM, alarm);
        }
        db.update(DEVICE_TABLE_NAME,contentValues,DEVICE_COLUMN_ID+" = ? ",new String[]{deviceId});
        return true;
    }

    public Boolean deleteDevice (String deviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery( "Delete from "+DEVICE_TABLE_NAME+" WHERE "+DEVICE_COLUMN_ID+"="+deviceId, null );
        db.rawQuery( "Delete from "+DATA_COLUMN_ID+" WHERE "+DEVICE_COLUMN_ID+"="+deviceId, null );
        return true;
    }

    public HashMap<String,Atributs> getLastsAtribust(){
        SQLiteDatabase db = this.getWritableDatabase();
        HashMap<String,Atributs> hashMap = new HashMap<>();
        Atributs a;
        CalendarE calendarE;



        String query = "SELECT * FROM "+DATA_TABLE_NAME+ " WHERE "+DATA_COLUMN_ISLAST+"= 1";

        Cursor cur = db.rawQuery(query,null);
        cur.moveToFirst();

        int i = cur.getCount();

        while(cur.moveToNext()){
            int h = cur.getColumnIndex(DATA_COLUMN_CALENDAR);
            String q = cur.getString(h);
            calendarE = new CalendarE(cur.getString(cur.getColumnIndex(DATA_COLUMN_CALENDAR)));
            a = new Atributs(calendarE,cur.getString(cur.getColumnIndex(DATA_COLUMN_HUMIDITY)),
                    cur.getString(cur.getColumnIndex(DATA_COLUMN_TEMPERATURE)));
            hashMap.put(cur.getString(cur.getColumnIndex(DATA_COLUMN_ID)),a);
        }

        return hashMap;
    }

    public ArrayList<Atributs> getAtributsFromDeviceId(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Atributs> list = new ArrayList<>();
        Atributs a;
        CalendarE calendarE;

        Cursor cur = db.rawQuery("SELECT * FROM "+DATA_TABLE_NAME+ "WHERE "+DATA_COLUMN_ID+"="+id,null);
        for(int i=0;i<cur.getColumnCount();i++){
            calendarE = new CalendarE(cur.getString(cur.getColumnIndex(DATA_COLUMN_CALENDAR)));
            a = new Atributs(calendarE,cur.getString(cur.getColumnIndex(DATA_COLUMN_HUMIDITY)),
                    cur.getString(cur.getColumnIndex(DATA_COLUMN_TEMPERATURE)));
            list.add(a);
        }

        return list;
    }
}
