package com.nuvoex.library.analytics;

import android.content.Context;

/**
 * Created by dilip on 05/09/16.
 */
public class Analytics {

    private static AnalyticsService mAnalyticsService;

    public static AnalyticsService getService(Context context) {
        if (mAnalyticsService == null) {
            mAnalyticsService = new FirebaseAnalyticsService(context);
        }

        return mAnalyticsService;
    }
}
