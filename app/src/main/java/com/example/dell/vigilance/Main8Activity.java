package com.example.dell.vigilance;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main8Activity extends AppCompatActivity {

    TextView textViewInfo;
    Button b1, b2;
    private NfcAdapter nfcAdapter;
    Context ctx = this;
    NdefRecord ndefRecord;
    String tagcontent;
    EditText inputSearch;
    Databaseop mydb;
    ListView l1;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        //textViewInfo = (TextView) findViewById(R.id.textView6);
        b1 = (Button) findViewById(R.id.button17);
        b2 = (Button) findViewById(R.id.button15);
        l1 = (ListView) findViewById(R.id.listView2);

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

              /*  inputSearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        // When user changed the Text
                        Main8Activity.this.listAdapter.getFilter().filter(cs);

                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub

                    }
                });
            }*/

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    Intent i = new Intent(Main8Activity.this, WriteA.class);
                    startActivity(i);

                    /*String compname = textViewInfo.getText().toString();
                    if (compname.equals("")) {
                        Toast.makeText(Main8Activity.this, "Fields should not be EMPTY", Toast.LENGTH_LONG).show();
                    } else {
                        Databaseop dp = new Databaseop(ctx);
                        mydb = new Databaseop(Main8Activity.this);
                        mydb.puttaginfo(dp, compname);
                        mydb.gettagContents();

                        //populate an ArrayList<String> from the database and then view it
                        final ArrayList<String> theList = new ArrayList<>();
                        final Cursor data = mydb.gettagContents();
                        if (data.getCount() == 0) {
                           Toast tos= Toast.makeText(Main8Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG);
                            tos.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            tos.show();

                        } else {
                            while (data.moveToNext()) {
                                theList.add(data.getString(0));

                                listAdapter = new ArrayAdapter<String>(Main8Activity.this, android.R.layout.simple_list_item_multiple_choice, theList) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                                        textView.setTextColor(Color.WHITE);
                                        return view;
                                    }
                                };
                                listAdapter.sort(new Comparator<String>() {
                                    @Override
                                    public int compare(String lhs, String rhs) {
                                        return lhs.compareTo(rhs);
                                    }
                                });
                                l1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                l1.setAdapter(listAdapter);


                            }

                        }

                       Toast tos1= Toast.makeText(Main8Activity.this, "compartment added", Toast.LENGTH_LONG);
                        tos1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        tos1.show();
                        textViewInfo.setText("");

                    }*/
                }
            });
        /*e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e1.setHint("");
                else
                    e1.setHint("Enter route name");
            }
        });*/
        mydb=new Databaseop(Main8Activity.this);

        //populate an ArrayList<String> from the database and then view it
        final ArrayList<String> theList = new ArrayList<>();
        final Cursor data = mydb.gettagContents();
        if (data.getCount() == 0) {
            Toast.makeText(ctx, "", Toast.LENGTH_SHORT).show();
            Toast tos3=Toast.makeText(Main8Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG);
            tos3.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            tos3.show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(0));
                if (theList.size() > 0) {
                   ContextThemeWrapper themedContext =new ContextThemeWrapper(this,R.style.MyCheckBox);
                    listAdapter = new ArrayAdapter<String>(themedContext, android.R.layout.simple_list_item_multiple_choice, theList) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView textView = (TextView) view.findViewById(android.R.id.text1);
                            textView.setTextColor(Color.WHITE);
                            return view;
                        }
                    };
                    l1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    l1.setAdapter(listAdapter);
                } else {
                    Toast tos4=Toast.makeText(Main8Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG);
                    tos4.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    tos4.show();
                }}



            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    final String rname = intent.getStringExtra("name");
                    String rcap = intent.getStringExtra("rcap");



                                    SparseBooleanArray checked = l1.getCheckedItemPositions();
                                    ArrayList<String> selectedItems = new ArrayList<String>();
                                    if(checked.size()==0){
                                       Toast tos2= Toast.makeText(Main8Activity.this,"No compartments selected",Toast.LENGTH_SHORT);
                                        tos2.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

                                        tos2.show();
                                    }
                                    else if(!(checked.size() ==0)) {
                                        for (int i = 0; i < checked.size(); i++) {
                                            // Item position in adapter
                                            int position = checked.keyAt(i);

                                            // Add sport if it is checked i.e.) == TRUE!
                                            if (checked.valueAt(i))
                                                selectedItems.add(listAdapter.getItem(position));
                                        }


                                        final String[] outputStrArr = new String[selectedItems.size()];

                                        for (int i = 0; i < selectedItems.size(); i++) {
                                            outputStrArr[i] = selectedItems.get(i);
                                        }
                                        final String compname = convertArrayToString(outputStrArr);
                                        new AlertDialog.Builder(Main8Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                                                .setMessage("New Round Route-" + rname +"\n" +
                                                        "Compartments-"+compname+"\n" +
                                                        "Are you sure you want create route")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Databaseop dp = new Databaseop(ctx);
                                                        dp.putinfo(dp, rname, compname);
                                                        Toast tos5 = Toast.makeText(Main8Activity.this, "Route created", Toast.LENGTH_SHORT);
                                                        tos5.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        tos5.show();

                                                        Intent i = new Intent(Main8Activity.this, Main5Activity.class);
                                                        startActivity(i);
                                                    }


                                                }).setNegativeButton("No", null).show();

                                    }
                }

            });
        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main8Activity.this,Main5Activity.class);
                startActivity(i);

            }
        });*/

        }}

    public static String strSeparator = ",";

    public static String convertArrayToString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1) {
                str = str + strSeparator;
            }
        }
        return str;
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Main8Activity.this,Main5Activity.class);
                startActivity(i);

    }
    @Override
    protected void onNewIntent(Intent intent) {

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            textViewInfo.setText("tag == null");
        } else {
            Parcelable[] parcelables=intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(parcelables!=null && parcelables.length>0){
                readTextFromTag((NdefMessage)parcelables[0]);
            }else{Toast.makeText(this,"no ndef message",Toast.LENGTH_SHORT).show();}


        }



        super.onNewIntent(intent);
    }

    private void readTextFromTag(NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords=ndefMessage.getRecords();
        if(ndefRecords!=null&&ndefRecords.length>0){
            NdefRecord ndefRecord=ndefRecords[0];
            String tagcontent=gettextfromNdefrecord(ndefRecord);
            textViewInfo.setText(tagcontent);

        }else {
            Toast.makeText(this,"no ndef message",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, Main8Activity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);

        super.onResume();
    }

    @Override
    protected void onPause() {
        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    public String gettextfromNdefrecord(NdefRecord ndefRecord) {
        try {

            byte[] payload = ndefRecord.getPayload();
            String textencoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagcontent = new String(payload, languageSize+1, payload.length - languageSize-1 , textencoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("gettextfromndefrecord", e.getMessage(), e);

        }
        return tagcontent;
    }

}
