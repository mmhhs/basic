package com.base.feima.baseproject.util;

/*  * �� �� ��:  DataCleanManager.java  * ��    ��:  ��Ҫ�����������/�⻺�棬������ݿ⣬���sharedPreference�����files������Զ���Ŀ¼  */

import android.app.Activity;
import android.os.Environment;

import com.base.feima.baseproject.listener.IOnDialogBackgroundListener;
import com.base.feima.baseproject.task.ShowDialogTask;

import java.io.File;
import java.math.BigDecimal;

/** * ��Ӧ��������������� */
public class DataCleanManager {
    private static String tagStr = "DataCleanManager";

    /**
     * ���㻺���С
     * @param activity
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Activity activity) throws Exception {
//        long cacheSize = getFolderSize(activity.getCacheDir());
        long cacheSize = 0;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(activity.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // ������滹���ļ�
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * ��ʽ����λ
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /** * �����Ӧ���ڲ�����(/data/data/com.xxx.xxx/cache) * * @param activity */
    public static void cleanInternalCache(Activity activity) {
        deleteFilesByDirectory(activity,activity.getCacheDir());
    }

    /** * �����Ӧ���������ݿ�(/data/data/com.xxx.xxx/databases) * * @param activity */
    public static void cleanDatabases(Activity activity) {
        deleteFilesByDirectory(activity,new File("/data/data/"
                + activity.getPackageName() + "/databases"));
    }

    /**
     * * �����Ӧ��SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * activity
     */
    public static void cleanSharedPreference(Activity activity) {
        deleteFilesByDirectory(activity,new File("/data/data/"
                + activity.getPackageName() + "/shared_prefs"));
    }

    /** * �����������Ӧ�����ݿ� * * @param activity * @param dbName */
    public static void cleanDatabaseByName(Activity activity, String dbName) {
        activity.deleteDatabase(dbName);
    }

    /** * ���/data/data/com.xxx.xxx/files�µ����� * * @param activity */
    public static void cleanFiles(Activity activity) {
        deleteFilesByDirectory(activity,activity.getFilesDir());
    }

    /**
     * * ����ⲿcache�µ�����(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * activity
     */
    public static void cleanExternalCache(Activity activity) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(activity,activity.getExternalCacheDir());
        }
    }

    /** * ����Զ���·���µ��ļ���ʹ����С�ģ��벻Ҫ��ɾ������ֻ֧��Ŀ¼�µ��ļ�ɾ�� * * @param filePath */
    public static void cleanCustomCache(Activity activity,String filePath) {
        deleteFilesByDirectory(activity,new File(filePath));
    }

    /** * �����Ӧ�����е����� * * @param activity * @param filepath */
    public static void cleanApplicationData(Activity activity, String... filepath) {
        cleanInternalCache(activity);
        cleanExternalCache(activity);
        cleanDatabases(activity);
        cleanSharedPreference(activity);
        cleanFiles(activity);
        for (String filePath : filepath) {
            cleanCustomCache(activity,filePath);
        }
    }

//    /** * ɾ������ ����ֻ��ɾ��ĳ���ļ����µ��ļ�����������directory�Ǹ��ļ������������� * * @param directory */
//    private static void deleteFilesByDirectory(File directory) {
//        if (directory != null && directory.exists() && directory.isDirectory()) {
//            for (File item : directory.listFiles()) {
//                item.delete();
//            }
//        }
//    }

    /** * ɾ������ �����ɾ�������ļ����µ��ļ�*/
    private static void deleteFilesByDirectory(Activity activity, final File directory) {
        if (directory != null && directory.exists()) {
            ShowDialogTask showDialogTask = new ShowDialogTask(activity,tagStr,null,"",false);
            showDialogTask.setiOnDialogBackgroundListener(new IOnDialogBackgroundListener() {
                @Override
                public BaseConstant.TaskResult onBackground(ShowDialogTask showDialogTask) {
                    BaseConstant.TaskResult taskResult = BaseConstant.TaskResult.NOTHING;
                    DeleteFile(directory);
                    return taskResult;
                }
            });
            showDialogTask.execute();
        }

    }

    private static void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    DeleteFile(f);
                }
                file.delete();
            }
        }
    }

}