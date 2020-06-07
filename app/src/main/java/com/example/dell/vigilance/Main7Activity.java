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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class Main7Activity extends AppCompatActivity {
    TextView t1;

    ListView listView;
    Context ctx = this;
    Databaseop mydb;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        t1 = (TextView) findViewById(R.id.textView3);

        listView = (ListView) findViewById(R.id.listview4);
        Databaseop dp = new Databaseop(ctx);
        mydb = new Databaseop(Main7Activity.this);
        final ArrayList<String> theList = new ArrayList<>();
        Cursor data = mydb.getroutes();
        if (data.getCount() == 0) {
            Toast.makeText(Main7Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(0));
                final ListAdapter listAdapter = new ArrayAdapter<String>(Main7Activity.this,android.R.layout.simple_list_item_1, theList){
                    @Override
                    public View getView(int position , View convertView, ViewGroup parent){
                        View view=super.getView(position, convertView, parent);
                        TextView textView=(TextView)view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.WHITE);
                        return view;
                    }
                };
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String string= String.valueOf(listAdapter.getItem(position));
                        Intent intent=new Intent(Main7Activity.this,Main6Activity.class);
                        intent.putExtra("name",string);

                        startActivity(intent);

                    }
                });



            }

        }


    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Main7Activity.this, MainActivity.class);
                startActivity(i);

    }

}
