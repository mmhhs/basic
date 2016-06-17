package com.feima.baseproject.util;

import android.content.Context;

import com.feima.baseproject.R;

public class ResultUtil {
    /**
     * 获取图片正确路径
     * @return
     */
    public static String judgeImagePath(String path) {
        String resultString = "";
        try {
            if (path.startsWith("http")) {
                resultString = path;
            } else if (path.startsWith("/public")){
                resultString = BaseConstant.SERVICE_HOST_IP_LAN_IMG+path;
            }else {
                resultString = "file://"+path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return resultString;
        }
    }

    /**
     * 判断网络返回成功与否
     * @param result
     * @return
     */
    public static boolean judgeResult(Context context, String result){
        String[] codeArray = context.getResources().getStringArray(R.array.base_error_key);
        if(result.equals(codeArray[0])){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断登录失效
     * @param result
     * @return
     */
    public static boolean judgeLoginInvalid(Context context, String result){
        String[] codeArray = context.getResources().getStringArray(R.array.base_error_key);
        if(result.equals(codeArray[1])){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断网络返回结果提示
     * @param result
     * @return
     */
    public static String judgeResult2(Context context, String result){
        String msg = "";
        try {
            String[] codeArray = context.getResources().getStringArray(R.array.base_error_key);
            String[] errorArray = context.getResources().getStringArray(R.array.base_error_vaule);
            for(int i=0;i<codeArray.length;i++){
                if(result.equals(codeArray[i])){
                    msg = errorArray[i];
                    break;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            return msg;
        }

    }

    /**
     * 修正网页路径
     * @param webUrl
     * @return
     */
    public static String judgeWebUrl(String webUrl) {
        String resultString = "";
        try {
            if (webUrl.startsWith("http")||webUrl.startsWith("file://")) {
                resultString = webUrl;
            } else {
                resultString = BaseConstant.SERVICE_HOST_IP_LAN_IMG+webUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return resultString;
        }
    }
}