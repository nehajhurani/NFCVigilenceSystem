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

public class Main2Activity extends AppCompatActivity {
    TextView t1;
    Button b1, b2;
    EditText e1, e2;
    Databaseop dp;
    Context ctx=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        t1 = (TextView) findViewById(R.id.textView);
        b1 = (Button) findViewById(R.id.button3);
        //b2 = (Button) findViewById(R.id.button27);

        e1 = (EditText) findViewById(R.id.editText);

        e1.setFilters(new InputFilter[]{EMOJI_FILTER});
        e1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        e2 = (EditText) findViewById(R.id.editText2);
        e2.setFilters(new InputFilter[]{EMOJI_FILTER});
        e2.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e1.getText().toString();
                String pwd = e2.getText().toString();
                Databaseop dp=new Databaseop(ctx);
                Cursor data=dp.getlogin(dp);
                data.moveToFirst();
                if (name.equals(data.getString(0)) && pwd.equals(data.getString(1))) {
                    Intent i = new Intent(Main2Activity.this, Main3Activity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Main2Activity.this, "Access Denied", Toast.LENGTH_LONG).show();
                }

            }
        });
        /*b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(i);
            }
        });*/

        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e1.setHint("");
                else
                    e1.setHint("Username");
            }
        });
        e2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e2.setHint("");
                else
                    e2.setHint("Password");
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
        Intent i = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(i);
    }

}


