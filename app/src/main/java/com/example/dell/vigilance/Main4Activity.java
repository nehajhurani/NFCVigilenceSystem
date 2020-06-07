package com.example.dell.vigilance;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity {
   // private NfcAdapter nfcAdapter;
    TextView textViewInfo,t1;
    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    public static final String WRITE_ERROR = "Error during writing, is the NFC tag close enough to your device?";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;

    Button b1,b2;
    //Intent intent;
    Databaseop mydb;
    String tagcontent;
    Context ctx=this;
    EditText e1;
    int flag,flag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context=this;
        textViewInfo = (TextView) findViewById(R.id.info);
        b1=(Button)findViewById(R.id.button);
        t1=(TextView)findViewById(R.id.textView);
        //b2=(Button)findViewById(R.id.button1);
        e1=(EditText)findViewById(R.id.editText);
        e1.setFilters(new InputFilter[]{EMOJI_FILTER});
        e1.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    e1.setHint("");
                else
                    e1.setHint("Enter compartment name");
            }
        });





        /*b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main4Activity.this, Main3Activity.class);
                startActivity(i);
            }
        });*/
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC NOT Enabled!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            finish();
        }
        else{
            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
            writeTagFilters = new IntentFilter[] { tagDetected };}
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(myTag ==null) {
                        Toast.makeText(context, ERROR_DETECTED, Toast.LENGTH_LONG).show();
                    } else {
                        mydb = new Databaseop(Main4Activity.this);
                        Cursor date = mydb.gettagContents();
                        String[] array = new String[date.getCount()];
                        int i = 0;
                        while (date.moveToNext()) {
                            String uname = date.getString(0);
                            array[i] = uname;
                            i++;
                        }
                        for (int j = 0; j <= array.length - 1; j++) {
                            if (e1.getText().toString().equals(array[j])) {
                                Toast.makeText(Main4Activity.this, "Route Name already exists", Toast.LENGTH_SHORT).show();
                                flag = 1;
                                break;
                            } else if (e1.getText().toString().equals("")) {
                                Toast.makeText(Main4Activity.this, "Enter Compartment Name", Toast.LENGTH_SHORT).show();
                                flag = 1;
                                break;
                            } else {
                                flag = 0;
                            }
                        }

                        if (flag == 0) {
                            new AlertDialog.Builder(Main4Activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                                    .setMessage("The Tag is presently set to " + tagcontent + ", please confirm you want to change it to " + e1.getText().toString())
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                write(e1.getText().toString(), myTag);
                                                Toast.makeText(context, WRITE_SUCCESS, Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                Toast.makeText(context,WRITE_ERROR, Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            } catch (FormatException e) {
                                                Toast.makeText(context,WRITE_ERROR, Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }


                                        }
                                        //  else{Toast.makeText(Main4Activity.this, "tag not placed", Toast.LENGTH_SHORT).show();}
                                    }).setNegativeButton("Back", null).show();
                        }
                    }
                 }

        });

    }
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            // textViewInfo.setText("tag == null");
        } else {
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (parcelables != null && parcelables.length > 0) {
                readTextFromTag((NdefMessage) parcelables[0]);
                flag2 = 1;
            } else {
                Toast.makeText(Main4Activity.this, "no ndef message", Toast.LENGTH_SHORT).show();
            }
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
                myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


            }


            b1.setBackgroundColor(getResources().getColor(R.color.GREEN));
        }
    }

    private void readTextFromTag(NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords=ndefMessage.getRecords();
        if(ndefRecords!=null&&ndefRecords.length>0){
            NdefRecord ndefRecord=ndefRecords[0];
            String tagcontent=gettextfromNdefrecord(ndefRecord);
//            textViewInfo.setText(tagcontent);

        }else {
            Toast.makeText(this,"no ndef message",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onResume() {
        //

        super.onResume();
        WriteModeOn();
    }

    @Override
    protected void onPause() {

        b1.setBackgroundResource(R.drawable.border);
        super.onPause();
        WriteModeOff();
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

    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        // Enable I/O
        ndef.connect();
        // Write the message
        ndef.writeNdefMessage(message);
        // Close the connection
        Databaseop  dp = new Databaseop(ctx);
        mydb = new Databaseop(Main4Activity.this);
        mydb.puttaginfo(dp, e1.getText().toString());

        ndef.close();
    }
    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang       = "en";
        byte[] textBytes  = text.getBytes();
        byte[] langBytes  = lang.getBytes("US-ASCII");
        int    langLength = langBytes.length;
        int    textLength = textBytes.length;
        byte[] payload    = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1,              langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

        return recordNFC;
    }

    private void WriteModeOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }
    /******************************************************************************
     **********************************Disable Write*******************************
     ******************************************************************************/
    private void WriteModeOff(){
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
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
        Intent i = new Intent(Main4Activity.this, Main13Activity.class);
        startActivity(i);
    }


}


