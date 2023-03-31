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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

public class MainActivity4 extends AppCompatActivity {
    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bmp, scaledbmp;
    private static final int PERMISSION_REQUEST_CODE = 200;

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
        TableLayout timetableTable = findViewById(R.id.timetable_table);
        for (int i = 0; i < numWorkingDays; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            TextView dayView = new TextView(this);

            dayView.setText(day_arr[i]);
            dayView.setAllCaps(false);
            dayView.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(dayView);

            for (int j = 0; j < numSlotsPerDay; j++) {

                TextView subjectView = new TextView(this);
                subjectView.setText(timetable[i][j]);
                subjectView.setGravity(Gravity.CENTER_HORIZONTAL);
                subjectView.setAllCaps(true);
                row.addView(subjectView);
            }

            timetableTable.addView(row);
        }

        /*AppCompatButton pdf_button = findViewById(R.id.pdf_button);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg_create_new_activity);
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
        });*/

    }
    /*private void generatePDF(){
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
        canvas.drawText("A portal for IT professionals.", 209, 100, title);
        canvas.drawText("Geeks for Geeks", 209, 80, title);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
        title.setTextSize(15);

        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);
        pdfDocument.finishPage(myPage);
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");
        try {
            // after creating a file name we will
            // write our PDF file to that location.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pdfDocument.writeTo(Files.newOutputStream(file.toPath()));
            }

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(MainActivity4.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();

    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
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
    }*/

}



