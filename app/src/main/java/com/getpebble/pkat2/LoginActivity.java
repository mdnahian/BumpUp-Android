package com.getpebble.pkat2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by mdislam on 4/17/16.
 */
public class LoginActivity extends Activity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        setContentView(R.layout.login_activity);

        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);

        TextView loginBtn = (TextView) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Cannot Login")
                            .setMessage("Please fill all fields and try again.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.logo)
                            .show();
                } else {
                    final Firebase firebase = new Firebase("https://bumpup.firebaseio.com/");

                    firebase.authWithPassword(email.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            SavedSession savedSession = new SavedSession();
                            savedSession.setEmail(email.getText().toString());
                            createSavedSession(savedSession);

                            Intent intent = new Intent(LoginActivity.this, RaceActivity.class);
                            intent.putExtra("SavedSession", savedSession);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Cannot Login")
                                    .setMessage("Username or password is incorrect. Please try again.")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setIcon(R.drawable.logo)
                                    .show();
                        }
                    });


                }
            }
        });


    }




    public void createSavedSession(SavedSession savedSession){
        SharedPreferences sp = getSharedPreferences("SavedSession", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("fname", savedSession.getFname());
        ed.putString("lname", savedSession.getLname());
        ed.putString("email", savedSession.getEmail());
        ed.putString("capital_one_id", savedSession.getCapital_one_id());
        ed.apply();
    }



}
