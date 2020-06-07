package com.example.dell.vigilance;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class repobyround extends AppCompatActivity implements OnItemClickListener {
    ListView listView;
    ListViewAdapter2 lviewAdapter;
    String[] outlet_id, outlet_date, outlet_strt, outlet_rank,outlet_name;
    Databaseop mydb;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repobyround);

        listView = (ListView) findViewById(R.id.listview);
        Intent i = getIntent();
        //  tableLayout=(TableLayout)findViewById(R.id.table);
        String date = i.getStringExtra("rname");

        mydb = new Databaseop(repobyround.this);
        Cursor data = mydb.getrepo1(date);
        outlet_id = new String[data.getCount()];
        int a = 0;
        while (data.moveToNext()) {
            String id = data.getString(0);
            outlet_id[a] = id;
            a++;
        }

        Cursor dat1 = mydb.getrepo1(date);
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
        outlet_date = new String[dat1.getCount()];
        int b = 0;
        while (dat1.moveToNext()) {
            String rname = dat1.getString(1);
            outlet_date[b] = rname;
            b++;


        }

        Cursor dat2 = mydb.getrepo1(date);
        outlet_strt = new String[dat2.getCount()];
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
        int c = 0;
        while (dat2.moveToNext()) {
            String sttime = dat2.getString(2);

            outlet_strt[c] = sttime;
            c++;

        }

        Cursor dat3 = mydb.getrepo1(date);
        //Toast.makeText(results.this,""+user+"  "+route+"   "+rank,Toast.LENGTH_SHORT).show();
        outlet_name = new String[dat3.getCount()];
        outlet_rank = new String[dat3.getCount()];
        int d = 0,e=0;
        while (dat3.moveToNext()) {
            String repot = dat3.getString(3);
            String rank=dat3.getString(4);
            outlet_name[d] = repot;
            outlet_rank[e]=rank;


            d++;
            e++;

        }



        lviewAdapter = new ListViewAdapter2(this, outlet_id, outlet_date, outlet_strt, outlet_name,outlet_rank);
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


