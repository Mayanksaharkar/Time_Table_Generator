package com.example.proj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class sub_name extends AppCompatActivity {
    public TableLayout tableLayout;
    LinearLayout layoutRow;
    private LinearLayout layoutRowContainer;
    private Button btnAddRow;
    private Button btnSubmit, clear, generate;
    TextView textView1, textView2;
    public static ArrayList<RowValues> rowValuesList = new ArrayList<>();
    AlertDialog alert;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_name);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.offwhite));
        }


        layoutRowContainer = findViewById(R.id.layout_row_container);
        btnSubmit = findViewById(R.id.btn_submit);
        clear = findViewById(R.id.clear);
        generate = findViewById(R.id.generate);

        SharedPreferences preferences_from_main2 = getSharedPreferences("Main2", Context.MODE_PRIVATE);

        textView1 = findViewById(R.id.textview1);
        textView2 = findViewById(R.id.textview2);
        int num_of_sub = preferences_from_main2.getInt("s", 1);
        for (int i = 0; i < (num_of_sub + 1); i++) {
            addRow();
        }
        int[] countList_array = new int[num_of_sub];
        String[] subList_array = new String[num_of_sub];
        int rowCount = num_of_sub;

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Iterate through all rows and check if all EditTexts are empty
                boolean allEditTextEmpty = true;
                for (int i = 0; i < layoutRowContainer.getChildCount(); i++) {
                    View view = layoutRowContainer.getChildAt(i);
                    if (view instanceof LinearLayout) {
                        LinearLayout layoutRow = (LinearLayout) view;
                        for (int j = 0; j < layoutRow.getChildCount(); j++) {
                            View childView = layoutRow.getChildAt(j);
                            if (childView instanceof EditText) {
                                EditText editText = (EditText) childView;
                                if (!editText.getText().toString().trim().isEmpty()) {
                                    allEditTextEmpty = false;
                                    break;
                                }
                            }
                        }
                        if (!allEditTextEmpty) {
                            break;
                        }
                    }
                }

                // Check if the value of any spinner is 1
                boolean spinnerValueOne = false;
                for (int i = 0; i < layoutRowContainer.getChildCount(); i++) {
                    View view = layoutRowContainer.getChildAt(i);
                    if (view instanceof LinearLayout) {
                        LinearLayout layoutRow = (LinearLayout) view;
                        for (int j = 0; j < layoutRow.getChildCount(); j++) {
                            View childView = layoutRow.getChildAt(j);
                            if (childView instanceof Spinner) {
                                Spinner spinner = (Spinner) childView;
                                if (spinner.getSelectedItemPosition() == 0) {
                                    spinnerValueOne = true;
                                    break;
                                }
                            }
                        }
                        if (spinnerValueOne) {
                            break;
                        }
                    }
                }

                // Display toast message if all EditTexts are empty or if any spinner value is 1
                if (allEditTextEmpty || spinnerValueOne) {
                    String message = "Please enter values for all fields and make sure none of the spinners has a value of 0";
                    Toast.makeText(sub_name.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }

                new AlertDialog.Builder(sub_name.this)
                        .setTitle("Confirm Submission")
                        .setMessage("Are you sure you want to submit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                textView1.setVisibility(View.VISIBLE);
                                textView2.setVisibility(View.VISIBLE);
                                generate.setVisibility(View.VISIBLE);
                                layoutRow.removeAllViews();


                                storeValues();

                                tableLayout = findViewById(R.id.gridlay);
                                int numRows = rowValuesList.size();
                                int numCols = 2;


                                for (RowValues rowValues : rowValuesList) {

                                    String editTextValue = rowValues.getEditTextValue();
                                    String spinnerValue = rowValues.getSpinnerValue();

                                    TextView editTextTextView = new TextView(sub_name.this);
                                    editTextTextView.setText(editTextValue);

                                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, // Width
                                            TableLayout.LayoutParams.WRAP_CONTENT,
                                            1);
                                    editTextTextView.setLayoutParams(layoutParams);
                                    TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1);
                                    editTextTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    editTextTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    editTextTextView.setTextColor(getResources().getColor(R.color.offwhite));

                                    TextView spinnerTextView = new TextView(sub_name.this);
                                    spinnerTextView.setLayoutParams(layoutParams2);
                                    spinnerTextView.setText(spinnerValue);
                                    spinnerTextView.setTextColor(getResources().getColor(R.color.offwhite));

                                    TableRow tableRow = new TableRow(sub_name.this);
                                    spinnerTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    spinnerTextView.setText(spinnerValue);
                                    tableRow.addView(editTextTextView);
                                    tableRow.addView(spinnerTextView);

                                    tableLayout.addView(tableRow);
                                }
                                submitData();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }


        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < rowCount; i++) {
                    TableRow row = (TableRow) tableLayout.getChildAt(i);
                    TextView cell = (TextView) row.getChildAt(0);

                    subList_array[i] = cell.getText().toString();
                }
                for (int i = 0; i < rowCount; i++) {
                    TableRow row = (TableRow) tableLayout.getChildAt(i);
                    TextView cell = (TextView) row.getChildAt(1);
                    countList_array[i] = Integer.parseInt(cell.getText().toString());
                }
                Intent intent_to_Activity4 = new Intent(sub_name.this, timeTable.class);
                intent_to_Activity4.putExtra("lectnum_array", countList_array);
                intent_to_Activity4.putExtra("subname_array", subList_array);

                startActivity(intent_to_Activity4);
            }
        });
        removeRow(layoutRow);


    }

    private void addRow() {
        // Create a new row layout
        layoutRow = new LinearLayout(this);
        layoutRow.setOrientation(LinearLayout.HORIZONTAL);
        layoutRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Create a new EditText
        EditText editText = new EditText(this);
        editText.setBackground(ContextCompat.getDrawable(sub_name.this, R.drawable.edit_text_border_for_sub_name));
        editText.setHint("Subject Name");
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.editText_width),
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        editText.setPadding(20, 20, 0, 15);
        editText.setHintTextColor(ContextCompat.getColor(sub_name.this, R.color.white));
        editText.setTextColor(ContextCompat.getColor(sub_name.this, R.color.white));

        editText.setLayoutParams(new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.editText_width),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        ));
        layoutRow.addView(editText);
        Space space = new Space(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium),
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        layoutRow.addView(space);

        // Create a new Spinner
        Spinner spinner = new Spinner(this);
        spinner.setBackground(ContextCompat.getDrawable(sub_name.this, R.drawable.edit_text_border_for_sub_name));


        ViewGroup.LayoutParams params = spinner.getLayoutParams();
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
        ));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                sub_name.this,
                R.array.spinner_values,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        layoutRow.addView(spinner);

        // Create a new "Remove Row" button


        // Add the new row layout to the container layout
        layoutRowContainer.addView(layoutRow);
    }

    private void submitData() {
        // Submit data to databasre or server
        Toast.makeText(this, "Data submitted successfully!", Toast.LENGTH_SHORT).show();

    }

    private void removeRow(LinearLayout layoutRow) {
        layoutRowContainer.removeView(layoutRow);
        layoutRowContainer.removeView(layoutRow);
    }

    private void storeValues() {
        // Clear the existing row values list
        rowValuesList.clear();

        for (int i = 0; i < layoutRowContainer.getChildCount(); i++) {
            LinearLayout layoutRow = (LinearLayout) layoutRowContainer.getChildAt(i);

            // Extract values from the row layout
            EditText editText = (EditText) layoutRow.getChildAt(0);
            Spinner spinner = (Spinner) layoutRow.getChildAt(2);
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

        clearEditTextFields();
    }

    private void clearEditTextFields() {
        // Iterate through all the row layouts in the container layout
        for (int i = 0; i < layoutRowContainer.getChildCount(); i++) {
            LinearLayout layoutRow = (LinearLayout) layoutRowContainer.getChildAt(i);
            EditText editText = (EditText) layoutRow.getChildAt(0);
            editText.setText("");


        }
        layoutRowContainer.removeAllViews();
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