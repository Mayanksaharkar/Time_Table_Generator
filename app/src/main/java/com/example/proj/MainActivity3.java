package com.example.proj;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity3 extends AppCompatActivity {

    GridLayout gridLayout;
    private LinearLayout layoutRowContainer;
    private Button btnAddRow;
    private Button btnSubmit;
    private ArrayList<RowValues> rowValuesList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        layoutRowContainer = findViewById(R.id.layout_row_container);
        btnAddRow = findViewById(R.id.btn_add_row);
        btnSubmit = findViewById(R.id.btn_submit);

        btnAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeValues();

                    gridLayout = findViewById(R.id.gridlay);
                    int numRows = rowValuesList.size();
                    int numCols = 2;

// Loop through the rows and columns and add TexdtView objects to the GridLayout

                for (RowValues rowValues : rowValuesList ) {
                    for (int i = 0 ; i< numRows ;i++) {
                        String editTextValue = rowValues.getEditTextValue();
                        String spinnerValue = rowValues.getSpinnerValue();
                        // Display the values in a TextView or another UI component


                        TextView textView1 = new TextView(MainActivity3.this);
                        textView1.setText(editTextValue);
                        TextView textView2 = new TextView(MainActivity3.this);
                        textView2.setText(spinnerValue);

                        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams();
                        params1.columnSpec = GridLayout.spec(0); // Column 0
                        params1.rowSpec = GridLayout.spec(0); // Row 0
                        textView1.setLayoutParams(params1);

                        GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                        params2.columnSpec = GridLayout.spec(1); // Column 1
                        params2.rowSpec = GridLayout.spec(0); // Row 0
                        textView2.setLayoutParams(params2);

                        gridLayout.addView(textView1);
                        gridLayout.addView(textView2);
                    }


                    /* for (int row = 0; row < numRows; row++) {
                        for (int col = 0; col < numCols; col++) {
                            // Create a new TextView object
                            TextView textView = new TextView(MainActivity3.this);
                            textView.setText(editTextValue);
                            TextView textView2 = new TextView(MainActivity3.this);
                            textView.setText(spinnerValue);


                            // Define the row and column position for the TextView


                            // Add the TextView to the GridLayout

                        }

                    // Display the values in a TextView or another UI component
                }*/
            }
            }

        });
    }



    private void addRow() {
        // Create a new row layout
        LinearLayout layoutRow = new LinearLayout(this);
        layoutRow.setOrientation(LinearLayout.HORIZONTAL);
        layoutRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Create a new EditText
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        ));
        layoutRow.addView(editText);

        // Create a new Spinner
        Spinner spinner = new Spinner(this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        ));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_values,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        layoutRow.addView(spinner);

        // Create a new "Remove Row" button
        Button btnRemoveRow = new Button(this);
        btnRemoveRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        btnRemoveRow.setText("Remove Row");
        btnRemoveRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRow(layoutRow);
            }
        });
        layoutRow.addView(btnRemoveRow);

        // Add the new row layout to the container layout
        layoutRowContainer.addView(layoutRow);
    }

    private void removeRow(LinearLayout layoutRow) {
        // Remove the row layout from the container layout
        layoutRowContainer.removeView(layoutRow);
    }

    private void storeValues() {
        // Clear the existing row values list
        rowValuesList.clear();

        // Iterate through all the row layouts in the container layout
        for (int i = 0; i < layoutRowContainer.getChildCount(); i++) {
            LinearLayout layoutRow = (LinearLayout) layoutRowContainer.getChildAt(i);

            // Extract values from the row layout
            EditText editText = (EditText) layoutRow.getChildAt(0);
            Spinner spinner = (Spinner) layoutRow.getChildAt(1);
            String editTextValue = editText.getText().toString().trim();
            String spinnerValue = spinner.getSelectedItem().toString();

            // Check if the user entered a value in the EditText field
            if (editTextValue.isEmpty()) {
                // Show an error message and return without storing the values
                Toast.makeText(this, "Please enter a value for all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new RowValues object and add it to the list
            RowValues rowValues = new RowValues(editTextValue, spinnerValue);
            rowValuesList.add(rowValues);
        }

        // Show a success message and clear the EditText fields

        Toast.makeText(this, "Values stored successfully"+rowValuesList, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }

    private void clearEditTextFields() {
        // Iterate through all the row layouts in the container layout
        for (int i = 0; i < layoutRowContainer.getChildCount(); i++) {
            LinearLayout layoutRow = (LinearLayout) layoutRowContainer.getChildAt(i);
            EditText editText = (EditText) layoutRow.getChildAt(0);
            editText.setText("");
        }
    }
    public class RowValues {
        private String editTextValue;
        private String spinnerValue;

        public RowValues(String editTextValue, String spinnerValue) {
            this.editTextValue = editTextValue;
            this.spinnerValue = spinnerValue;
        }

        public String getEditTextValue() {
            return editTextValue;
        }

        public String getSpinnerValue() {
            return spinnerValue;
        }

        public void setEditTextValue(String editTextValue) {
            this.editTextValue = editTextValue;

        }

        public void setSpinnerValue(String spinnerValue) {
            this.spinnerValue = spinnerValue;
        }
    }

}