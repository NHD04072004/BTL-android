package com.example.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class ReportActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private EditText editTextDate;
    private ReportViewPagerAdapter reportViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.report);

        tabLayout.addTab(tabLayout.newTab().setText("Chi tiêu"));
        tabLayout.addTab(tabLayout.newTab().setText("Thu nhập"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        reportViewPagerAdapter = new ReportViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(reportViewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        editTextDate = findViewById(R.id.edittextDate);
        String today = DateUtils.getCurrentDateString();
        editTextDate.setText(today);
        editTextDate.setFocusable(false);
        editTextDate.setKeyListener(null);
        editTextDate.setOnClickListener(v -> {
            DatePickerFragment dp = new DatePickerFragment();
            Bundle b = new Bundle();
            b.putInt("targetId", R.id.edittextDate);
            dp.setArguments(b);
            dp.show(getSupportFragmentManager(), "datePicker");
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.input) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            } else if (id == R.id.calendar) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                finish();
                return true;
            } else if (id == R.id.budget) {
                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
                finish();
                return true;
            } else return id == R.id.report;
        });
    }
}