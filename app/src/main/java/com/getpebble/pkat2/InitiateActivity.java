package com.getpebble.pkat2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by mdislam on 4/17/16.
 */
public class InitiateActivity extends Activity {

    NfcAdapter mNfcAdapter;
    boolean mAndroidBeamAvailable  = false;
    private Uri[] mFileUris = new Uri[10];
    private FileUriCallback mFileUriCallback;
    String transferFile = "bumpup.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mAndroidBeamAvailable = false;
        } else {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

            if(mNfcAdapter != null){
                mFileUriCallback = new FileUriCallback();
                mNfcAdapter.setBeamPushUrisCallback(mFileUriCallback, this);

                writeToFile("{ \"user\" : \"" + "md.islam007@rutgers.edu" + "\", \"capital_one_id\" : \"1234567890\", \"amount\" : \"10.00\" }");

                File extDir = getExternalFilesDir(null);
                File requestFile = new File(extDir, transferFile);
                requestFile.setReadable(true, false);

                Uri fileUri = Uri.fromFile(requestFile);
                if (fileUri != null) {
                    mFileUris[0] = fileUri;
                } else {
                    new AlertDialog.Builder(InitiateActivity.this)
                            .setTitle("Failed to Start Race")
                            .setMessage("Cannot find json file.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.logo)
                            .show();
                }
            } else {
                new AlertDialog.Builder(InitiateActivity.this)
                        .setTitle("Cannot Start Race")
                        .setMessage("This device is not supported.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.logo)
                        .show();
            }

        }

    }





    private class FileUriCallback implements NfcAdapter.CreateBeamUrisCallback {

        public FileUriCallback() {}

        public Uri[] createBeamUris(NfcEvent event) {
            return mFileUris;
        }
    }


    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(transferFile, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }





}
