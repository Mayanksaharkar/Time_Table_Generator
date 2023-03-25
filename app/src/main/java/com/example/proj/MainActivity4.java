package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    TextView  classNAme,workingdays,lectures,subjects;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        TableLayout tableLayout = findViewById(R.id.gridlay);
        TableLayout table = findViewById(R.id.table);
        classNAme=findViewById(R.id.classNAme);
        workingdays=findViewById(R.id.workingdays);
        lectures=findViewById(R.id.lectures);
        subjects=findViewById(R.id.subjects);

        SharedPreferences preferences_from_main = getSharedPreferences("Main" , Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main2 = getSharedPreferences("Main2" , Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main3 = getSharedPreferences("Main3" , Context.MODE_PRIVATE);
          Toast.makeText(this, ""+preferences_from_main.getString("className" , null), Toast.LENGTH_LONG).show();

        classNAme.setText(preferences_from_main.getString("className" ,"null"));
        workingdays.setText(preferences_from_main2.getString("wd" , "null"));
        lectures.setText(preferences_from_main2.getString("l" , "null"));
        subjects.setText(preferences_from_main2.getString("s" , "null"));



        int numSubjects = Integer.parseInt(preferences_from_main2.getString("wd" , "null"));
       /* Toast.makeText(this, ""+numSubjects, Toast.LENGTH_SHORT).show();*/
        int[] numLectures = new int[numSubjects];
        String[] subjects = new String[numSubjects];
        int rowCount = tableLayout.getChildCount() ;
        int numWorkingDays = Integer.parseInt(preferences_from_main2.getString("wd","null"));
        int numSlots =Integer.parseInt(preferences_from_main2.getString("l","null"));


        for (int i = 0; i < rowCount; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            TextView cell = (TextView) row.getChildAt(0);
            subjects[i] = cell.getText().toString();
        }
        for (int i = 0; i < rowCount; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            TextView cell = (TextView) row.getChildAt(1);
            numLectures[i] = Integer.parseInt(cell.getText().toString());
        }

        String[][] timetable = new String[numWorkingDays][numSlots];

        int subjectIndex = 0;
        int lectureCount = 0;

        for (int i = 0; i < numWorkingDays; i++) {
            for (int j = 0; j < numSlots; j++) {
                timetable[i][j] = subjects[subjectIndex];
                lectureCount++;

                if (lectureCount == numLectures[subjectIndex]) {
                    subjectIndex++;
                    lectureCount = 0;
                }

                if (subjectIndex == numSubjects) {
                    subjectIndex = 0;
                }
            }
        }

        for (int i = 0; i < timetable.length; i++) {
            TableRow row = new TableRow(MainActivity4.this);
            for (int j = 0; j < timetable[i].length; j++) {
                TextView cell = new TextView(this);
                cell.setText(String.valueOf(timetable[i][j]));
                row.addView(cell);
            }
            table.addView(row);
        }

    }

}
