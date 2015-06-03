package com.base.feima.baseproject.tool;

import android.content.Context;

import com.base.feima.baseproject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class OptionTools {
	
	
	public static DisplayImageOptions getBaseOptions(Context context){
		DisplayImageOptions options = new DisplayImageOptions.Builder()		
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.showImageOnLoading(R.color.grey)
		.showImageOnFail(R.color.grey)
		.showImageForEmptyUri(R.color.grey)
		.build();
		return options;
	}
	
	public static DisplayImageOptions getNoDiscOptions(Context context){
		DisplayImageOptions options = new DisplayImageOptions.Builder()		
		.cacheInMemory(true)
		.cacheOnDisc(false)
		.considerExifParams(true)
		.showImageOnLoading(R.color.grey)
		.showImageOnFail(R.color.grey)
		.showImageForEmptyUri(R.color.grey)
		.build();
		return options;
	}



	
}