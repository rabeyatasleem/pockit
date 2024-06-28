package com.example.pockeitt.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pockeitt.R;
import com.example.pockeitt.utils.CustomExpandableListAdapter;
import com.example.pockeitt.utils.ExpandableListDataPump;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    Button btnWeekly, btnMonthly;
    ConstraintLayout btnRepeat;
    ImageView imgRepeat;
    TextView textRepeat;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imgRepeat = findViewById(R.id.repeat_circle);
        textRepeat = findViewById(R.id.textView2);
        btnRepeat = findViewById(R.id.mainlayout);
        ConstraintLayout sheet = findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(sheet);
        bottomSheetBehavior.setPeekHeight(600);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewSample);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        btnWeekly = findViewById(R.id.button_weekly);
        btnMonthly = findViewById(R.id.button_monthly);

        btnMonthly.setOnClickListener(v -> {
            if (btnMonthly.isEnabled()) {
                btnMonthly.setEnabled(false);
                btnWeekly.setEnabled(true);
                btnMonthly.setBackgroundColor(getResources().getColor(R.color.white));
                btnWeekly.setBackgroundColor(getResources().getColor(R.color.green));
                imgRepeat.setColorFilter(Color.GREEN);
                imgRepeat.setBackgroundColor(getResources().getColor(R.color.green));
                Toast.makeText(this, "Month Selected", Toast.LENGTH_SHORT).show();
            }
        });

        btnWeekly.setOnClickListener(v -> {
            if (btnWeekly.isEnabled()) {
                btnWeekly.setEnabled(false);
                btnMonthly.setEnabled(true);
                btnWeekly.setBackgroundColor(getResources().getColor(R.color.white));
                btnMonthly.setBackgroundColor(getResources().getColor(R.color.green));
//                imgRepeat.setColorFilter(Color.GREEN);
//                imgRepeat.setBackgroundColor(getResources().getColor(R.color.green));
//                DrawableCompat.setTint(imgRepeat.getDrawable(),ContextCompat.getColor(getApplicationContext(), R.color.green));
                Toast.makeText(this, "Week Selected", Toast.LENGTH_SHORT).show();
            }
        });

////        btnRepeat.setOnClickListener(v -> {
//
//            if (btnWeekly.isEnabled() || btnMonthly.isEnabled()) {
//                imgRepeat.setImageResource(R.color.black);
//                textRepeat.setTextColor(Color.BLACK);
//            } else{
//                imgRepeat.setBackgroundColor(Color.GRAY);
//                textRepeat.setTextColor(Color.GRAY);
//            }
//        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(groupPosition -> Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show());

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        initDatepicker();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            dateButton = findViewById(R.id.calender_btn);
            dateButton.setText(getTodaysDate());
            return insets;

        });
    }


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatepicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = makeDateString(dayOfMonth, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "March";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "Jan";
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

}
