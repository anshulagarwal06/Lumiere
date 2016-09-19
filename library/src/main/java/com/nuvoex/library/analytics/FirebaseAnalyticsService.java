package com.nuvoex.library.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;

/**
 * Created by dilip on 05/09/16.
 */
public class FirebaseAnalyticsService implements AnalyticsService {

    private FirebaseAnalytics mFirebaseAnalytics;

    public FirebaseAnalyticsService(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void trackView(String name, Map<String, String> params) {
        Bundle bundle = new Bundle();
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                bundle.putString(key, params.get(key));
            }
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Screen");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    @Override
    public void trackEvent(String name, Map<String, String> params) {
        Bundle bundle = new Bundle();
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                bundle.putString(key, params.get(key));
            }
        }
        mFirebaseAnalytics.logEvent(name, bundle);
    }

    @Override
    public void setProperties(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                mFirebaseAnalytics.setUserProperty(key, params.get(key));
            }
        }
    }

    @Override
    public void setIdentifier(String id) {
        mFirebaseAnalytics.setUserId(id);
    }
}
