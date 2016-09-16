package com.nuvoex.library.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by dilip on 05/09/16.
 */
public class FirebaseAnalyticsService implements AnalyticsService {

    private FirebaseAnalytics mFirebaseAnalytics;

    public FirebaseAnalyticsService(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void trackView(String name, Bundle params) {
        Bundle bundle = new Bundle();
        if (params != null) {
            bundle.putAll(params);
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "screen");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    @Override
    public void trackEvent(String name, Bundle params) {
        Bundle bundle = new Bundle();
        if (params != null) {
            bundle.putAll(params);
        }
        mFirebaseAnalytics.logEvent(name, bundle);
    }
}
