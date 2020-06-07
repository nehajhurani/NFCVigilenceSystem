package com.example.dell.vigilance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main6Activity extends AppCompatActivity {
    EditText e1, e2;
    Button b1, b2;
    Context ctx=this;
NfcAdapter nfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        e1 = (EditText) findViewById(R.id.editText4);
        //e2=(EditText)findViewById(R.id.editText5);
        e1.setFilters(new InputFilter[]{EMOJI_FILTER});
        e1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //e2.setFilters(new InputFilter[]{EMOJI_FILTER});
        //b2 = (Button) findViewById(R.id.button17);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            finish();
        }
        b1 = (Button) findViewById(R.id.button13);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ename = e1.getText().toString();
                String erank = dropdown.getSelectedItem().toString();
                if (ename.equals("")) {
                // && erank.equals("")) {
                    Toast.makeText(Main6Activity.this, "Field must not be empty", Toast.LENGTH_LONG).show();
                } else {
                    String rname = getIntent().getStringExtra("name");
                    Databaseop dp = new Databaseop(ctx);
                    dp.reports(dp,ename,erank,rname);
                    dp = new Databaseop(Main6Activity.this);

                            Cursor data1 = dp.getrouteid();
                            data1.moveToLast();
                            String theListcon = data1.getString(0);

                        //    Toast.makeText(Main6Activity.this, "" + theListcon, Toast.LENGTH_SHORT).show();
                            Intent ia = new Intent(Main6Activity.this, Main15Activity.class);

                            ia.putExtra("name", ename);
                            ia.putExtra("rname", rname);
                            ia.putExtra("rank", erank);
                            ia.putExtra("rid", theListcon);
                            startActivity(ia);


                }


            }
        });


        String[] items = new String[]{"RankA", "RankB", "RankC", "RankD", "RankE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        dropdown.setAdapter(adapter);
        dropdown.setSelection(0,true);
        View v=dropdown.getSelectedView();
        ((TextView)v).setTextColor(Color.WHITE);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)view).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main6Activity.this, MainActivity.class);
                startActivity(i);


            }
        });*/
        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e1.setHint("");
                else
                    e1.setHint("Enter name");
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
    @Override
    public void onBackPressed() {
         Intent i = new Intent(Main6Activity.this, MainActivity.class);
                startActivity(i);

    }






}


