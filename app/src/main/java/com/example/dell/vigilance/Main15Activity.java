package com.example.dell.vigilance;

import android.app.PendingIntent;
import android.content.Context;
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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main15Activity extends AppCompatActivity {
    ListView l1;
    TextView t1;


    Button b1;
    String tagcontent,rname;
    Context ctx = this;
    Databaseop mydb;
    ArrayAdapter<String> listAdapter;
    private NfcAdapter nfcAdapter;
    ArrayList<String> theList,arrayList;
    int flag=0;
    Databaseop dp;
    int counter;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);

        l1 = (ListView) findViewById(R.id.listview3);
        b1 = (Button) findViewById(R.id.button14);
        //b2 = (Button) findViewById(R.id.button30);
        t1 = (TextView) findViewById(R.id.textView2);
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
        mydb = new Databaseop(Main15Activity.this);
        final String rname = getIntent().getStringExtra("rname");
        t1.setText(rname);
       dp = new Databaseop(ctx);
        mydb = new Databaseop(Main15Activity.this);

        Cursor data = mydb.getListContents(dp, rname);
        if (data.getCount() == 0) {
            Toast tos=Toast.makeText(Main15Activity.this, "There are no contents in this list!", Toast.LENGTH_LONG);
            tos.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            tos.show();;
        } else {
            while (data.moveToNext()) {
                String theListcon = data.getString(0);
                Intent intent=getIntent();
                //String user=intent.getStringExtra("name");
                String rid=intent.getStringExtra("rid");
                final String[] contents = convertStringToArray(theListcon);
                for (int j = 0; j <= contents.length - 1; j++) {
                    String cmp=contents[j];
                    dp = new Databaseop(ctx);
                    dp.reports2(dp,rid,cmp);
                }
                //arrayList=new ArrayList<>(Arrays.asList(contents));


                listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contents){

                    @Override

                public View getView(int position , View convertView, ViewGroup parent){
                    View view=super.getView(position, convertView, parent);
                    TextView textView=(TextView)view.findViewById(android.R.id.text1);
                    textView.setTextColor(Color.WHITE);
                    //textView.setBackgroundColor(Color.RED);

                    String content=getItem(position);


                        if(content.equals(tagcontent)&&!(flag==1))
                        {
                            textView.setBackgroundColor(Color.GREEN);
                            Databaseop dp = new Databaseop(ctx);
                            dp.repoupdt(dp,tagcontent);
                           Toast tos1= Toast.makeText(Main15Activity.this,tagcontent+" is Visited",Toast.LENGTH_SHORT);
                            tos1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            tos1.show();;
                        }
                        //Toast.makeText(Main15Activity.this, "Location not on route", Toast.LENGTH_SHORT).show();


                    return view;
                }

                };

                l1.setAdapter(listAdapter);








            }


        }
       b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String rid=intent.getStringExtra("rid");
                Databaseop dp = new Databaseop(ctx);
                dp.repoupdt(dp,rid);
                Cursor data1 = dp.getstatcnt(Integer.parseInt(rid));
                data1.moveToNext();
                int theListcon = Integer.parseInt(data1.getString(0));
                int j= l1.getAdapter().getCount();
                dp.updatereport(dp,rid,theListcon,j);
                dp.updateendtime(dp,rid);
                Toast.makeText(Main15Activity.this,
                        ""+theListcon+"/"+j,
                        Toast.LENGTH_LONG).show();

                Intent i=new Intent(Main15Activity.this,MainActivity.class);
                startActivity(i);

            }
        });


    }

    public static String strSeparator = ",";

    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);
        return arr;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {Toast.makeText(this,"no ndef message",Toast.LENGTH_SHORT).show();

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


        }else {
            Toast.makeText(this,"no ndef message",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, Main15Activity.class);
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
            listAdapter.notifyDataSetChanged();
        } catch (UnsupportedEncodingException e) {
            Log.e("gettextfromndefrecord", e.getMessage(), e);

        }

        return tagcontent;
    }
    @Override
    public void onBackPressed() {
        // Simply Do noting!
        Toast.makeText(Main15Activity.this,"you cannot go back from here",Toast.LENGTH_SHORT).show();
    }
}
