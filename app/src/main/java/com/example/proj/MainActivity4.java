package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

public class MainActivity4 extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent = getIntent();
        TableLayout tableLayout = findViewById(R.id.gridlay);

        SharedPreferences preferences_from_main = getSharedPreferences("Main", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main2 = getSharedPreferences("Main2", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main3 = getSharedPreferences("Main3", Context.MODE_PRIVATE);
        Toast.makeText(this, "" + preferences_from_main.getString("className", null), Toast.LENGTH_LONG).show();

        TextView classname= findViewById(R.id.className);
        classname.setText(preferences_from_main.getString("className","class name" ));
        // Define timetable variables
        int numSubjects = preferences_from_main2.getInt("s" ,5);
        int numWorkingDays =preferences_from_main2.getInt("wd", 5);
        int numSlotsPerDay =  preferences_from_main2.getInt("l", 5);


        int[] NUMLECT =intent.getIntArrayExtra("lectnum_array");
        String[] SUBLIST = intent.getStringArrayExtra("subname_array");

        for (int i = 0 ; i <SUBLIST.length ; i++){
            Log.d("mayank", "onCreate: "+SUBLIST[i]);
        }
        for (int i = 0 ; i <NUMLECT.length ; i++){
            Log.d("mayank", "onCreate: "+NUMLECT[i]);
        }

// Create blank timetable matrix
        String[][] timetable = new String[numWorkingDays][numSlotsPerDay];
        for (int i = 0; i < numWorkingDays; i++) {
            Arrays.fill(timetable[i], "");
        }

// Assign slots for each subject
        Random random = new Random();
        for (int i = 0; i < numSubjects; i++) {
            String sub = SUBLIST[i];
            int numSlots = NUMLECT[i];
            for (int j = 0; j < numSlots; j++) {
                int day = random.nextInt(numWorkingDays);
                int slot = random.nextInt(numSlotsPerDay);
                while (!timetable[day][slot].equals("")) {
                    day = random.nextInt(numWorkingDays);
                    slot = random.nextInt(numSlotsPerDay);
                }
                timetable[day][slot] = sub;
            }
        }

// Find the timetable table layout
        TableLayout timetableTable = findViewById(R.id.timetable_table);


// Add table headers
       /* TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        headerRow.setBackgroundColor(Integer.parseInt(String.valueOf(Color.RED)));

        TextView dayHeader = new TextView(this);
        dayHeader.setText("Day");
        dayHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        dayHeader.setTextSize(15);
        headerRow.addView(dayHeader);


        TextView subjectHeader = new TextView(this);
        subjectHeader.setText("Subject");
        subjectHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        subjectHeader.setTextSize(15);
        headerRow.addView(subjectHeader);

        timetableTable.addView(headerRow);*/

// Add table content
        for (int i = 0; i < numWorkingDays; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            TextView dayView = new TextView(this);
            dayView.setText(String.valueOf(i+1));
            dayView.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(dayView);

            for (int j = 0; j < numSlotsPerDay; j++) {

                TextView subjectView = new TextView(this);
                subjectView.setText(timetable[i][j]);
                subjectView.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(subjectView);
            }

            timetableTable.addView(row);
        }

        AppCompatButton pdf_button = findViewById(R.id.pdf_button);

    }
}



