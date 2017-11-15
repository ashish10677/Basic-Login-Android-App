package com.example.ashish.mysqliteexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignedIn extends AppCompatActivity {
    TextView tvWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_PREFS,MODE_PRIVATE);
        String result = prefs.getString("username","");
        tvWelcome.setText("Welcome "+result+"!");
    }

    public void logOut(View v){
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.MY_PREFS,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
