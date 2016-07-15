package com.nuvoex.baseframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nuvoex.library.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void configureToolbar() {

    }

    @Override
    protected void setUpNavigationView() {

    }

    @Override
    public int getFrameLayoutId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
