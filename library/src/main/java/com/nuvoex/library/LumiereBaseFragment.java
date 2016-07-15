package com.nuvoex.library;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

import icepick.Icepick;

/**
 * All Fragments must extend From BaseFragment.
 * This class adds support for saving state of the activity easily using Icepick and memory leak detection using Leakcanary.
 */
public abstract class LumiereBaseFragment extends Fragment {

    private FragmentInterface mFragmentListener;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentListener().updateToolBarTitle(screenTitle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentListener = (FragmentInterface) context;
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        mFragmentListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // RefWatcher refWatcher = JacksonApplication.getRefWatcher(getActivity());
        //refWatcher.watch(this);
    }

    public FragmentInterface getFragmentListener() {
        return mFragmentListener;
    }

    public void setActivityTitle() {
        getFragmentListener().updateToolBarTitle(screenTitle());
    }

    protected abstract String screenTitle();
}