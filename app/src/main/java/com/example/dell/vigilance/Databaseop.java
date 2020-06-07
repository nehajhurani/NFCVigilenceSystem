package com.example.dell.vigilance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dell.vigilance.TableData.Tableinfo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Databaseop extends SQLiteOpenHelper {

    public String CREATE_QUERY = "create table " + Tableinfo.TABLE_NAME + "(" + Tableinfo.ROUTE_NAME + " VARCHAR2,"+ Tableinfo.COMPNM + " VARCHAR2);";
    public String CREATE_QUERY1 = "create table " + TableData.Tableinfo.TABLE_NAME1 + "(" + TableData.Tableinfo.TAG_NAME +" VARCHAR2(2000));";
    public String CREATE_QUERY2="create table "+Tableinfo.TABLE_NAME2+"("+Tableinfo.R_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Tableinfo.USER_NAME+ " VARCHAR2,"+Tableinfo.USER_RANK+" VARCHAR2,"+Tableinfo.R_NAME+" VARCHAR2,"+Tableinfo.STRTTIME+"  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,"+Tableinfo.ENDTIME+"  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,"+Tableinfo.DATE+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,"+Tableinfo.REPORT+" Varchar2);";
    public String CREATE_QUERY4="create table "+Tableinfo.TABLE_NAME4+"("+Tableinfo.R_ID2+" NUMBER ,"+Tableinfo.TAG_NAME1+" VARCHAR2,"+Tableinfo.STATUS+" VARCHAR2,"+Tableinfo.TIME+"  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);";
    public String CREATE_QUERY3 = "create table " + Tableinfo.ADMIN_INFO + "(" + Tableinfo.ADMIN_NAME +" VARCHAR2,"+Tableinfo.ADMIN_PASS+" VARCHAR2);";
    //public String CREATE_QUERY5 ="create sequence "+Tableinfo.R_ID+"_seq start with 1 increment by 1;";
    public static final int database_version = 1;

    public Databaseop(Context context) {
        super(context, Tableinfo.DATABASE_NAME, null, database_version);
        Log.d("database operations", "database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY1);
        db.execSQL(CREATE_QUERY2);
        db.execSQL(CREATE_QUERY3);
        db.execSQL(CREATE_QUERY4);
      //  db.execSQL(CREATE_QUERY5);
       // db.execSQL("alter session set time_zone='+5:30';");
        db.execSQL("insert into "+Tableinfo.TABLE_NAME1+" values('default')");
        db.execSQL("insert into "+Tableinfo.TABLE_NAME+" values('default','default')");
        db.execSQL("insert into "+Tableinfo.ADMIN_INFO+" values('ADMIN','PASSWORD');");

        Log.d("database operations", "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }
    public void deletefromroute(Databaseop dp,String routename){
        String selection=Tableinfo.ROUTE_NAME+" LIKE ? ";
        String args[]={routename};
        SQLiteDatabase db=dp.getWritableDatabase();
        db.delete(Tableinfo.TABLE_NAME,selection,args);
        Log.d("Database Operations","One row deleted");
    }

    public void putinfo(Databaseop dp, String routename,  String capacity) {
        SQLiteDatabase db = dp.getWritableDatabase();
        {

            ContentValues cv = new ContentValues();
            cv.put(Tableinfo.ROUTE_NAME, routename);
            //cv.put(Tableinfo.ROUTE_CAP, routecap);
            cv.put(Tableinfo.COMPNM, capacity);
            db.insert(Tableinfo.TABLE_NAME, null, cv);
            Log.d("database operations", "one row inserted");
        }
    }
    public void reports2(Databaseop dp, String rid,String cmp) {
        SQLiteDatabase db = dp.getWritableDatabase();
        {
            ContentValues cv = new ContentValues();
            cv.put(Tableinfo.R_ID2, rid);
            //cv.put(Tableinfo.ROUTE_CAP, routecap);
            long milliseconds1=System.currentTimeMillis();
            SimpleDateFormat sdf1=new SimpleDateFormat("HH-mm");
            Date result2=new Date(milliseconds1);
            String result3=sdf1.format(result2);
            cv.put(Tableinfo.TIME,"null");
            cv.put(Tableinfo.STATUS,"not visited");
            cv.put(Tableinfo.TAG_NAME1,cmp);
            db.insert(Tableinfo.TABLE_NAME4, null, cv);
            Log.d("database operations", "one row inserted");
        }
    }

    public void reports(Databaseop dp, String uname, String urank, String route){
        SQLiteDatabase db = dp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Tableinfo.USER_NAME, uname);
        //cv.put(Tableinfo.R_ID,"rid_seq.nextval");
        cv.put(Tableinfo.USER_RANK, urank);
        cv.put(Tableinfo.R_NAME,route);
       cv.put(Tableinfo.REPORT,"null");
        //cv.put(Tableinfo.TAG_NAME1,compnm );
        long milliseconds=System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        Date result=new Date(milliseconds);
        String result1=sdf.format(result);
        cv.put(Tableinfo.DATE, String.valueOf(result1));
        long milliseconds1=System.currentTimeMillis();
        SimpleDateFormat sdf1=new SimpleDateFormat("HH-mm");
        Date result2=new Date(milliseconds1);
        String result3=sdf1.format(result2);
        cv.put(Tableinfo.STRTTIME, String.valueOf(result3));
        cv.put(Tableinfo.ENDTIME,"null");
        db.insert(Tableinfo.TABLE_NAME2, null, cv);
        Log.d("database operations", "one row inserted");
    }
    public void repoupdt(Databaseop dp,String cname){
        SQLiteDatabase db=dp.getWritableDatabase();
        String selection=Tableinfo.TAG_NAME1+" LIKE ? ";
        String args[]={cname};
        ContentValues cv = new ContentValues();
        cv.put(Tableinfo.STATUS,"visited");
        long milliseconds=System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        Date result=new Date(milliseconds);
        String result1=sdf.format(result);
        //cv.put(Tableinfo.DATE, String.valueOf(result1));
        long milliseconds1=System.currentTimeMillis();
        SimpleDateFormat sdf1=new SimpleDateFormat("HH-mm");
        Date result3=new Date(milliseconds1);
        String result4=sdf1.format(result3);
        cv.put(Tableinfo.TIME,result4);
        db.update(Tableinfo.TABLE_NAME4,cv,selection,args);
       // db.execSQL("update "+Tableinfo.TABLE_NAME2+" set "+Tableinfo.STATUS+"='visited', "+Tableinfo.TSTAMP+"=current_timestamp where "+Tableinfo.TAG_NAME1+"='"+cname+"';");
        Log.d("Database operations","One row updated");


    }
    public void admupdt(Databaseop dp,String pwd,String npwd){
        SQLiteDatabase db=dp.getWritableDatabase();
        String selection=Tableinfo.ADMIN_PASS+" LIKE ? ";
        String args[]={pwd};
        ContentValues cv = new ContentValues();
        cv.put(Tableinfo.ADMIN_PASS,npwd);
        db.update(Tableinfo.ADMIN_INFO,cv,selection,args);
        Log.d("Database Operations","One row updated");


    }
    public void tagupt(Databaseop dp,String name,String nname){
        SQLiteDatabase db=dp.getWritableDatabase();
        String selection=Tableinfo.TAG_NAME+" LIKE ? ";
        String args[]={name};
        ContentValues cv = new ContentValues();
        cv.put(Tableinfo.TAG_NAME,nname);
        db.update(Tableinfo.TABLE_NAME1,cv,selection,args);
        Log.d("Database Operations","One row updated");


    }


    public void updateinfo(Databaseop dp,String routename,String compname){
        SQLiteDatabase db=dp.getWritableDatabase();
        String selection=Tableinfo.ROUTE_NAME+" LIKE ? ";
        String args[]={routename};
        ContentValues cv = new ContentValues();
        cv.put(Tableinfo.COMPNM, compname);
        db.update(Tableinfo.TABLE_NAME,cv,selection,args);
        Log.d("Database Operations","One row updated");
    }
    public void updatecomp(Databaseop dp,String rname,String pre,String value){
        SQLiteDatabase db=dp.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Tableinfo.COMPNM,pre+","+value);
        String selection=Tableinfo.ROUTE_NAME+" LIKE ? " ;
        String args[]={rname};
        db.update(Tableinfo.TABLE_NAME,cv,selection,args);
       //db.execSQL("update "+Tableinfo.TABLE_NAME+" set "+Tableinfo.COMPNM+"="+Tableinfo.COMPNM+"+'"+value+"' where "+Tableinfo.ROUTE_NAME+"='"+rname+"';");
        Log.d("Database Operations","One row updated");
    }

    public void updatereport(Databaseop dp,String rid,int pre,int value){
        SQLiteDatabase db=dp.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Tableinfo.REPORT,pre+"/"+value);
        String selection=Tableinfo.R_ID+" LIKE ? " ;
        String args[]={rid};
        db.update(Tableinfo.TABLE_NAME2,cv,selection,args);
        //db.execSQL("update "+Tableinfo.TABLE_NAME+" set "+Tableinfo.COMPNM+"="+Tableinfo.COMPNM+"+'"+value+"' where "+Tableinfo.ROUTE_NAME+"='"+rname+"';");
        Log.d("Database Operations","One row updated");
    }

    public void updatename(Databaseop dp,String routename,String rname){
        SQLiteDatabase db=dp.getWritableDatabase();
        String selection=Tableinfo.ROUTE_NAME+" LIKE ? ";
        String args[]={routename};
        ContentValues cv = new ContentValues();
        cv.put(Tableinfo.ROUTE_NAME, rname);
        db.update(Tableinfo.TABLE_NAME,cv,selection,args);
        Log.d("Database Operations","One row updated");
    }
    public void updateendtime(Databaseop dp,String rid){
        SQLiteDatabase db=dp.getWritableDatabase();
        String selection=Tableinfo.R_ID+" LIKE ? ";
        String args[]={rid};
        ContentValues cv = new ContentValues();
        long milliseconds1=System.currentTimeMillis();
        SimpleDateFormat sdf1=new SimpleDateFormat("HH-mm");
        Date result3=new Date(milliseconds1);
        String result4=sdf1.format(result3);
        cv.put(Tableinfo.ENDTIME,result4);
        db.update(Tableinfo.TABLE_NAME2,cv,selection,args);
        Log.d("Database Operations","One row updated");
    }

    public Cursor getListContents(Databaseop dp,String rname){
        SQLiteDatabase db = dp.getWritableDatabase();
        String[] coloumns={Tableinfo.COMPNM};
        String selection=Tableinfo.ROUTE_NAME+" LIKE ? ";
        String args[]={rname};
        Cursor data = db.query(Tableinfo.TABLE_NAME,coloumns,selection,args,null,null,null);
        return data;


    }
    public Cursor getstatcnt(int rid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT COUNT("+Tableinfo.STATUS+") FROM " + Tableinfo.TABLE_NAME4+" WHERE "+Tableinfo.STATUS+"='visited' AND "+Tableinfo.R_ID2+"="+rid,null);
        return data;


    }
    public Cursor getrouteid() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select "+Tableinfo.R_ID+" from "+Tableinfo.TABLE_NAME2,null);
        return data;
    }
    public Cursor getroutesrepo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT DISTINCT "+Tableinfo.R_NAME+" FROM " + Tableinfo.TABLE_NAME2,null);
        return data;


    }

    public Cursor getlogin(Databaseop dp){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] coloumns={Tableinfo.ADMIN_NAME,Tableinfo.ADMIN_PASS};
        Cursor data=db.query(Tableinfo.ADMIN_INFO,coloumns,null,null,null,null,null);
        return data;


    }
    public Cursor getupd(Databaseop dp){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] coloumns={Tableinfo.ADMIN_PASS};
        Cursor data=db.query(Tableinfo.ADMIN_INFO,coloumns,null,null,null,null,null);
        return data;


    }

    public Cursor getrepo(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+Tableinfo.R_ID+","+Tableinfo.R_NAME+","+Tableinfo.STRTTIME+","+Tableinfo.REPORT+" FROM " + Tableinfo.TABLE_NAME2+" WHERE "+Tableinfo.DATE+"='"+date+"' ORDER BY "+Tableinfo.R_ID+" DESC",null);
        return data;


    }
    public Cursor getrepo1(String rname){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+Tableinfo.R_ID+","+Tableinfo.DATE+","+Tableinfo.STRTTIME+","+Tableinfo.USER_NAME+","+Tableinfo.USER_RANK+" FROM " + Tableinfo.TABLE_NAME2+" WHERE "+Tableinfo.R_NAME+"='"+rname+"' ORDER BY "+Tableinfo.R_ID+" DESC",null);
        return data;


    }

    public Cursor getnames(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT DISTINCT "+Tableinfo.USER_NAME+" FROM " + Tableinfo.TABLE_NAME2,null);
        return data;


    }
    public Cursor getrepots(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+Tableinfo.TAG_NAME1+","+Tableinfo.STATUS+","+Tableinfo.TIME+" FROM " + Tableinfo.TABLE_NAME4+" WHERE "+Tableinfo.R_ID2+"="+id,null);
        return data;


    }
    public Cursor getroutes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+Tableinfo.ROUTE_NAME+" FROM " + Tableinfo.TABLE_NAME,null);
        return data;


    }
    public void puttaginfo(Databaseop dp, String tagname) {
        SQLiteDatabase db = dp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.Tableinfo.TAG_NAME, tagname);
        db.insert(TableData.Tableinfo.TABLE_NAME1, null, cv);
        Log.d("database operations", "one row inserted");
    }

    public Cursor gettagContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] coloumns={Tableinfo.TAG_NAME};
        Cursor data=db.query(Tableinfo.TABLE_NAME1,coloumns,null,null,null,null,Tableinfo.TAG_NAME);

        //Cursor data = db.rawQuery("SELECT * FROM " + TableData.Tableinfo.TABLE_NAME1+" order by "+Tableinfo.TAG_NAME,null);
        return data;
    }

    public Cursor getnrse(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+Tableinfo.USER_NAME+","+Tableinfo.USER_RANK+","+Tableinfo.STRTTIME+","+Tableinfo.ENDTIME+","+Tableinfo.REPORT+" FROM " + TableData.Tableinfo.TABLE_NAME2+ " where "+Tableinfo.R_ID+"="+id,null);
        return data;
    }
    public Cursor getallrepo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TableData.Tableinfo.TABLE_NAME2+ " ORDER BY "+Tableinfo.R_ID+" DESC",null);
        return data;
    }

    public void deletefromtag(Databaseop dp,String tagname){
        String selection=Tableinfo.TAG_NAME+" LIKE ? ";
        String args[]={tagname};
        SQLiteDatabase db=dp.getWritableDatabase();
        db.delete(Tableinfo.TABLE_NAME1,selection,args);
        Log.d("Database Operations","One row deleted");
    }

}




