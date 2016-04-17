package com.getpebble.pkat2;

import android.app.Activity;
import android.os.Bundle;

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
    }



}
