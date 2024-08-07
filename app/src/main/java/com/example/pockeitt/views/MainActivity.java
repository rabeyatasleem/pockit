package com.example.pockeitt.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pockeitt.R;
import com.example.pockeitt.utils.CustomExpandableListAdapter;
import com.example.pockeitt.utils.ExpandableListDataPump;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

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
    TextView textRepeat, text_amount;
    private TextWatcher textWatcher;
    private boolean bottomSheetShown = false;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ConstraintLayout sheet = findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(sheet);
        bottomSheetBehavior.setPeekHeight(600);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        imgRepeat = findViewById(R.id.repeat_circle);
        textRepeat = findViewById(R.id.textView2);
        btnRepeat = findViewById(R.id.mainlayout);
        text_amount = findViewById(R.id.amount_edit);

        expandableListView = findViewById(R.id.expandableListViewSample);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        btnWeekly = findViewById(R.id.button_weekly);
        btnMonthly = findViewById(R.id.button_monthly);
        TabLayout tabLayout = findViewById(R.id.tabLayout);


//        TabLayout.Tab tab0 = tabLayout.getTabAt(0);
//        TabLayout.Tab tab1 = tabLayout.getTabAt(1);


//        tab0.setCustomView(createCustomTabView("Income", 19, R.color.blue));
//        tab1.setCustomView(createCustomTabView("Expense", 19, R.color.blue));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = tab.view;
                tabView.setBackgroundResource(R.drawable.tab_border);
                setTabTextSize(tab, 19, R.color.blue);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View tabView = tab.view;
                tabView.setBackgroundResource(android.R.color.transparent);
                setTabTextSize(tab, 14, R.color.black);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        btnMonthly.setOnClickListener(v -> {
            if (btnMonthly.isEnabled()) {
                btnMonthly.setEnabled(false);
                btnWeekly.setEnabled(true);
                btnMonthly.setBackgroundColor(getResources().getColor(R.color.white));
                btnWeekly.setBackgroundColor(getResources().getColor(R.color.green));
                imgRepeat.setImageResource(R.drawable.repeat_circle_greeen);
                Toast.makeText(this, "Month Selected", Toast.LENGTH_SHORT).show();
            }
        });

        btnWeekly.setOnClickListener(v -> {
            if (btnWeekly.isEnabled()) {
                btnWeekly.setEnabled(false);
                btnMonthly.setEnabled(true);
                btnWeekly.setBackgroundColor(getResources().getColor(R.color.white));
                btnMonthly.setBackgroundColor(getResources().getColor(R.color.green));
                imgRepeat.setImageResource(R.drawable.repeat_circle_greeen);
                Toast.makeText(this, "Week Selected", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupExpandListener(groupPosition -> {
        });

        expandableListView.setOnGroupCollapseListener(groupPosition -> Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show());

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
            return false;
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

    private View createCustomTabView(String tabText, int tabSizeSp, int textColor) {

        View tabCustomView = getLayoutInflater().inflate(R.layout.activity_main, null);
        TextView tabTextView = tabCustomView.findViewById(R.id.tabItem1);
        tabTextView.setText(tabText);
        tabTextView.setTextSize(tabSizeSp);
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.getContext(), textColor));
        return tabCustomView;
    }

    private void setTabTextSize(TabLayout.Tab tab, int tabSizeSp, int textColor) {
        View tabCustomView = tab.getCustomView();
        if (tabCustomView != null) {
            TextView tabTextView = tabCustomView.findViewById(R.id.tabItem1);
            tabTextView.setTextSize(tabSizeSp);
            tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.getContext(), textColor));
        }
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

    private void showBottomSheetDialog() {
        if (!bottomSheetShown) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.bottomsheet);

            bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            bottomSheetDialog.setOnDismissListener(dialog -> bottomSheetShown = false);
            bottomSheetDialog.show();

            bottomSheetShown = true;
        }
    }
}
