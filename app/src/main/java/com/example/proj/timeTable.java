package com.example.proj;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

public class timeTable extends AppCompatActivity {
    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bmp, scaledbmp;
    ConstraintLayout layout ;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);



        Drawable drawable = getDrawable(R.drawable.edit_text_border);
        Intent intent = getIntent();
        TableLayout tableLayout = findViewById(R.id.gridlay);

        SharedPreferences preferences_from_main = getSharedPreferences("Main", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main2 = getSharedPreferences("Main2", Context.MODE_PRIVATE);
        SharedPreferences preferences_from_main3 = getSharedPreferences("Main3", Context.MODE_PRIVATE);

        TextView classname= findViewById(R.id.className);
        classname.setText(preferences_from_main.getString("className","class name" ));
        // Define timetable variables
        int numSubjects = preferences_from_main2.getInt("s" ,5);
        int numWorkingDays =preferences_from_main2.getInt("wd", 5);
        int numSlotsPerDay =  preferences_from_main2.getInt("l", 5);

        int[] NUMLECT ={5,5,5,5,5};
        String[] SUBLIST = {"Network and information security.  ","Wireless mobile application. ", "Management ", "Emerging dreams. " ," Mobile application developer. "};


        /*int[] NUMLECT =intent.getIntArrayExtra("lectnum_array");
        String[] SUBLIST = intent.getStringArrayExtra("subname_array");*/
        String[] day_arr = new String[]{"Monday" , "Tuesday" , "Wednesday" , "Thursday" , "Friday" , "Saturday" , "Sunday"};

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
            dayView.setTextColor(Color.BLACK);
            dayView.setGravity(Gravity.CENTER_HORIZONTAL);
            dayView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            row.addView(dayView);
            for (int j = 0; j < numSlotsPerDay; j++) {
                TextView subjectView = new TextView(this);
                subjectView.setText(timetable[i][j]);
                subjectView.setTextColor(Color.BLACK);
                subjectView.setGravity(Gravity.CENTER_HORIZONTAL);
                subjectView.setPadding(5,10,5,10);
                subjectView.setAllCaps(true);
                row.addView(subjectView);
            }

            timetableTable.addView(row);
        }

        AppCompatButton pdf_button = findViewById(R.id.pdf_button);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        pdf_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF();
            }
        });

    }
    @SuppressLint("WrongViewCast")
    private void generatePDF(){
        layout = findViewById(R.id.layout);

        bmp = LoadBitmap(layout , layout.getWidth() ,layout.getHeight());


        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth,pageHeight,1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(scaledbmp, 56, 40, paint);
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(15);
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
        canvas.drawText("Hello", 209, 100, title);
        canvas.drawText("Time Table Generator", 209, 80, title);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
        title.setTextSize(15);

        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(" Automated TimeTable Generator", 396, 560, title);
        pdfDocument.finishPage(myPage);
        File file = new File(Environment.getExternalStorageDirectory(), "timetable.pdf");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pdfDocument.writeTo(Files.newOutputStream(file.toPath()));
            }
            Toast.makeText(timeTable.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();

    }

    private Bitmap LoadBitmap(View v, int maxWidth, int maxHeight) {
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(),layout.getHeight(), Bitmap.Config.ARGB_8888);
        return bitmap;
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0)
            {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}



