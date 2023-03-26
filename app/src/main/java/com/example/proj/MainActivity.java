package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
/*
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;*/
/*import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;*/

public class MainActivity extends AppCompatActivity {
AppCompatButton next_button_to_activity2;
EditText classname;
String className;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classname = findViewById(R.id.class_name_edittext);




       /* String fileName = "timetable.pdf";
        PdfWriter writer;
        try {
            writer = new PdfWriter(fileName);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // Set the font
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // Create a new table
            Table table = new Table(new float[]{3, 3, 3, 3});

            // Add the timetable to the table
            String[][] timetable = {{"Math (John)", "Science (Mary)", "Math (John)", "English (Mark)"},
                    {"Science (Mary)", "Math (John)", "English (Mark)", null},
                    {"Math (John)", "English (Mark)", "Science (Mary)", "Math (John)"},
                    {"English (Mark)", null, "Math (John)", "Science (Mary)"},
                    {null, "Science (Mary)", "English (Mark)", "Math (John)"}};
            for (String[] rowArray : timetable) {
                for (String cellValue : rowArray) {
                    Cell cell = new Cell();
                    cell.setFont(font);
                     cell.setTextAlignment(TextAlignment.CENTER);
                    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    table.addCell(cell);
                }
            }

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            System.out.println("PDF file written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
       /* String[][] data = {{"Name", "Age", "Gender"},
                {"John", "30", "Male"},
                {"Jane", "25", "Female"}};

        // create a new Excel workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data");

        // populate the sheet with data from the 2D array
        int rownum = 0;
        for (String[] rowdata : data) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (String cellvalue : rowdata) {
                Cell cell = row.createCell(cellnum++);
                cell.setCellValue(cellvalue);
            }
        }

        // write the workbook to an output stream
        try (FileOutputStream outputStream = new FileOutputStream("data.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data exported successfully to Excel sheet.");


*/


        next_button_to_activity2 = (AppCompatButton) findViewById(R.id.next_button_to_activity2);
        next_button_to_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                    SharedPreferences preferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("className", classname.getText().toString());
                    editor.commit();
                }
                Intent intent = new Intent(MainActivity.this ,MainActivity2.class);

                startActivity(intent);
            }
        });






    }





}




