package com.example.proj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class create_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) AppCompatButton createTTBtn = findViewById(R.id.createTTBtn);
        createTTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_to_className_Activity = new Intent(create_Activity.this , classNameActivity.class);
                startActivity(intent_to_className_Activity);
                overridePendingTransition(R.xml.slide_in_right, R.xml.slide_out_left);
            }
        });
    }
}