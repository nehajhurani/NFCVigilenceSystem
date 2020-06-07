package com.example.dell.vigilance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class allrepo extends AppCompatActivity /*implements OnItemClickListener */ {
    ListView listView;
    ListViewAdapter1 lviewAdapter;
    String ide, uname, urank, rname, strt, end, date, repot;
    Databaseop mydb;
    Context context = this;
    // ArrayAdapter listAdapter;
       TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrepo);
//        listView = (ListView) findViewById(R.id.listview);
        tableLayout = (TableLayout) findViewById(R.id.table);
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.BLACK);
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Id", "Name", "Rank", "Route", " StartTime  ", "  EndTime  ", " Report "};
        for (String c1 : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.LEFT);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(20);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c1);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        mydb = new Databaseop(allrepo.this);

        Cursor data = mydb.getallrepo();
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
        if (data.getCount() == 0) {
            //Toast.makeText(report2.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {

            while (data.moveToNext()) {
                ide = data.getString(0);
                uname = data.getString(1);
                urank = data.getString(2);
                rname = data.getString(3);
                strt = data.getString(4);
                end = data.getString(5);
                date = data.getString(6);
                repot = data.getString(7);

         /*       mydb = new Databaseop(allrepo.this);
        Cursor data = mydb.getallrepo();
        ide = new String[data.getCount()];
        int a = 0;
        while (data.moveToNext()) {
            String ida = data.getString(0);
            ide[a] = ida;
            a++;
        }

        Cursor dat1 = mydb.getallrepo();
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
        uname = new String[dat1.getCount()];
        int b = 0;
        while (dat1.moveToNext()) {
            String name = dat1.getString(1);
            uname[b] = name;
            b++;


        }

        Cursor dat2 = mydb.getallrepo();
        urank = new String[dat2.getCount()];
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
        int c = 0;
        while (dat2.moveToNext()) {
            String rank = dat2.getString(2);

            urank[c] = rank;
            c++;

        }

        Cursor dat3 = mydb.getallrepo();
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
        rname = new String[dat3.getCount()];
        strt = new String[dat3.getCount()];
        end = new String[dat3.getCount()];
        date = new String[dat3.getCount()];
        repot = new String[dat3.getCount()];

        int d = 0, e = 0, f = 0, g = 0, h = 0;
        while (dat3.moveToNext()) {
            String route = dat3.getString(3);
            String start = dat3.getString(4);
            String end1 = dat3.getString(5);
            String date1 = dat3.getString(6);
            String report1 = dat3.getString(7);
            rname[d] = route;
            strt[e] = start;
            end[f] = end1;
            date[g] = date1;
            repot[h] = report1;
            d++;
            e++;
            f++;
            g++;
            h++;
        }
*/              final TableRow row = new TableRow(context);
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                //row.setClickable(true);
                final String[] colText = {"Date-"+ date + " "};
                for (String text : colText) {
                    final TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.LEFT);
                    tv.setTextSize(20);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setText(text);
                    tv.setClickable(true);
                    tv.setTextColor(Color.YELLOW);
           /* tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TableRow trow=(TableRow)v.getId();
                    String str=tv.getText().toString();
                    Toast.makeText(report2.this,""+str,Toast.LENGTH_SHORT).show();
                }
            });*/
                    row.addView(tv);
                }
                tableLayout.addView(row);

                final TableRow row1 = new TableRow(context);
                row1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                //row.setClickable(true);
                final String[] colText1 = {ide + " ", uname + " ", urank + " ", rname + " ", strt + " ", end + " ", repot + " "};
                for (String text : colText1) {
                    final TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.LEFT);
                    tv.setTextSize(20);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setText(text);
                    tv.setClickable(true);
                    tv.setTextColor(Color.WHITE);
           /* tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TableRow trow=(TableRow)v.getId();
                    String str=tv.getText().toString();
                    Toast.makeText(report2.this,""+str,Toast.LENGTH_SHORT).show();
                }
            });*/
                    row1.addView(tv);
                }
                tableLayout.addView(row1);

            }


        }
    }
/*
        lviewAdapter = new ListViewAdapter1(this,ide,uname,urank, rname, strt, end,date,repot);
        listView.setAdapter(lviewAdapter);
        listView.setOnItemClickListener(this);
    }


    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        String ida=ide[position];
        Intent i=new Intent(this,results.class);
        i.putExtra("id",ida);
        startActivity(i);
        // Toast.makeText(this,""+ida,Toast.LENGTH_SHORT).show();
        //  Toast.makeText(this, " " + outlet_id[position] + "" + outlet_rname[position] + "" + outlet_strt[position] + "" + outlet_repot[position], Toast.LENGTH_SHORT).show();
    }*/

    }