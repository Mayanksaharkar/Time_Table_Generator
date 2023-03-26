package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity2 extends AppCompatActivity {

    EditText lect_in_a_day,working_days,no_of_Sub;
params params = new params();

    AppCompatButton btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Stringent intent = getStringent();
        Toast.makeText(this, ""+intent.getStringExtra("className"), Toast.LENGTH_SHORT).show();
*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lect_in_a_day = findViewById(R.id.lect_in_a_day);
        working_days = findViewById(R.id.wroking_days);
        no_of_Sub = findViewById(R.id.no_of_sub);
        String int_lect_in_a_day = lect_in_a_day.getText().toString();
        String int_working_days = working_days.getText().toString();
        String int_no_of_Sub =no_of_Sub.getText().toString();

        btn =(AppCompatButton)findViewById(R.id.btn_next);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               params.setNo_working_days();

                Intent next_intent =  new Intent(MainActivity2.this , MainActivity3.class);
                startActivity(next_intent);
            }
        });

    }
}