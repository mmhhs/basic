package com.feima.baseproject.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.feima.baseproject.base.BaseFragmentActivity;
import com.feima.baseproject.listener.IOnDialogListener;
import com.feima.baseproject.manager.ScreenManager;
import com.feima.baseproject.view.dialog.DialogUtil;

import java.lang.ref.WeakReference;

import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;

public class PermissionUtil {
    //定位
    public final static int CHECK_LOCTION_PERMISSION_CODE = 1;
    public final static String CHECK_LOCTION_PERMISSION_NAME = "android.permission.ACCESS_FINE_LOCATION";
    //拨打电话及联系人
    public final static int CHECK_PHONE_PERMISSION_CODE = 2;
    public final static String CHECK_PHONE_PERMISSION_NAME = "android.permission.CALL_PHONE";
    //读写文件
    public final static int CHECK_FILE_PERMISSION_CODE = 3;
    public final static String CHECK_FILE_PERMISSION_NAME = "android.permission.WRITE_EXTERNAL_STORAGE";
    //系统弹窗
    public final static int CHECK_WINDOW_PERMISSION_CODE = 4;
    public final static String CHECK_WINDOW_PERMISSION_NAME = "android.permission.SYSTEM_ALERT_WINDOW";

    //定义的权限编码,当PERMISSION_DOACACHENEEDSPERMISSION有N个权限，那么REQUEST_DOACACHENEEDSPERMISSION就会有多少值
    private static final int REQUEST_DOACACHENEEDSPERMISSION = 1;
    //需要请求的权限名称
    private static String[] PERMISSION_DOACACHENEEDSPERMISSION = new String[]{CHECK_LOCTION_PERMISSION_NAME,CHECK_FILE_PERMISSION_NAME};


    private PermissionUtil() {
    }

    /**
     * 检查用户设备是否拥有该权限
     *
     * @param activity
     */
    public static void doACacheNeedsPermissionWithCheck(BaseFragmentActivity activity) {
        boolean isM = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
        if (isM){
            PERMISSION_DOACACHENEEDSPERMISSION = new String[]{CHECK_LOCTION_PERMISSION_NAME,CHECK_FILE_PERMISSION_NAME,CHECK_WINDOW_PERMISSION_NAME};
        }
        // 如果拥有该权限，那么调用用户注解为：@NeedsPermission的方法
        if (PermissionUtils.hasSelfPermissions(activity, PERMISSION_DOACACHENEEDSPERMISSION)) {
            activity.doACacheNeedsPermission();
        } else {
            // 如果用户设备没有该权限，那么请求提示用户是否赋予该权限
            if (PermissionUtils.shouldShowRequestPermissionRationale(activity, PERMISSION_DOACACHENEEDSPERMISSION)) {
                activity.ACacheShowRationale(new DoACacheNeedsPermissionPermissionRequest(activity));
            } else {
                ActivityCompat.requestPermissions(activity, PERMISSION_DOACACHENEEDSPERMISSION, CHECK_LOCTION_PERMISSION_CODE);
            }
        }
    }

    /**
     * 权限请求回调
     *
     * @param activity
     * @param requestCode  权限编码
     * @param grantResults
     */
    public static void onRequestPermissionsResult(BaseFragmentActivity activity, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case CHECK_LOCTION_PERMISSION_CODE:
                if (PermissionUtils.getTargetSdkVersion(activity) < 23 && !PermissionUtils.hasSelfPermissions(activity, PERMISSION_DOACACHENEEDSPERMISSION)) {
                    activity.ACacheOnPermissionDenied();
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    activity.doACacheNeedsPermission();
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(activity, PERMISSION_DOACACHENEEDSPERMISSION)) {
                        activity.ACacheOnNeverAskAgain();
                    } else {
                        activity.ACacheOnPermissionDenied();
                    }
                }
                break;
            default:
                break;
        }
    }

    private static final class DoACacheNeedsPermissionPermissionRequest implements PermissionRequest {
        private final WeakReference<BaseFragmentActivity> weakActivity;

        private DoACacheNeedsPermissionPermissionRequest(BaseFragmentActivity activity) {
            this.weakActivity = new WeakReference<>(activity);
        }

        @Override
        public void proceed() {
            Activity activity = weakActivity.get();
            if (activity == null) return;
            ActivityCompat.requestPermissions(activity, PERMISSION_DOACACHENEEDSPERMISSION, CHECK_LOCTION_PERMISSION_CODE);
        }

        @Override
        public void cancel() {
            BaseFragmentActivity activity = weakActivity.get();
            if (activity == null) return;
                activity.ACacheOnPermissionDenied();
        }
    }

    public static void showPermissionDialog(final Activity activity, View view) {
        DialogUtil dialogUtil = new DialogUtil(activity);
        dialogUtil.setTitle("帮助");
        dialogUtil.setConfirmStr("设置");
        dialogUtil.setCancelStr("退出应用");
        dialogUtil.showTipDialog(view, "当前应用缺少定位、读写文件、弹窗权限\n" +
                "请点击“设置”-“权限”-打开所需权限");
        dialogUtil.setiOnDialogListener(new IOnDialogListener() {
            @Override
            public void onConfirm() {
                OptionUtil.setting(activity);
            }

            @Override
            public void onCancel() {
                ScreenManager.getScreenManagerInstance().closeAll();
            }

            @Override
            public void onOther() {

            }
        });
    }


    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param Activity
     * @return true 表示开启
     */
    public static final boolean isOpenGps(final Activity Activity) {
        LocationManager locationManager
                = (LocationManager) Activity.getSystemService(Activity.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param Activity
     */
    public static final void openGPS(Activity Activity) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(Activity, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


}