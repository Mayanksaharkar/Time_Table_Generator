package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity2 extends AppCompatActivity {

    EditText lect_in_a_day,working_days,no_of_Sub;
    public static int int_lect_in_a_day;
    public static int int_working_days;
    public static int int_no_of_Sub;

    AppCompatButton btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lect_in_a_day = findViewById(R.id.lect_in_a_day);
        working_days = findViewById(R.id.wroking_days);
        no_of_Sub = findViewById(R.id.no_of_sub);

       /*int_lect_in_a_day = Integer.parseInt(lect_in_a_day.getText().toString());
        int_working_days = Integer.parseInt(working_days.getText().toString());
        int_no_of_Sub = Integer.parseInt(no_of_Sub.getText().toString());*/

        btn =(AppCompatButton)findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });

    }
}