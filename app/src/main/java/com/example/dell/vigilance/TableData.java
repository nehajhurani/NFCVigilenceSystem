package com.example.dell.vigilance;

import android.provider.BaseColumns;

/**
 * Created by DELL on 6/2/2017.
 */
public class TableData {
    public TableData(){


    }
    public static abstract class Tableinfo implements BaseColumns
    {
        public static final String ROUTE_NAME="rname";
        public static final String ROUTE_CAP="rcap";
        public static final String ADMIN_NAME="aname";
        public static final String ADMIN_PASS="apass";
        public static final String ADMIN_INFO="ainfo";
        public static final String COMPNM="cname";
        public static final String DATABASE_NAME="mydb";
        public static final String TABLE_NAME="routes";
        public static final String TABLE_NAME1="tags";
        public static final String TAG_NAME="tagname";
        public static final String TABLE_NAME2="reports";
        public static final String TAG_NAME1="comp";
        public static final String USER_NAME="uname";
        public static final String USER_RANK="urank";
        public static final String R_NAME="rtname";
        public static final String STRTTIME="strtt";
        public static final String ENDTIME="endt";
        public static final String STATUS="status";
        public static final String DATE="sqltime";
        public static final String TIME="time";
        public static final String REPORT="report";
        public static final String R_ID="rid";
        public static final String TABLE_NAME4="reports2";
        public static final String R_ID2="rid2";

    }
}
