package com.base.feima.baseproject.util.tool;

/*  * 文 件 名:  DataCleanManager.java  * 描    述:  主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录  */

import android.app.Activity;
import android.os.Environment;

import com.base.feima.baseproject.listener.IOnDialogBackgroundListener;
import com.base.feima.baseproject.task.ShowDialogTask;
import com.base.feima.baseproject.util.BaseConstant;

import java.io.File;
import java.math.BigDecimal;

/** * 本应用数据清除管理器 */
public class DataCleanManager {
    private static String tagStr = "DataCleanManager";

    /**
     * 计算缓存大小
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
                // 如果下面还有文件
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
     * 格式化单位
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

    /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param activity */
    public static void cleanInternalCache(Activity activity) {
        deleteFilesByDirectory(activity,activity.getCacheDir());
    }

    /** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param activity */
    public static void cleanDatabases(Activity activity) {
        deleteFilesByDirectory(activity,new File("/data/data/"
                + activity.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * activity
     */
    public static void cleanSharedPreference(Activity activity) {
        deleteFilesByDirectory(activity,new File("/data/data/"
                + activity.getPackageName() + "/shared_prefs"));
    }

    /** * 按名字清除本应用数据库 * * @param activity * @param dbName */
    public static void cleanDatabaseByName(Activity activity, String dbName) {
        activity.deleteDatabase(dbName);
    }

    /** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param activity */
    public static void cleanFiles(Activity activity) {
        deleteFilesByDirectory(activity,activity.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * activity
     */
    public static void cleanExternalCache(Activity activity) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(activity,activity.getExternalCacheDir());
        }
    }

    /** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
    public static void cleanCustomCache(Activity activity,String filePath) {
        deleteFilesByDirectory(activity,new File(filePath));
    }

    /** * 清除本应用所有的数据 * * @param activity * @param filepath */
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

//    /** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
//    private static void deleteFilesByDirectory(File directory) {
//        if (directory != null && directory.exists() && directory.isDirectory()) {
//            for (File item : directory.listFiles()) {
//                item.delete();
//            }
//        }
//    }

    /** * 删除方法 这里会删除所有文件夹下的文件*/
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