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
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main12Activity extends AppCompatActivity {

    ListView l1;
    TextView t1, textViewInfo;
    Button b1, b2;
    Context ctx = this;
    EditText inputSearch;
    Databaseop mydb;
    private NfcAdapter nfcAdapter;
    NdefRecord ndefRecord;
    String tagcontent;
    List<String> subject_list;
    ArrayAdapter<String> listAdapter;
    String theListcon;

    String[] contents,contents1,c1,c2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        l1 = (ListView) findViewById(R.id.listview);
        b1 = (Button) findViewById(R.id.button19);
        b2 = (Button) findViewById(R.id.button18);
        t1 = (TextView) findViewById(R.id.textView8);
       // textViewInfo = (TextView) findViewById(R.id.textView7);

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
        mydb = new Databaseop(Main12Activity.this);
        Intent i = getIntent();
        final String rname = i.getStringExtra("name");
        t1.setText(rname);
        Databaseop dp = new Databaseop(ctx);
        mydb.gettagContents();
        mydb = new Databaseop(Main12Activity.this);

        Cursor data1 = mydb.getListContents(dp, rname);
        if (data1.getCount() == 0) {
            Toast.makeText(Main12Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data1.moveToNext()) {
                String theListcon = data1.getString(0);
                contents = convertStringToArray(theListcon);
            }
            //populate an ArrayList<String> from the database and then view it
            //final ArrayList<String> theList = new ArrayList<>();
            final Cursor data = mydb.gettagContents();
            contents1 = new String[data.getCount()];
            int a = 0;
            while (data.moveToNext()) {
                String con = data.getString(0);
                contents1[a] = con;
                a++;
            }
            //contents1=convertStringToArray(theList);
            ContextThemeWrapper themedContext =new ContextThemeWrapper(this,R.style.MyCheckBox);
            listAdapter = new ArrayAdapter<String>(themedContext, android.R.layout.simple_list_item_multiple_choice, contents1) {
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
            for (int k = 0; k <= contents1.length - 1; k++) {
                for (int j = 0; j <= contents.length - 1; j++) {

                    if (contents1[k].equals(contents[j])) {
                        int position = k;
                        // Toast.makeText(Main12Activity.this,""+contents[k]+" "+contents1[j]+""+position+"",Toast.LENGTH_SHORT).show();
                        l1.setItemChecked(k, true);

                    }

                }
            }
        }


          /*  l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    // TODO Auto-generated method stub
                    theList.get(position);
                    String value = (String) l1.getItemAtPosition(position);
                    Databaseop dp = new Databaseop(ctx);
                    dp.deletefromtag(dp, value);
                    theList.remove(position);

                    listAdapter.notifyDataSetChanged();

                    Toast.makeText(Main12Activity.this, "Item Deleted", Toast.LENGTH_LONG).show();

                    return true;
                }

            });
        }*/

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Databaseop dp = new Databaseop(ctx);
                    final Cursor data2 = mydb.getListContents(dp, rname);
                    if (data2.getCount() == 0) {
                        Toast.makeText(Main12Activity.this, "no data", Toast.LENGTH_LONG).show();
                    } else {
                        while (data2.moveToNext()) {
                            String theListcon = data2.getString(0);

                            dp.updatecomp(dp, rname, theListcon, tagcontent);
                            mydb.puttaginfo(dp, tagcontent);
                            textViewInfo.setText("");
                        }
                    }
                    mydb.gettagContents();
                    mydb = new Databaseop(Main12Activity.this);

                    Cursor data3 = mydb.getListContents(dp, rname);
                    if (data3.getCount() == 0) {
                        Toast.makeText(Main12Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
                    } else {
                        while (data3.moveToNext()) {
                            String theListcon = data3.getString(0);
                            c1 = convertStringToArray(theListcon);
                        }
                        //populate an ArrayList<String> from the database and then view it
                        //final ArrayList<String> theList = new ArrayList<>();
                        final Cursor data4 = mydb.gettagContents();
                        c2 = new String[data4.getCount()];
                        int a = 0;
                        while (data4.moveToNext()) {
                            String con = data4.getString(0);
                            c2[a] = con;
                            a++;
                        }
                        //contents1=convertStringToArray(theList);

                        listAdapter = new ArrayAdapter<String>(Main12Activity.this, android.R.layout.simple_list_item_multiple_choice, c2) {
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
                        for (int k = 0; k <= c2.length - 1; k++) {
                            for (int j = 0; j <= c1.length - 1; j++) {

                                if (c2[k].equals(c1[j])) {
                                    int position = k;
                                    // Toast.makeText(Main12Activity.this,""+contents[k]+" "+contents1[j]+""+position+"",Toast.LENGTH_SHORT).show();
                                    l1.setItemChecked(k, true);

                                }

                            }
                        }
                    }


                    Toast.makeText(Main12Activity.this, "Item added", Toast.LENGTH_LONG).show();

                }
            });

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(Main12Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                                .setMessage("Are you sure you want to Modify?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SparseBooleanArray checked = l1.getCheckedItemPositions();
                                        ArrayList<String> selectedItems = new ArrayList<String>();
                                        if(checked.size()==0){Toast.makeText(Main12Activity.this,"No compartment Selected",Toast.LENGTH_SHORT).show();}
                                        else if (!(checked.size() ==0)) {
                                            for (int i = 0; i < checked.size(); i++) {
                                                // Item position in adapter
                                                int position = checked.keyAt(i);

                                                // Add sport if it is checked i.e.) == TRUE!
                                                if (checked.valueAt(i))
                                                    selectedItems.add((String) listAdapter.getItem(position));
                                            }

                                            String[] outputStrArr = new String[selectedItems.size()];

                                            for (int i = 0; i < selectedItems.size(); i++) {
                                                outputStrArr[i] = selectedItems.get(i);
                                            }
                                            String compname = convertArrayToString(outputStrArr);
                                            Databaseop dp = new Databaseop(ctx);
                                            dp.updateinfo(dp, rname, compname);
                                            Toast.makeText(Main12Activity.this, "Route Modified", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Main12Activity.this, Main5Activity.class);
                                            startActivity(i);
                                        }
                                    }
                                }).setNegativeButton("No", null).show();


                    }

                });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main12Activity.this, WriteM.class);
                startActivity(i);
            }

        });

            }

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
        Intent i = new Intent(Main12Activity.this, Main5Activity.class);
        startActivity(i);
    }

    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);
        return arr;
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
        Intent intent = new Intent(this, Main12Activity.class);
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


