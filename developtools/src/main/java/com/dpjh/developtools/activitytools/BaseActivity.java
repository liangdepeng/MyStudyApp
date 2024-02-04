package com.dpjh.developtools.activitytools;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private final static List<Activity> activities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        activities.remove(this);
        super.onDestroy();
    }

    public static Activity getTopActivity() {
        if (activities.isEmpty())
            return null;
        return activities.get(activities.size() - 1);
    }
}
