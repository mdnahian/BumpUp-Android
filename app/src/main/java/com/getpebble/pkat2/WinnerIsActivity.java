package com.getpebble.pkat2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * Created by mdislam on 4/17/16.
 */
public class WinnerIsActivity extends Activity {

    Compare compare;
    SavedSession savedSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winneris_activity);

        savedSession = (SavedSession) getIntent().getSerializableExtra("SavedSession");
        compare = (Compare) getIntent().getSerializableExtra("Compare");

        TextView competeBtn = (TextView) findViewById(R.id.competeBtn);
        final TextView winner = (TextView) findViewById(R.id.winner);

        competeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WinnerIsActivity.this, RaceActivity.class);
                intent.putExtra("SavedSession", savedSession);
                startActivity(intent);
                finish();
            }
        });

        winner.setText("user2 is the winner!");



    }


}
