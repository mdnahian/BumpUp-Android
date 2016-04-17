package com.getpebble.pkat2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class RaceActivity extends Activity {

    private static final UUID APP_UUID = UUID.fromString("8C488329-0A39-4A07-81B7-D257567BC3C5");
    private static final int KEY_BUTTON_UP = 0;
    private static final int KEY_BUTTON_DOWN = 1;
    private PebbleKit.PebbleDataReceiver mDataReceiver;


    private TextView steps;
    private TextView calories;
    private TextView timer;
    private TextView distance;

    private int current_steps;
    private int steps_today;
    private boolean isStepsReceived;
    private double calories_today;

    private double seconds;


    private double miles;


    private SavedSession savedSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        Firebase.setAndroidContext(this);

        savedSession = new SavedSession();
        loadSavedSession();

        if(savedSession.getEmail() == null){
            Intent intent = new Intent(RaceActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        steps = (TextView) findViewById(R.id.steps);
        calories = (TextView) findViewById(R.id.calories);
        distance = (TextView) findViewById(R.id.distance);

        current_steps = 0;


        timer = (TextView) findViewById(R.id.timer);


        new AlertDialog.Builder(RaceActivity.this)
                .setTitle("Get Ready")
                .setMessage("Are you ready to start?")
                .setPositiveButton(getText(R.string.ready), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PebbleKit.startAppOnPebble(RaceActivity.this, APP_UUID);

                        final Timer myTimer = new Timer();
                        myTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if(seconds < 35){
                                    seconds = seconds + 0.25;
                                    runOnUiThread(Timer_Tick);
                                } else {

                                    myTimer.cancel();
                                    myTimer.purge();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String user;

                                            if(savedSession.getEmail().equals("md.islam007@rutgers.edu")){
                                                user = "user1";
                                            } else {
                                                user = "user2";
                                            }

                                            String date_str = "201604200910";

                                            try {
                                                String str_date = new Date().toString();
                                                SimpleDateFormat dt = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
                                                Date date = dt.parse(str_date);
                                                SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMddhhmm");

                                                date_str = dt1.format(date);

                                            } catch (ParseException e){
                                                Log.d("Crash", "Could not parse date.");
                                            }



                                            final Intent intent = new Intent(RaceActivity.this, WinnerIsActivity.class);

                                            final Firebase firebase2 = new Firebase("https://bumpup.firebaseio.com/");
                                            Compare compare = new Compare(
                                                    new Info(Double.toString(seconds), date_str),
                                                    new User(Double.parseDouble(String.format("%1$,.2f", calories_today)), miles, user, current_steps));

                                            firebase2.child(generateRandom()).setValue(compare);


                                            intent.putExtra("Compare", compare);
                                            intent.putExtra("SavedSession", savedSession);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                }
                            }

                        }, 0, 250);

                    }
                })
                .setCancelable(false)
                .setIcon(R.drawable.logo)
                .show();


    }



    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            timer.setText(String.format("%1$,.2f", seconds)+"s");
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        if(mDataReceiver == null) {
            mDataReceiver = new PebbleKit.PebbleDataReceiver(APP_UUID) {

                @Override
                public void receiveData(Context context, int transactionId, PebbleDictionary dict) {
                    // Always ACK
                    PebbleKit.sendAckToPebble(context, transactionId);
                    //Log.i("Crash", "Got message from Pebble!");

                    // Up received?
                    if(dict.getInteger(KEY_BUTTON_UP) != null) {

                        if(!isStepsReceived){
                            steps_today = dict.getInteger(KEY_BUTTON_UP).intValue();
                            isStepsReceived = true;
                        } else {
                            current_steps = dict.getInteger(KEY_BUTTON_UP).intValue() - steps_today;

                            if(current_steps < 0){
                                current_steps = 0;
                                steps_today = dict.getInteger(KEY_BUTTON_UP).intValue();
                            }


                            calories_today = 0.045 * current_steps;

                            miles = current_steps / 2000;

                            steps.setText(Integer.toString(current_steps));
                            calories.setText(Double.toString(Math.round(calories_today)));
                            distance.setText(Double.toString(Math.round(miles))+" mi.");
                        }




                    }

                    // Down received?
                    if(dict.getInteger(KEY_BUTTON_DOWN) != null) {
                        Log.d("Crash", "Down Received");
                    }
                }

            };
            PebbleKit.registerReceivedDataHandler(getApplicationContext(), mDataReceiver);
        }
    }



    public void loadSavedSession(){
        SharedPreferences sp1 = this.getSharedPreferences("SavedSession", 0);
        savedSession.setEmail(sp1.getString("email", null));
    }


    public static String generateRandom(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }



}
