package com.getpebble.pkat2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

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

    private int current_steps;
    private int steps_today;
    private boolean isStepsReceived;
    private double calories_today;

    private double seconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        steps = (TextView) findViewById(R.id.steps);
        calories = (TextView) findViewById(R.id.calories);

        current_steps = 0;


        timer = (TextView) findViewById(R.id.timer);


        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds = seconds + 0.25;
                runOnUiThread(Timer_Tick);
            }

        }, 0, 250);





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
                            }


                            calories_today = 0.045 * current_steps;


                            steps.setText(Integer.toString(current_steps));
                            calories.setText(Double.toString(Math.round(calories_today)));
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


}
