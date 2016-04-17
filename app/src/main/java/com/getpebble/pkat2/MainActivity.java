package com.getpebble.pkat2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mdislam on 4/16/16.
 */
public class MainActivity extends Activity {


    private SavedSession savedSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        savedSession = (SavedSession) getIntent().getSerializableExtra("SavedSession");


        TextView tap2Btn = (TextView) findViewById(R.id.tap2);
        tap2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecieveActivity.class);
                startActivity(intent);
            }
        });


        TextView tabBtn = (TextView) findViewById(R.id.tap);
        tabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InitiateActivity.class);
                startActivity(intent);
            }
        });

    }


















}
