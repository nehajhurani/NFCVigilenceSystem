package com.example.dell.vigilance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class reports extends AppCompatActivity {
 //   EditText e1,e2;
    Button b1;
    Databaseop mydb;
    Spinner s1;
    Context ctx=this;
    private DatePicker datePicker;
    private java.util.Calendar calendar;
    private TextView dateView;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        final Spinner dropdown = (Spinner) findViewById(R.id.spinner3);
        s1 = (Spinner) findViewById(R.id.spinner);
        Databaseop dp = new Databaseop(ctx);
        mydb = new Databaseop(reports.this);
        final ArrayList<String> theList = new ArrayList<>();
        Cursor data = mydb.getnames();
        if (data.getCount() == 0) {
            Toast.makeText(reports.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(0));
                final ListAdapter listAdapter = new ArrayAdapter<String>(reports.this, android.R.layout.simple_spinner_dropdown_item, theList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.WHITE);
                        return view;

                    }
                };
                s1.setAdapter((SpinnerAdapter) listAdapter);

            }
            b1 = (Button) findViewById(R.id.button24);
            dateView = (TextView) findViewById(R.id.textView11);
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDate(year, month, day);

        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = s1.getSelectedItem().toString();
                String rank = dropdown.getSelectedItem().toString();
                String date = dateView.getText().toString();
if(!rank.equals("Select Rank")) {
    Intent i = new Intent(reports.this, list.class);
    i.putExtra("name", name);
    i.putExtra("rank", rank);
    i.putExtra("date", date);
    startActivity(i);
}
else if(rank.equals("Select Rank"))Toast.makeText(reports.this,"Please Select a Rank",Toast.LENGTH_SHORT).show();
            }
        });
        String[] items = new String[]{"Select Rank","RankA", "RankB", "RankC", "RankD", "RankE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        dropdown.setAdapter(adapter);
        dropdown.setSelection(0, true);
        View v = dropdown.getSelectedView();
        ((TextView) v).setTextColor(Color.WHITE);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                View v = dropdown.getSelectedView();
                ((TextView) v).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       /* e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e1.setHint("");
                else
                    e1.setHint("name");
            }
        });*/
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
       // Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(formatDate(year, month, day));
    }

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(date);
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



