package com.feima.baseproject.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MIUIUtil {  // 检测MIUI
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI(Context context) {//获取缓存状态
        if (SharedUtil.getMIUIChecked(context)) {
            return SharedUtil.getMIUI(context);
        }
        Properties prop = new Properties();
        boolean isMIUI;
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        SharedUtil.saveMIUI(context,isMIUI,true);
        return isMIUI;  }
    }
