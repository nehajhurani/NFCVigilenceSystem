package com.example.dell.vigilance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main9Activity extends AppCompatActivity {
    Button b1;
    String rname;
    TextView t1;
    int flag;
    EditText e1,e2;
    Databaseop mydb;Context ctx=this;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        t1 = (TextView) findViewById(R.id.textView);
        e1 = (EditText) findViewById(R.id.editText6);
        e1.setFilters(new InputFilter[]{EMOJI_FILTER});
        e1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        //e2 = (EditText) findViewById(R.id.editText7);
        b1=(Button) findViewById(R.id.button16);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rname = e1.getText().toString();
                final String nrname = e1.getText().toString();
                final Databaseop dp = new Databaseop(ctx);
                mydb = new Databaseop(Main9Activity.this);
                Cursor date = mydb.getroutes();
                String[] array = new String[date.getCount()];
                int i = 0;
                while (date.moveToNext()) {
                    String uname = date.getString(0);
                    array[i] = uname;
                    i++;
                }

                for (int j = 0; j <= array.length - 1; j++) {
                    if (nrname.equals(array[j])) {
                        Toast.makeText(Main9Activity.this, "Route Name already exists", Toast.LENGTH_SHORT).show();
                        flag = 1;
                        break;
                    }
                    else if(e1.getText().toString().equals("")){
                        Toast.makeText(Main9Activity.this, "Enter Route Name", Toast.LENGTH_SHORT).show();
                        flag = 1;
                        break;
                    }

                    else {
                        flag = 0;
                    }
                }

                if (flag == 0) {
                    Intent ip = new Intent(Main9Activity.this, Main8Activity.class);
                    ip.putExtra("name", rname);
                    startActivity(ip);
                }
            }

        });


       e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e1.setHint("");
                else
                    e1.setHint("ROUND ROUTE NAME");
            }
        });
        /*e2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e2.setHint("");
                else
                    e2.setHint("NUMBER OF COMPARTMENTS");
            }
        });*/

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
        Intent i=new Intent(Main9Activity.this,Main5Activity.class);
                    startActivity(i);

    }
}

