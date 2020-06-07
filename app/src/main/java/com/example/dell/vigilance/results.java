package com.example.dell.vigilance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class results extends AppCompatActivity {
    Databaseop mydb;
    String id;
    TextView t1;
   // ListView listView,t1,t2;
    Context context=this;
    TableLayout tableLayout;
    //ArrayList<String> theList,the1,the2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        t1 = (TextView) findViewById(R.id.t1);
        mydb = new Databaseop(results.this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Cursor data3 = mydb.getnrse(id);
        while (data3.moveToNext()) {
                String name = data3.getString(0);
                String rank = data3.getString(1);
                String start = data3.getString(2);
                String end = data3.getString(3);
            String report=data3.getString(4);

                t1.setText(" Name=" + name + "\n Rank=" + rank + "\n Start time=" + start + " Endtime=" + end+"\n Report-"+report);
            }


            tableLayout = (TableLayout) findViewById(R.id.table);

            TableRow rowHeader = new TableRow(context);
            rowHeader.setBackgroundColor(Color.BLACK);
            rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] headerText = {"COMP", "TIME","STATUS"};
            for (String c : headerText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.LEFT);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(c);
                rowHeader.addView(tv);
            }
            tableLayout.addView(rowHeader);

            mydb = new Databaseop(results.this);

            Cursor data = mydb.getrepots(id);
            //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
            if (data.getCount() == 0) {
                Toast.makeText(results.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
            } else {

                while (data.moveToNext()) {
                    String outlet_id = data.getString(data.getColumnIndex("comp"));
                    String outlet_name = data.getString(data.getColumnIndex("status"));
                    String outlet_type = data.getString(data.getColumnIndex("time"));




                    // dara rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {outlet_id + "",  outlet_type + "", outlet_name+""};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.LEFT);
                        tv.setTextSize(18);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        tv.setTextColor(Color.WHITE);

                        if (outlet_name.equals("visited")) {
                            tv.setTextColor(Color.GREEN);
                        } else if (outlet_name.equals("not visited")) {
                            tv.setTextColor(Color.RED);
                        }

                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
        }
    }



