package com.example.ashish.mysqliteexample;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Show extends AppCompatActivity {
    TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        tvData = (TextView)findViewById(R.id.tvData);

        try{
            SignUpDB db = new SignUpDB(this);
            db.open();
            tvData.setText(db.getData());
            db.close();

        }
        catch (SQLException e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

