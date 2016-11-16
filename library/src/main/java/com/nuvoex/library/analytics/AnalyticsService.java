package com.nuvoex.library.analytics;

import android.os.Bundle;

import java.util.Map;

/**
 * Created by dilip on 05/09/16.
 */
public interface AnalyticsService {

    void trackView(String name, Map<String, String> params);

    void trackEvent(String name, Map<String, String> params);

    void setProperties(Map<String, String> params);

    void setIdentifier(String id);
}
