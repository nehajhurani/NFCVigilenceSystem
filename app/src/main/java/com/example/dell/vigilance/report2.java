package com.example.dell.vigilance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;





import java.util.ArrayList;

public class report2 extends AppCompatActivity implements OnItemClickListener {
    ListView listView;
    ListViewAdapter lviewAdapter;
    String[] outlet_id, outlet_rname, outlet_strt, outlet_repot;
    Databaseop mydb;
    Context context = this;
    // ArrayAdapter listAdapter;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2);
        listView = (ListView) findViewById(R.id.listview);
        Intent i = getIntent();
        //  tableLayout=(TableLayout)findViewById(R.id.table);
        String date = i.getStringExtra("date");

        mydb = new Databaseop(report2.this);
        Cursor data = mydb.getrepo(date);
        outlet_id = new String[data.getCount()];
            int a = 0;
            while (data.moveToNext()) {
                String id = data.getString(0);
                outlet_id[a] = id;
                a++;
            }

            Cursor dat1 = mydb.getrepo(date);
            //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
                outlet_rname = new String[dat1.getCount()];
                int b = 0;
                while (dat1.moveToNext()) {
                    String rname = dat1.getString(1);
                    outlet_rname[b] = rname;
                    b++;


                }

                Cursor dat2 = mydb.getrepo(date);
                 outlet_strt = new String[dat2.getCount()];
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
                    int c = 0;
                    while (dat2.moveToNext()) {
                        String sttime = dat2.getString(2);

                        outlet_strt[c] = sttime;
                        c++;

                    }

                    Cursor dat3 = mydb.getrepo(date);
                     //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
                      outlet_repot = new String[dat3.getCount()];
                        int d = 0;
                        while (dat3.moveToNext()) {
                            String repot = dat3.getString(3);
                            outlet_repot[d] = repot;

                            d++;


                        }



                        lviewAdapter = new ListViewAdapter(this, outlet_id, outlet_rname, outlet_strt, outlet_repot);
                       listView.setAdapter(lviewAdapter);
                        listView.setOnItemClickListener(this);
    }


    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
String ida=outlet_id[position];
       Intent i=new Intent(this,results.class);
        i.putExtra("id",ida);
        startActivity(i);
        // Toast.makeText(this,""+ida,Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, " " + outlet_id[position] + "" + outlet_rname[position] + "" + outlet_strt[position] + "" + outlet_repot[position], Toast.LENGTH_SHORT).show();
    }
}
