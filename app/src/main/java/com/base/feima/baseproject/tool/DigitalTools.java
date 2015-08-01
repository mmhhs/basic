package com.base.feima.baseproject.tool;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class DigitalTools{
    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     */
    public static String formatMoney(String money){
        String result = "";
        try {
            String[] strs = money.split("\\.");
            String reverseStr = new StringBuilder(strs[0]).reverse().toString();
            String strTemp = "";
            for (int i=0; i<reverseStr.length(); i++) {
                if (i*3+3 > reverseStr.length()){
                    strTemp += reverseStr.substring(i*3,reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i*3, i*3+3)+",";
            }
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length()-1);
            }
            result = new StringBuilder(strTemp).reverse().toString();
            if (strs.length>1){
                result+= "."+strs[1];
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 保留digit位小数 方式1
     * @param num 目标数字
     * @param digit 几位小数
     * @return
     */
    public static double storePoint(double num,int digit){
        double result = 0;
        try{
            BigDecimal bd = new BigDecimal(num);
            bd = bd.setScale(digit,BigDecimal.ROUND_HALF_UP);
            result = bd.doubleValue();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return result;
        }
    }

    /**
     * 保留digit位小数 方式1 美式记法
     * @param num 目标数字
     * @param digit 几位小数
     * @return
     */
    public static String storePointString(double num,int digit){
        String result = "";
        try{
            BigDecimal bd = new BigDecimal(num);
            bd = bd.setScale(digit,BigDecimal.ROUND_HALF_UP);
            result = String.valueOf(bd.doubleValue());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return result;
        }
    }

    /**
     * 保留2位小数 方式2
     * @param num
     * @return
     */
    public static String storePoint2(double num){
        String result = "";
        try{
            java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");
            result = df.format(num);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return result;
        }

    }

    /**
     * 保留小数 方式3
     * @param num
     * @param digit
     * @return
     */
    public static String storePoint3(double num,int digit){
        String result = "";
        try{
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(digit);
            result = nf.format(num);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return result;
        }

    }
}