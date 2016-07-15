package com.nuvoex.library;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import icepick.Icepick;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity implements FragmentInterface,
        NavigationView.OnNavigationItemSelectedListener {

    /**
     * Inspiration - http://mateoj.com/2015/06/21/adding-toolbar-and-navigation-drawer-all-activities-android/
     */

    // @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    //@BindView(R.id.navigation_view)
    protected NavigationView mNavigationView;

    //@BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;

    //@BindView(R.id.app_bar)
    protected AppBarLayout mAppBarLayout;

    //@BindView(R.id.coordinator_layout)
    protected CoordinatorLayout mCoordinatorLayout;

    // @BindView(R.id.progressbar_container)
    protected LinearLayout mProgressBarContainer;

    //    @BindView(R.id.network_error_container)
    protected LinearLayout mNetworkErrorContainer;

    // @BindView(R.id.text_error_title)
    protected TextView mTextErrorTitle;

    // @BindView(R.id.activity_container)
    protected FrameLayout mActivityContainer;

    protected static final int NONE = 0;
    protected int mActivityLayoutResourceId = NONE;
    protected int mFragmentReplacementContentViewId = NONE;//TODO is this needed?
    protected ActionBarDrawerToggle mDrawerToggle;

    private RetryApiCallback mRetryApiCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void setContentView(int layoutResourceId) {

        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);

        if (layoutResourceId != NONE) {
            FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_container);
            getLayoutInflater().inflate(layoutResourceId, activityContainer, true);
            mActivityLayoutResourceId = layoutResourceId;
        }

        super.setContentView(fullView);

        //ButterKnife.bind(this);
        initView();

        if (!useNavDrawer()) {
            fullView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
            setupDrawerContent(mNavigationView);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            setUpNavigationView();
        }

        //sanity check
        if (appBarInUse()) {
            if (useToolbar()) {
                setSupportActionBar(mToolbar);
                configureToolbar();
            } else {
                mToolbar.setVisibility(View.GONE);
            }
        } else {
            mAppBarLayout.setVisibility(View.GONE);
        }

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        mProgressBarContainer = (LinearLayout) findViewById(R.id.progressbar_container);

        mNetworkErrorContainer = (LinearLayout) findViewById(R.id.network_error_container);

        mTextErrorTitle = (TextView) findViewById(R.id.text_error_title);

        mActivityContainer = (FrameLayout) findViewById(R.id.activity_container);

        mNetworkErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryApiCall();
            }
        });

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (useNavDrawer() && mDrawerToggle != null)
            mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (useNavDrawer() && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void addFragment(BaseFragment fragmentToBeLoaded, boolean addToBackStack, String tag) {
        boolean allowStateLoss = true;

        if (!getSupportFragmentManager().isDestroyed()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction
                    .add(getFrameLayoutId(), fragmentToBeLoaded, tag);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            if (allowStateLoss) {
                fragmentTransaction.commitAllowingStateLoss();
            } else {
                fragmentTransaction.commit();
            }
        } else {
            Timber.e(getName(), "addFragment: Actvity Destroyed, won't perform FT to load" +
                    " Fragment " + tag);
        }
    }

    @Override
    public void replaceFragment(BaseFragment fragmentToBeLoaded, boolean addToBackStack, String tag) {
        boolean allowStateLoss = true;

        if (!getSupportFragmentManager().isDestroyed()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(getFrameLayoutId(), fragmentToBeLoaded,
                    tag);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            if (allowStateLoss) {
                fragmentTransaction.commitAllowingStateLoss();
            } else {
                fragmentTransaction.commit();
            }
        } else {
            Timber.e(getName(), "replaceFragment: Actvity Destroyed, won't perform FT to load" +
                    " Fragment " + tag);
        }
    }

    @Override
    public void showProgressBar() {
        showProgressBarWithBackgroundColor(R.color.progress_background);
    }

    @Override
    public void showProgressBar(@ColorRes int color) {
        showProgressBarWithBackgroundColor(color);
    }


    @Override
    public void showNetworkError(RetryApiCallback retryApiCallback) {
        mRetryApiCallback = retryApiCallback;
        mTextErrorTitle.setText(getString(R.string.network_error_title));
        setContainerVisibility(false, true);
    }

    @Override
    public void showGenericError(String message, RetryApiCallback retryApiCallback) {
        mRetryApiCallback = retryApiCallback;
        setContainerVisibility(false, true);
        mTextErrorTitle.setText(message);
    }

    @Override
    public void showActivityContent() {
        setContainerVisibility(false, false);
    }

    @Override
    public void updateToolBarTitle(String title) {
        if (appBarInUse()) {
            getSupportActionBar().setTitle(title);
        }
    }

    private boolean appBarInUse() {
        return (useToolbar() && useAppBar());
    }

    protected boolean useAppBar() {
        return true;
    }

    protected boolean useToolbar() {
        return true;
    }

    protected boolean useNavDrawer() {
        return true;
    }

    protected boolean selectDrawerItem(MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        //Return false so that menu item wont be shown as selected
        return false;
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return mCoordinatorLayout;
    }


    abstract protected void configureToolbar();

    abstract protected void setUpNavigationView();


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        return selectDrawerItem(menuItem);
                    }
                });
    }

    private void setContainerVisibility(boolean showProgressbar, boolean showNetworkError) {
        mProgressBarContainer.setVisibility(showProgressbar ? View.VISIBLE : View.GONE);
        mNetworkErrorContainer.setVisibility(showNetworkError ? View.VISIBLE : View.GONE);
    }

    private void showProgressBarWithBackgroundColor(@ColorRes int color) {
        mProgressBarContainer.setBackgroundColor(ContextCompat.getColor(BaseActivity.this, color));
        setContainerVisibility(true, false);
    }

    public void retryApiCall() {
        Timber.d("Retry API call");
        mRetryApiCallback.retry();
    }

    public interface RetryApiCallback {
        void retry();
    }
}
