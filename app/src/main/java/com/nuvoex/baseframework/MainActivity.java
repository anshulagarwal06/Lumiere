package com.nuvoex.baseframework;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.nuvoex.library.LumiereBaseActivity;
import com.nuvoex.library.analytics.Analytics;
import com.nuvoex.library.analytics.AnalyticsService;
import com.nuvoex.library.permission.Permission;
import com.nuvoex.library.permission.Permission.PermissionBuilder;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends LumiereBaseActivity implements Permission.PermissionCallback {

    private final static String TAG= MainActivity.class.getSimpleName();

    private static final int PHOTO_ACTIVITY_REQUEST_CARMERA_AND_READ_WRITE = 50;
    private static final String[] PHOTO_ACTIVITY_CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionCamera();

        AnalyticsService service = Analytics.getService(this);
        Map<String, String> params = new HashMap<>();
        service.trackView("Home", params);
    }

    private void checkPermissionCamera() {

        PermissionBuilder permissionBuilder = new PermissionBuilder(PHOTO_ACTIVITY_CAMERA_PERMISSIONS, PHOTO_ACTIVITY_REQUEST_CARMERA_AND_READ_WRITE, this);
        permissionBuilder.enableDefaultRationalDialog("ration","dsadasdsada")
                .enableDefaultSettingDialog("Setting", "eaeqwe wqeew qweqwe");
        requestAppPermissions(permissionBuilder.build());
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

    @Override
    public void onPermissionGranted(int requestCode) {
        Log.i("TAG", "onPermissionGranted : " + requestCode);

    }

    @Override
    public void onPermissionDenied(int requestCode) {

        Log.i("TAG", "onPermissionDenied : " + requestCode);

    }

    @Override
    public void onPermissionAccessRemoved(int requestCode) {
        Log.i("TAG", "onPermissionAccessRemoved : " + requestCode);
    }
}
