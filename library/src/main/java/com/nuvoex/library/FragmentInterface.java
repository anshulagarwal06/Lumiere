package com.nuvoex.library;

import android.support.annotation.ColorRes;

public interface FragmentInterface {

    void addFragment(BaseFragment fragment, boolean addToBackStack, String tag);

    void replaceFragment(BaseFragment fragment, boolean addToBackStack, String tag);

    int getFrameLayoutId();

    String getName();

    void showProgressBar();

    void showProgressBar(@ColorRes int color);

    void showNetworkError(BaseActivity.RetryApiCallback retryApiCallback);

    void showGenericError(String message, BaseActivity.RetryApiCallback retryApiCallback);

    void showActivityContent();

    void updateToolBarTitle(String title);
}