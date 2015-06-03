/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.base.feima.baseproject.tool.image;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.os.StrictMode;

import com.base.feima.baseproject.tool.ImageTools;
import com.base.feima.baseproject.tool.image.UILConstants.Config;
import com.base.feima.baseproject.util.BaseConstant;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.File;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class UILApplication extends Application {
	public static ImageLoaderConfiguration configs;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}

		super.onCreate();
        //初始化存储图片路径
        BaseConstant.initImagePath(getApplicationContext());
		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		int maxImageWidthForMemoryCache = 720;
		int maxImageHeightForMemoryCache = 1280;
		String imgDiscCache = BaseConstant.IMAGESAVEPATH;
		File cacheDir = null ;
		BitmapProcessor bitmapProcessor = null;
		
		if(ImageTools.sdCardExist()){
			cacheDir = new File(imgDiscCache);
			if(!cacheDir.exists()){
				cacheDir.mkdirs();
			}
		}		
		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		memClass = memClass > 128 ? 128 : memClass;
		// 使用可用内存的1/4作为图片缓存
		final int cacheSize = 1024 * 1024 * memClass / 4;

		configs = new
                ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(maxImageWidthForMemoryCache, maxImageHeightForMemoryCache)
                .discCacheExtraOptions(maxImageWidthForMemoryCache, maxImageHeightForMemoryCache, CompressFormat.JPEG, 100, bitmapProcessor)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new FIFOLimitedMemoryCache(cacheSize)) // You can pass your own memory cache implementation
                .discCache(new TotalSizeLimitedDiscCache(cacheDir, 100)) // You can pass your own disc cache implementation
                .discCacheFileCount(100)
                .discCacheSize(50 * 1024 * 1024)
                        // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
		        .build();
		ImageLoader.getInstance().init(configs);
	}
	
	public ImageLoaderConfiguration getConfigs(){
		return configs;
	}
	
	
}