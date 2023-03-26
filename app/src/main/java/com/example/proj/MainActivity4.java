package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class MainActivity4 extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent = getIntent();

        SharedPreferences preferences_from_main = getSharedPreferences("Main", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main2 = getSharedPreferences("Main2", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main3 = getSharedPreferences("Main3", Context.MODE_PRIVATE);
        Toast.makeText(this, "" + preferences_from_main.getString("className", null), Toast.LENGTH_LONG).show();

        // Define timetable variables
        int numSubjects = Integer.parseInt(preferences_from_main2.getString("s" ,"5"));
        int numWorkingDays = Integer.parseInt(preferences_from_main2.getString("wd", "5"));
        int numSlotsPerDay =  Integer.parseInt(preferences_from_main2.getString("l", "5"));


        int[] NUMLECT = intent.getIntArrayExtra("lectnum_array");
        String[] SUBLIST = intent.getStringArrayExtra("subname_array");



       /* String[] subjectList = intent.getStringArrayExtra("subjects");
        int[] slotsPerSubject = intent.getIntArrayExtra("lectnum_array");
        */
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
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        TextView dayHeader = new TextView(this);
        dayHeader.setText("Day");
        dayHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        headerRow.addView(dayHeader);

        TextView slotHeader = new TextView(this);
        slotHeader.setText("Slot");
        slotHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        headerRow.addView(slotHeader);

        TextView subjectHeader = new TextView(this);
        subjectHeader.setText("Subject");
        subjectHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        headerRow.addView(subjectHeader);

        timetableTable.addView(headerRow);

// Add table content
        for (int i = 0; i < numWorkingDays; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            TextView dayView = new TextView(this);
            dayView.setText(String.valueOf(i+1));
            dayView.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(dayView);

            for (int j = 0; j < numSlotsPerDay; j++) {
                TextView slotView = new TextView(this);
                slotView.setText(String.valueOf(j+1));
                slotView.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(slotView);

                TextView subjectView = new TextView(this);
                subjectView.setText(timetable[i][j]);
                subjectView.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(subjectView);
            }

            timetableTable.addView(row);
        }



    }

}


