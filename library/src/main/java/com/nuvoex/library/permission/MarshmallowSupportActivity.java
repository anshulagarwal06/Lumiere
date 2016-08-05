package com.nuvoex.library.permission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.nuvoex.library.R;

/**
 * Created by KhushbooGupta on 6/24/16.
 */
public abstract class MarshmallowSupportActivity extends AppCompatActivity {


    public Permission.FragmentPermissionCallback mFragmentPermissionCallback = null;
    private Permission mPermission = null;

    public void requestAppPermissions(@NonNull Permission permission) {
        mPermission = permission;
        mFragmentPermissionCallback = mPermission.mFragmentPermissionCallback;
        requestAppPermissions(mPermission.requestedPermissions, mPermission.requestCode, mPermission.mFragmentPermissionCallback);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (verifyPermissions(grantResults)) {
            mFragmentPermissionCallback.onPermissionGranted(requestCode);
        } else {
            boolean showRationale = shouldShowRequestPermissionRationale(permissions);
            if (!showRationale) {
                doNotAskedEnable(requestCode);
            } else {
                showRationalMessage(requestCode);
            }
        }

    }

    private void requestAppPermissions(final String[] requestedPermissions, final int requestCode, @Nullable Permission.FragmentPermissionCallback permissionCallback) {
        if (!hasPermissions(requestedPermissions)) {
            if (shouldShowRequestPermissionRationale(requestedPermissions)) {
                showRationalMessage(requestCode);
            } else {
                ActivityCompat.requestPermissions(MarshmallowSupportActivity.this, requestedPermissions, requestCode);
            }
        } else {
            mFragmentPermissionCallback.onPermissionGranted(requestCode);
        }
    }

    protected boolean hasPermissions(String[] permissions) {
        int length = permissions.length;
        for (int i = 0; i < length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    public boolean shouldShowRequestPermissionRationale(String[] permissions) {
        int length = permissions.length;
        for (int i = 0; i < length; i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]))
                return true;
        }
        return false;
    }

    private boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1)
            return false;

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private void showRequestPermissionDialog() {
        String message = mPermission.rationalDialogMessage;
        String positiveButton = getString(R.string.rational_permission_proceed);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        if (!TextUtils.isEmpty(mPermission.rationalDialogTitle)) {
            alertDialogBuilder.setTitle(mPermission.rationalDialogTitle);

        }
        alertDialogBuilder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                ActivityCompat.requestPermissions(MarshmallowSupportActivity.this, mPermission.requestedPermissions, mPermission.requestCode);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void showRationalMessage(int requestCode) {

        if (mPermission.showCustomRationalDialog) {
            mFragmentPermissionCallback.OnPermissionDenied(requestCode);
        } else {
            showRequestPermissionDialog();
        }
    }

    private void doNotAskedEnable(int requestCode) {

        if (mPermission.showCustomSettingDialog) {
            mFragmentPermissionCallback.OnPermissionAccessRemoved(requestCode);
        } else {
            showSettingsPermissionDialog();
        }
    }

    private void showSettingsPermissionDialog() {

        String message = mPermission.settingDialogMessage;
        String positiveButton = getString(R.string.permission_string_btn);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        if (!TextUtils.isEmpty(mPermission.settingDialogTitle)) {
            alertDialogBuilder.setTitle(mPermission.settingDialogTitle);

        }
        alertDialogBuilder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startSettingActivity();
            }
        });

        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();

    }

    public void startSettingActivity() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

}
