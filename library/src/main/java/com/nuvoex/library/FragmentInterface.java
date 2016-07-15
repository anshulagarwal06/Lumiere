package com.nuvoex.library;

import android.support.annotation.ColorRes;

public interface FragmentInterface {

    void addFragment(LumiereBaseFragment fragment, boolean addToBackStack, String tag);

    void replaceFragment(LumiereBaseFragment fragment, boolean addToBackStack, String tag);

    int getFrameLayoutId();

    String getName();

    void showProgressBar();

    void showProgressBar(@ColorRes int color);

    void showNetworkError(LumiereBaseActivity.RetryApiCallback retryApiCallback);

    void showGenericError(String message, LumiereBaseActivity.RetryApiCallback retryApiCallback);

    void showActivityContent();

    void updateToolBarTitle(String title);
}