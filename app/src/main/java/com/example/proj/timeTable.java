package com.example.proj;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

public class timeTable extends AppCompatActivity {
    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bitmap, scaledbmp;
    LinearLayout linear;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        View rootLayout = findViewById(android.R.id.content);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootLayout.startAnimation(fadeIn);


        Drawable drawable = getDrawable(R.drawable.edit_text_border);
        Intent intent = getIntent();
        TableLayout tableLayout = findViewById(R.id.gridlay);
        AppCompatButton regenerateBtn = findViewById(R.id.reGenerete);
        SharedPreferences preferences_from_main = getSharedPreferences("Main", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main2 = getSharedPreferences("Main2", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main3 = getSharedPreferences("Main3", Context.MODE_PRIVATE);

        TextView classname = findViewById(R.id.className);
        TextView clgname = findViewById(R.id.collegeName);
        String heading =preferences_from_main.getString("deptName" ,"Department Name")  +" : " + preferences_from_main.getString("className" , "Class Name") ;
        clgname.setText(preferences_from_main.getString("clgName", "Institute Name"));
        classname.setText( heading);
        // Define timetable variables
        int numSubjects = preferences_from_main2.getInt("s", 5);
        int numWorkingDays = preferences_from_main2.getInt("wd", 5);
        int numSlotsPerDay = preferences_from_main2.getInt("l", 5);

        /*int[] NUMLECT ={5,5,5,5,5};
        String[] SUBLIST = {"Network and information security.  ","Wireless mobile application. ", "Management ", "Emerging dreams. " ," Mobile application developer. "};
*/

        int[] NUMLECT = intent.getIntArrayExtra("lectnum_array");
        String[] SUBLIST = intent.getStringArrayExtra("subname_array");
        String[] day_arr = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        for (int i = 0; i < SUBLIST.length; i++) {
            Log.d("mayank", "onCreate: " + SUBLIST[i]);
        }
        for (int i = 0; i < NUMLECT.length; i++) {
            Log.d("mayank", "onCreate: " + NUMLECT[i]);
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

        String prevVal = SUBLIST[0]; // keep track of previously assigned value
        for (int i = 0; i < timetable.length; i++) {
            for (int j = 0; j < timetable[i].length; j++) {
                // shuffle array until current value is not the same as previous one
                do {
                    // use Fisher-Yates shuffle to shuffle array
                    int row = random.nextInt(timetable.length);
                    int col = random.nextInt(timetable[row].length);
                    String temp = timetable[i][j];
                    timetable[i][j] = timetable[row][col];
                    timetable[row][col] = temp;
                } while (timetable[i][j].equals(prevVal));
                prevVal = timetable[i][j]; // update previously assigned value
            }
        }
        TableLayout timetableTable = findViewById(R.id.timetable_table);
        for (int i = 0; i < numWorkingDays; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            TextView dayView = new TextView(this);


            dayView.setText(day_arr[i]);
            dayView.setAllCaps(false);
            dayView.setTextSize(15);
            dayView.setTextColor(Color.BLACK);
            dayView.setGravity(Gravity.CENTER_HORIZONTAL);
            dayView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            dayView.setTextColor(getResources().getColor(R.color.green));
            row.addView(dayView);
            for (int j = 0; j < numSlotsPerDay; j++) {
                TextView subjectView = new TextView(this);
                subjectView.setText(timetable[i][j]);
                subjectView.setTextColor(getResources().getColor(R.color.green));
                subjectView.setGravity(Gravity.CENTER_HORIZONTAL);
                subjectView.setPadding(5, 10, 5, 10);
                subjectView.setTextSize(16);
                subjectView.setAllCaps(true);
                row.addView(subjectView);
            }

            timetableTable.addView(row);
        }





        regenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set a fade out animation
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator());
                fadeOut.setDuration(500);

                // Set a listener to recreate the activity when the animation is finished
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        recreate();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                // Apply the animation to the activity's root layout
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                rootView.startAnimation(fadeOut);
            }
        });

    }

    private void openPdf() {
        File file = new File("/sdcard/page.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application.pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No application for Pdf", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

}



