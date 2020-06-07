package com.example.dell.vigilance;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
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


public class Main11Activity extends AppCompatActivity {
    TextView t1;
    Button b1;
    ArrayAdapter listAdapter;
    ListView listView;
    Context ctx = this;
    Databaseop mydb;
    String[] array;
    ArrayList<String> theList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        t1 = (TextView) findViewById(R.id.textView2);
        //b1 = (Button) findViewById(R.id.button29);
       /* b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main11Activity.this,Main5Activity.class);
                startActivity(i);


            }
        });*/
        listView = (ListView) findViewById(R.id.listview);
        Databaseop dp = new Databaseop(ctx);
        mydb = new Databaseop(Main11Activity.this);
        Cursor date=mydb.getroutes();
        array = new String[date.getCount()];
        int i = 0;
        while(date.moveToNext()){
            String uname = date.getString(0);
            array[i] = uname;
            i++;
        }
               listAdapter = new ArrayAdapter<String>(Main11Activity.this,android.R.layout.simple_list_item_1, array){
                    @Override
                public View getView(int position , View convertView, ViewGroup parent){
                    View view=super.getView(position, convertView, parent);
                    TextView textView=(TextView)view.findViewById(android.R.id.text1);
                    textView.setTextColor(Color.WHITE);
                    return view;
                }
                };
                listView.setAdapter(listAdapter);
                 registerForContextMenu(listView);
                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String string= String.valueOf(listAdapter.getItem(position));
                        Intent i=new Intent(Main11Activity.this,Main12Activity.class);
                        i.putExtra("name",string);
                        startActivity(i);

                    }
                });

        }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "RENAME");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "DELETE");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        //String[] menuItems = getResources().getStringArray(R.array.menu);
       // String menuItemName = menuItems[menuItemIndex];
        final String listItemName = array[info.position];
        if(item.getTitle()=="RENAME"){
            Intent i=new Intent(Main11Activity.this,Main1Activity.class);
            i.putExtra("name",listItemName);
             startActivity(i);
        }
        else if(item.getTitle()=="DELETE"){
            new AlertDialog.Builder(Main11Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Databaseop dp = new Databaseop(ctx);
                            dp.deletefromroute(dp,listItemName);
                            mydb = new Databaseop(Main11Activity.this);
                            Cursor date=mydb.getroutes();
                            array = new String[date.getCount()];
                            int i = 0;
                            while(date.moveToNext()){
                                String uname = date.getString(0);
                                array[i] = uname;
                                i++;
                            }
                            listAdapter = new ArrayAdapter<String>(Main11Activity.this,android.R.layout.simple_list_item_1, array){
                                @Override
                                public View getView(int position , View convertView, ViewGroup parent){
                                    View view=super.getView(position, convertView, parent);
                                    TextView textView=(TextView)view.findViewById(android.R.id.text1);
                                    textView.setTextColor(Color.WHITE);
                                    return view;
                                }
                            };
                            listView.setAdapter(listAdapter);
                            registerForContextMenu(listView);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    String string= String.valueOf(listAdapter.getItem(position));
                                    Intent i=new Intent(Main11Activity.this,Main12Activity.class);
                                    i.putExtra("name",string);
                                    startActivity(i);

                                }
                            });

                        }
                    }).setNegativeButton("No", null).show();



        }else{
            return false;
        }
        return true;
    }





    @Override
    public void onBackPressed() {
        Intent i=new Intent(Main11Activity.this,Main5Activity.class);
                startActivity(i);

    }
}



