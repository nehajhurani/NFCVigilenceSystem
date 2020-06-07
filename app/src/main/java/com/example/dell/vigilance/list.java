package com.example.dell.vigilance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class list extends AppCompatActivity {
    TextView t1;
    ListView l1;
    Databaseop mydb;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
          t1 = (TextView) findViewById(R.id.textView);
          l1 = (ListView) findViewById(R.id.list);
          Databaseop dp = new Databaseop(ctx);
          final ArrayList<String> theList = new ArrayList<>();
        mydb = new Databaseop(this);
        Cursor data = mydb.getroutesrepo();
        if (data.getCount() == 0) {
            Toast.makeText(list.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(0));
                final ListAdapter listAdapter = new ArrayAdapter<String>(list.this, android.R.layout.simple_list_item_1, theList) {
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
                        Intent i = new Intent(list.this,repobyround.class);
                        i.putExtra("rname", string);
                        startActivity(i);

                    }
                });
            }
        }
    }}