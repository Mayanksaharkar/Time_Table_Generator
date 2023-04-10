package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

public class num_inputs extends AppCompatActivity {

    EditText lect_in_a_day,working_days,no_of_Sub;
    AppCompatButton btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 //       Stringent intent = getStringent();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_inputs);
        lect_in_a_day = findViewById(R.id.lect_in_a_day);
        working_days = findViewById(R.id.working_days);
        no_of_Sub = findViewById(R.id.no_of_sub);
        btn =(AppCompatButton)findViewById(R.id.btn_next);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.offwhite));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lect_in_a_day.getText().toString().isEmpty() && working_days.getText().toString().isEmpty() && no_of_Sub.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Details", Toast.LENGTH_SHORT).show();
                } else if (lect_in_a_day.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter # of lecture in a day ", Toast.LENGTH_SHORT).show();
                } else if (working_days.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter # of working days", Toast.LENGTH_SHORT).show();
                }else if (Integer.parseInt(working_days.getText().toString())>= 7) {
                    Toast.makeText(getApplicationContext(), "working days must be less than or equal 7", Toast.LENGTH_SHORT).show();
                }else if (no_of_Sub.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter # of subjects", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("Main2", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("l", Integer.parseInt(lect_in_a_day.getText().toString()));
                    editor.putInt("wd", Integer.parseInt(working_days.getText().toString()));
                    editor.putInt("s", Integer.parseInt(no_of_Sub.getText().toString()));
                    editor.commit();
                    Intent next_intent = new Intent(num_inputs.this, sub_name.class);
                    startActivity(next_intent);
                }
            }
        });

    }
}