package com.example.ashish.mysqliteexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class signup extends AppCompatActivity {

    EditText etname,etemail,etpassword1,etpassword2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etname = (EditText)findViewById(R.id.etname);
        etemail = (EditText)findViewById(R.id.etemail);
        etpassword1 = (EditText)findViewById(R.id.etpassword1);
        etpassword2 = (EditText)findViewById(R.id.etpassword2);
    }

    public void sign(View v){
        String pass1,pass2,name,email;
        pass1 = etpassword1.getText().toString().trim();
        pass2 = etpassword2.getText().toString().trim();
        name = etname.getText().toString().trim();
        email = etemail.getText().toString().trim();

        if(name.equals("") || email.equals("") || pass1.equals("")){
            Toast.makeText(this, "Fill in all the details", Toast.LENGTH_SHORT).show();
        }
        else {

            if (pass1.equals(pass2)) {
                try {
                    SignUpDB db = new SignUpDB(this);
                    db.open();
                    if (db.matchEmail(email) == true) {
                        db.insertDB(name, email, pass1);
                        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Email already exists!", Toast.LENGTH_SHORT).show();
                        etname.setText("");
                        etemail.setText("");
                        etpassword1.setText("");
                        etpassword2.setText("");
                    }
                    db.close();

                } catch (android.database.SQLException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Passwords didn't match!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
