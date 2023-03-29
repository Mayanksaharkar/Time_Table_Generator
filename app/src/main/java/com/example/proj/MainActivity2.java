package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity2 extends AppCompatActivity {

    EditText lect_in_a_day,working_days,no_of_Sub;
    AppCompatButton btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 //       Stringent intent = getStringent();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lect_in_a_day = findViewById(R.id.lect_in_a_day);
        working_days = findViewById(R.id.wroking_days);
        no_of_Sub = findViewById(R.id.no_of_sub);
        btn =(AppCompatButton)findViewById(R.id.btn_next);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable or disable the button based on the EditTexts' contents
                boolean enabled = !lect_in_a_day.getText().toString().trim().isEmpty()
                        && !working_days.getText().toString().trim().isEmpty() && !no_of_Sub.getText().toString().trim().isEmpty() ;
                btn.setEnabled(enabled);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        };
        lect_in_a_day.addTextChangedListener(textWatcher);
        working_days.addTextChangedListener(textWatcher);
        no_of_Sub.addTextChangedListener(textWatcher);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Main2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("l" ,Integer.parseInt(lect_in_a_day.getText().toString()));
                editor.putInt("wd" ,Integer.parseInt(working_days.getText().toString()));
                editor.putInt("s" ,Integer.parseInt(no_of_Sub.getText().toString()));
                editor.commit();
                Intent next_intent =  new Intent(MainActivity2.this , MainActivity3.class);
                startActivity(next_intent);
            }
        });

    }
}