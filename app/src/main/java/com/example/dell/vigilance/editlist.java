package com.example.dell.vigilance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class editlist extends AppCompatActivity {
ListView l1;
Databaseop mydb;
    Context ctx=this;
   List<String> theList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlist);
    l1=(ListView)findViewById(R.id.list1);
        mydb = new Databaseop(editlist.this);
        theList = new ArrayList<String>();
        Cursor data = mydb.gettagContents();

        if (data.getCount() == 0) {
            Toast.makeText(editlist.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(0));
                final ListAdapter listAdapter = new ArrayAdapter<String>(editlist.this, android.R.layout.simple_list_item_1, theList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.WHITE);
                        return view;
                    }
                };
                l1.setAdapter(listAdapter);
                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String string = String.valueOf(listAdapter.getItem(position));
                        Intent i = new Intent(editlist.this, Main17Activity.class);
                        i.putExtra("name", string);
                        startActivity(i);

                    }
                });

            }
        }}}