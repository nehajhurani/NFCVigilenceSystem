package com.example.dell.vigilance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main17Activity extends AppCompatActivity {

    Button b1,b2;
    TextView t1;
    String tagname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main17);
        b1=(Button)findViewById(R.id.button22);
        b2=(Button)findViewById(R.id.button12);
    t1=(TextView)findViewById(R.id.textView1) ;
        Intent i= getIntent();
        tagname = i.getStringExtra("name");
        t1.setText(tagname);

        b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(Main17Activity.this,Main14Activity.class);
            i.putExtra("name",tagname);
            Toast.makeText(Main17Activity.this,""+tagname,Toast.LENGTH_LONG).show();
            startActivity(i);
        }
    });
      /*  b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Main17Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Databaseop dp = new Databaseop(ctx);
                                dp.deletefromtag(dp,tagname );
                                Intent i=new Intent(Main17Activity.this,editlist.class);
                                startActivity(i);
                            }
                        }).setNegativeButton("No", null).show();
            }
        });*/
    }
}
