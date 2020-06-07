package com.example.dell.vigilance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Main18Activity extends AppCompatActivity {
Button b1,b3,b4;
    TextView t1;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main18);
        b3=(Button)findViewById(R.id.button11);
        b4=(Button)findViewById(R.id.button21);
        t1=(TextView)findViewById(R.id.textView5);
        b1=(Button)findViewById(R.id.button10);
        Intent i = getIntent();
        final String rname = i.getStringExtra("name");
        t1.setText(rname);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main18Activity.this,Main12Activity.class);
                i.putExtra("name",rname);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Main18Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = getIntent();
                                String rname = i.getStringExtra("name");
                                Databaseop dp = new Databaseop(ctx);
                                dp.deletefromroute(dp, rname);
                                Intent ia=new Intent(Main18Activity.this,Main5Activity.class);
                                startActivity(ia);                            }
                        }).setNegativeButton("No", null).show();



            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main18Activity.this,Main1Activity.class);
                i.putExtra("name",rname);
                startActivity(i);
            }
        });
    }
}
