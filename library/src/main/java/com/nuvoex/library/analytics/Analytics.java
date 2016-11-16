package com.nuvoex.library.analytics;

import android.content.Context;

import java.util.Map;

/**
 * Created by dilip on 05/09/16.
 */
public class Analytics {

    private static AnalyticsService mAnalyticsService;

    public static void initialize(Context context) {
        if (mAnalyticsService == null) {
            mAnalyticsService = new FirebaseAnalyticsService(context.getApplicationContext());
        }
    }

    public static AnalyticsService getService() {
        if (mAnalyticsService == null) {
            throw new RuntimeException("Analytics must be initialized first");
        }

        return mAnalyticsService;
    }

    public static void trackEvent(String event, Map<String, String> params) {
        getService().trackEvent(event, params);
    }

    public static void trackView(String view, Map<String, String> params) {
        getService().trackView(view, params);
    }

    public static void setProperties(Map<String, String> params) {
        getService().setProperties(params);
    }

    public static void setIdentifier(String id) {
        getService().setIdentifier(id);
    }
}
