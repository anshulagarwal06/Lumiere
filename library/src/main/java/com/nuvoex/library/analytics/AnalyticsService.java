package com.nuvoex.library.analytics;

import android.os.Bundle;

/**
 * Created by dilip on 05/09/16.
 */
public interface AnalyticsService {

    void trackView(String name, Bundle params);

    void trackEvent(String name, Bundle params);
}
