package com.example.dell.vigilance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main13Activity extends AppCompatActivity {
    Button b1,b2,b3;
    Context ctx=this;
    String tagname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);
        b1 = (Button) findViewById(R.id.button20);
        b2 = (Button) findViewById(R.id.button22);
        b3 = (Button) findViewById(R.id.button12);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main13Activity.this, Main4Activity.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main13Activity.this, Main14Activity.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main13Activity.this, Main16Activity.class);
                startActivity(i);

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Main13Activity.this, Main3Activity.class);
                startActivity(i);

    }

    }

