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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class changepass extends AppCompatActivity {
EditText e1,e2,e3;
    Button b1;
    Cursor data;
    Databaseop dp;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        e1=(EditText)findViewById(R.id.editText8);
        e2=(EditText)findViewById(R.id.editText);
        e3=(EditText)findViewById(R.id.editText1);
        b1=(Button)findViewById(R.id.button25);
        e1.setFilters(new InputFilter[]{EMOJI_FILTER});
        e1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        e2.setFilters(new InputFilter[]{EMOJI_FILTER});
        e2.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        e3.setFilters(new InputFilter[]{EMOJI_FILTER});
        e3.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(changepass.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                        .setMessage("Are you sure you want to change password?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String cupwd=e1.getText().toString();
                                String npwd=e2.getText().toString();
                                String rnpwd=e3.getText().toString();
                                Databaseop dp=new Databaseop(ctx);
                                Cursor data=dp.getupd(dp);
                                data.moveToFirst();
                                if(cupwd.equals(data.getString(0))&&npwd.equals(rnpwd)){
                                    dp = new Databaseop(ctx);
                                    dp.admupdt(dp,cupwd,npwd);
                                    e1.setText("");
                                    e2.setText("");
                                    e3.setText("");
                                    Toast.makeText(changepass.this,"password changed",Toast.LENGTH_LONG).show();
                                    Intent i=new Intent(changepass.this,Main2Activity.class);
                                    startActivity(i);
                                }
                                else if(!cupwd.equals(data.getString(0)))
                                {   e1.setText("");
                                    e2.setText("");
                                    e3.setText("");

                                    Toast.makeText(changepass.this,"Wrong Password entered",Toast.LENGTH_LONG).show();}
                                else{  e1.setText("");
                                    e2.setText("");
                                    e3.setText("");
                                    Toast.makeText(changepass.this,"passwords dont match",Toast.LENGTH_SHORT).show();
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
                    e1.setHint("Enter Current Password");
            }
        });

        e2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e2.setHint("");
                else
                    e2.setHint("Enter New Password");
            }
        });
        e3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e3.setHint("");
                else
                    e3.setHint("Re-enter new password");
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
