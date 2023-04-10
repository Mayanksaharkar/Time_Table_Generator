package com.example.proj;

import static com.example.proj.R.id.dept_name_edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


public class classNameActivity extends AppCompatActivity {
    AppCompatButton next_button_to_activity2;
    EditText classname, deptname ,clgname;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_name);
        classname = findViewById(R.id.class_name_edittext);
        deptname = findViewById(R.id.dept_name_edittext);
        clgname = findViewById(R.id.clg_name_edittext);

        next_button_to_activity2 = (AppCompatButton) findViewById(R.id.next_button_to_activity2);


        next_button_to_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classname.getText().toString().isEmpty() && deptname.getText().toString().isEmpty() && clgname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Details", Toast.LENGTH_SHORT).show();
                } else if (classname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Class Name ", Toast.LENGTH_SHORT).show();
                } else if (deptname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Department Name", Toast.LENGTH_SHORT).show();
                } else if (clgname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Institute Name", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences preferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("className", classname.getText().toString());
                    editor.putString("deptName", deptname.getText().toString());
                    editor.putString("clgName", clgname.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(classNameActivity.this, num_inputs.class);
                    startActivity(intent);
                    overridePendingTransition(R.xml.slide_up_enter, R.xml.slide_up_exit);
                }
            }
        });
    }
}




