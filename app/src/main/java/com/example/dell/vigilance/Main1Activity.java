package com.example.dell.vigilance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main1Activity extends AppCompatActivity {
Button b1;
    TextView t1;

    EditText e1;
    int flag;
    Context ctx = this;
    Databaseop mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        b1 = (Button) findViewById(R.id.button23);
        t1 = (TextView) findViewById(R.id.textView9);
        e1 = (EditText) findViewById(R.id.editText3);
        //e1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        e1.setFilters(new InputFilter[]{EMOJI_FILTER});
        e1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Intent ia = getIntent();
        final String rname = ia.getStringExtra("name");
        t1.setText(rname);


        b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(Main1Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                                    .setMessage("Are you sure you want to rename?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            final String nrname=e1.getText().toString();
                                            final Databaseop dp = new Databaseop(ctx);
                                            mydb = new Databaseop(Main1Activity.this);
                                            Cursor date=mydb.getroutes();
                                            String[] array = new String[date.getCount()];
                                            int i = 0;
                                            while(date.moveToNext()){
                                                String uname = date.getString(0);
                                                array[i] = uname;
                                                i++;
                                            }

                                            for (int j = 0; j <= array.length - 1; j++) {
                                                if (nrname.equals(array[j])) {
                                                    Toast.makeText(Main1Activity.this, "Route Name already exists", Toast.LENGTH_SHORT).show();
                                                    flag = 1;
                                                    break;
                                                } else {
                                                    flag = 0;
                                                }
                                            }


                                            if(flag==0){
                                                mydb.updatename(dp, rname, nrname);
                                                e1.setText("");
                                                Toast.makeText(Main1Activity.this, "route renamed", Toast.LENGTH_SHORT).show();
                                                Intent ia = new Intent(Main1Activity.this, Main5Activity.class);
                                                startActivity(ia);

                                            }


                                        }
                                    }).setNegativeButton("No", null).show();

                    }


                });
                e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus)
                            e1.setHint("");
                        else
                            e1.setHint("Enter COMPARTMENT name");
                    }
                });
            }
    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {

                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE) {
                    return "";
                }
            }
            return null;
        }
    };
        }


