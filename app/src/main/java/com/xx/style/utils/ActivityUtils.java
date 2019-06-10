package com.xx.style.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XX.
 */

public class ActivityUtils {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
