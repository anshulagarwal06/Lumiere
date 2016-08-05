package com.nuvoex.library.permission;

import android.support.annotation.NonNull;

/**
 * Created by Shine on 04/08/16.
 */
public class Permission {


    String[] requestedPermissions;
    int requestCode;
    public FragmentPermissionCallback mFragmentPermissionCallback = null;

    //Custom Rational Dialog
    boolean showCustomRationalDialog = true;
    String rationalDialogTitle;
    String rationalDialogMessage;
    //Custom Setting Dialog
    boolean showCustomSettingDialog = true;
    String settingDialogTitle;
    String settingDialogMessage;

    private Permission(PermissionBuilder builder) {
        requestedPermissions = builder.requestedPermissions;
        requestCode = builder.requestCode;
        mFragmentPermissionCallback = builder.mFragmentPermissionCallback;

        showCustomRationalDialog = builder.showCustomRationDialog;
        if (!showCustomRationalDialog) {
            rationalDialogMessage = builder.rationalDialogMessage;
            rationalDialogTitle = builder.rationalDialogTitle;
        }

        showCustomSettingDialog = builder.showCustomSettingDialog;
        if (!showCustomSettingDialog) {
            settingDialogMessage = builder.settingDialogMessage;
            settingDialogTitle = builder.settingDialogTitle;
        }

    }


    public static class PermissionBuilder {

        private String[] requestedPermissions;
        private int requestCode;

        //Custom Rational Dialog
        private boolean showCustomRationDialog = true;
        private String rationalDialogTitle;
        private String rationalDialogMessage;

        //Custom Setting Dialog
        private boolean showCustomSettingDialog = true;
        private String settingDialogTitle;
        private String settingDialogMessage;


        public FragmentPermissionCallback mFragmentPermissionCallback = null;

        public PermissionBuilder(final String[] requestedPermissions, final int requestCode, @NonNull FragmentPermissionCallback listener) {
            this.requestedPermissions = requestedPermissions;
            this.requestCode = requestCode;
            this.mFragmentPermissionCallback = listener;
        }


        public PermissionBuilder enableDefaultRationalDialog(@NonNull String title, @NonNull String message) {
            showCustomRationDialog = false;
            rationalDialogMessage = message;
            rationalDialogTitle = title;
            return this;
        }

        public PermissionBuilder enableDefaultSettingDialog(@NonNull String title, @NonNull String message) {
            showCustomSettingDialog = false;
            settingDialogMessage = message;
            settingDialogTitle = title;
            return this;
        }

        public Permission build() {
            return new Permission(this);
        }
    }

    public interface FragmentPermissionCallback {
        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);

        void onPermissionAccessRemoved(int requestCode);

    }

}
