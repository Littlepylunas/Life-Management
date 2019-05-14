package com.example.lifemanagement;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;


public class UserActivity extends AppCompatActivity {
    DashBoardFragment dashboardFrangment;
    HistoryFragment historyFragment;
    OtherFragment otherFragment;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        String text = "Nhấn back một lần nữa để thoát ứng dụng";
        SpannableString span = new SpannableString(text);
        span.setSpan(new ForegroundColorSpan(Color.GREEN), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Toast toast = new Toast(UserActivity.this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        Toast.makeText(UserActivity.this, span, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @SuppressLint("StaticFieldLeak")
    public static BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(dashboardFrangment);
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(historyFragment);
                    return true;
                case R.id.navigation_notifications:
                    setFragment(otherFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // Assign variable here
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        dashboardFrangment = new DashBoardFragment();
        otherFragment = new OtherFragment();
        historyFragment = new HistoryFragment();

        // Call function here
        setFragment(dashboardFrangment);
        //
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

}
