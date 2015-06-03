package com.base.feima.baseproject.tool;

import java.util.HashMap;
import java.util.Map;

public class EncryTools{

	
	public static Map<String,Object> getEncryMap(){
		Map<String, Object> arg = new HashMap<String, Object>();
//		String stamp = TimeTools.getStamp();
//		String md5 = MD5.get32MD5Str("meijia" + stamp);
//		arg.put("timestamp", ""+stamp);
//		arg.put("token", ""+md5);
		return arg;
	}
	
}