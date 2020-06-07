package com.example.dell.vigilance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class Main10Activity extends AppCompatActivity {
    Button b1,b2;
    ListView l1;
    TextView t1;
    ListAdapter listAdapter;
    ArrayList<String> theList;
    Databaseop mydb;
    Context ctx=this;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        t1=(TextView)findViewById(R.id.textView) ;


        b1 = (Button) findViewById(R.id.button8);
        //b2 = (Button) findViewById(R.id.button25);
        l1 = (ListView) findViewById(R.id.listview3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main10Activity.this,Main5Activity.class);
                startActivity(i);
            }
        });
        mydb = new Databaseop(Main10Activity.this);
        theList = new ArrayList<String>();
        Cursor data = mydb.getroutes();

        if (data.getCount() == 0) {
            Toast.makeText(Main10Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(0));
                listAdapter = new ArrayAdapter();
                l1.setAdapter(listAdapter);


            }


        }
       /* b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main10Activity.this,Main5Activity.class);
                startActivity(i);
            }
        });*/
    }

    class ArrayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return theList.size();
        }

        @Override
        public String getItem(int position) {
            return theList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
                           convertView = View.inflate(Main10Activity.this,
                        R.layout.item, null);
             ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.name.setText(theList.get(position));
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(Main10Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    theList.get(position);
                                    String value = (String) l1.getItemAtPosition(position);
                                    Databaseop dp = new Databaseop(ctx);
                                    dp.deletefromroute(dp, value);
                                    theList.remove(position);
                                            l1.setAdapter(listAdapter);
                                       }
                            }).setNegativeButton("No", null).show();

                }
            });

            return convertView;
        }

        class ViewHolder {
            private TextView name;
            private Button del;
            public ViewHolder(View view) {

                name = (TextView) view.findViewById(R.id.textView);
                name.setTextColor(Color.WHITE);
                del = (Button) view.findViewById(R.id.button);
                view.setTag(this);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Main10Activity.this,Main5Activity.class);
                startActivity(i);
    }

}
