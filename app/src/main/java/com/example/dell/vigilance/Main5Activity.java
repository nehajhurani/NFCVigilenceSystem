package com.example.dell.vigilance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main5Activity extends AppCompatActivity {
    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        b1=(Button)findViewById(R.id.button9);
        b2=(Button)findViewById(R.id.button10);
        //b3=(Button)findViewById(R.id.button11);
        //b4=(Button)findViewById(R.id.button21);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main5Activity.this,Main9Activity.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main5Activity.this,Main11Activity.class);
                startActivity(i);
            }
        });




    }

   @Override
    public void onBackPressed() {
        Intent i=new Intent(Main5Activity.this,Main3Activity.class);
                startActivity(i);
    }





}
