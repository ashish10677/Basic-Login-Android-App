package com.example.ashish.mysqliteexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    EditText etSignEmail, etSignPass;
    public static final String MY_PREFS = "com.example.ashish.mysqliteexample.details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS,MODE_PRIVATE);
        if(prefs.contains("username") && prefs.contains("email") && prefs.contains("passwd")){
            startActivity(new Intent(this,SignedIn.class));
            finish();
        }

        etSignEmail = (EditText)findViewById(R.id.etSignEmail);
        etSignPass = (EditText)findViewById(R.id.etSignPass);
    }

    public void callSignUp(View v){
        startActivity(new Intent(this,signup.class));
        finish();
    }

    public void callSignIn(View v){

        String email,passwd,res;

        email = etSignEmail.getText().toString().trim();
        passwd = etSignPass.getText().toString().trim();
        SignUpDB db = new SignUpDB(this);
        try {
            db.open();
            res = db.matchData(email, passwd);
            if (res != null) {

                Toast.makeText(this, "Sign In successful", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS,MODE_PRIVATE).edit();
                editor.putString("username",res);
                editor.putString("email",email);
                editor.putString("passwd",passwd);
                editor.commit();

                Intent i = new Intent(this, SignedIn.class);
                startActivity(i);
                finish();

            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
        catch (android.database.SQLException e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
