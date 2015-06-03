package com.base.feima.baseproject.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对外提供getMD5(String)方法
 * @author randyjia
 *
 */
public class MD5 {
	
	public static String getMD5(String val) throws NoSuchAlgorithmException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(val.getBytes());
		byte[] m = md5.digest();//加密
		return getString(m);
}
	private static String getString(byte[] b){
		StringBuffer sb = new StringBuffer();
		 for(int i = 0; i < b.length; i ++){
		  sb.append(b[i]);
		 }
		 return sb.toString();
}
	
	/**
	* MD5 32位加密方法二 小写
	* @param str
	* @return
	*/

	public final static String get32MD5Str(String str) { 
		MessageDigest messageDigest = null; 
		try { 
		messageDigest = MessageDigest.getInstance("MD5"); 
		messageDigest.reset(); 
		messageDigest.update(str.getBytes("UTF-8")); 
		} catch (NoSuchAlgorithmException e) { 
		System.out.println("NoSuchAlgorithmException caught!"); 
		System.exit(-1); 
		} catch (UnsupportedEncodingException e) { 
		e.printStackTrace(); 
		} 
		byte[] byteArray = messageDigest.digest(); 
		StringBuffer md5StrBuff = new StringBuffer(); 
		for (int i = 0; i < byteArray.length; i++) { 
		if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) 
		md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i])); 
		else 
		md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i])); 
		} 
		return md5StrBuff.toString(); 
	}
	
	
}
