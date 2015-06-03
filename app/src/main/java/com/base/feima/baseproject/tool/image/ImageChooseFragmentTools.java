package com.base.feima.baseproject.tool.image;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.base.feima.baseproject.util.BaseConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageChooseFragmentTools{
	private final static String tag = "ImageChooseFragmentTools";
	public static final int PHOTO_WITH_CAMERA = 116;	
	public static final int CHOOSE_PICTURE = 117;
	public static final int PHOTO_PICKED_WITH_CROP = 118;
	public static final int SCALE_WIDTH = 720;
	public static final int SCALE_HEIGHT = 1280;
	private static  String imagePathFolder = BaseConstant.IMAGETAMPPATH;
	private static Uri imageUri;
	private  String imageUrl = "";
	
	public ImageChooseFragmentTools(){
		File dir = new File(imagePathFolder);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}

	public ImageChooseFragmentTools(Fragment context){
		File dir = new File(imagePathFolder);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}
	
	
	
	/** 拍照获取相片 **/
	public  void doTakePhoto(Fragment context) {
		try {
			imageUrl = imagePathFolder +""+"image2.jpg";
			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE); // 使用"android.media.action.IMAGE_CAPTURE"    MediaStore.ACTION_IMAGE_CAPTURE
			intent.putExtra("return-data", true); // 有返回值
			
			imageUri = Uri.fromFile(new File(imageUrl));
			// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			// 直接使用，没有缩小
			context.startActivityForResult(intent, PHOTO_WITH_CAMERA); // 用户点击了从相机获取
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}	
	
	/** 拍照获取多张相片 **/
	public  void doTakePhotos(Fragment context) {
		try {
			imageUrl = imagePathFolder +""+System.currentTimeMillis()+"image2.jpg";
			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE); 
			intent.putExtra("return-data", true); // 有返回值
			
			imageUri = Uri.fromFile(new File(imageUrl));
			// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			// 直接使用，没有缩小
			context.startActivityForResult(intent, PHOTO_WITH_CAMERA); // 用户点击了从相机获取
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

	/** 从相册获取图片 **/
	public static void doGalleryPhoto(Fragment context) {
		try {
			Intent openAlbumIntent = doPickPhotoFromGallery();
			// openAlbumIntent.setType("image/*");
			context.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	private static Intent doPickPhotoFromGallery() {
		
		Intent intent = new Intent();
//		int apiLevel = PublicTools.getSystemVersion();
//		if(apiLevel>=19){
//			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//		}else{
//			intent.setAction(Intent.ACTION_PICK);
//		}
		intent.setAction(Intent.ACTION_PICK); // 使用Intent.ACTION_GET_CONTENT这个Action  ACTION_PICK
		intent.setType("image/*"); // 获取任意图片类型
		intent.putExtra("return-data", true); // 有返回值
		return intent;

	}
	
	/**
	 * 获取拍照的照片原图路径
	 * @param
	 * @return
	 */
	public  String getTakePhotosUrl() {
		
		return imageUrl;
	}
	/**
	 * 获取拍照按比例压缩后的照片路径
	 * @param
	 * @return
	 */
	public  String getTakePhotoScaleUrl() {
		String result = "";
		try {
			result = saveScaleImage(imageUrl);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}
		
	}
	
	/**
     * 裁剪图片
     * @param data
     */
	public static void doCropPhoto(Fragment context,Uri data,Boolean isRate){
		try {
			Intent intent = getCropImageIntent(data,isRate);
	        context.startActivityForResult(intent, PHOTO_PICKED_WITH_CROP);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
    }
   
    private static Intent getCropImageIntent(Uri uri,Boolean isRate) {
    	 	Intent intent = new Intent("com.android.camera.action.CROP");
		   intent.setDataAndType(uri, "image/*");		   
		   if(isRate){
			   			   
		   }
		   intent.putExtra("crop", "true");//可裁剪
		   intent.putExtra("aspectX", 1);
		   intent.putExtra("aspectY", 1);
		   intent.putExtra("outputX", 150);
		   intent.putExtra("outputY", 150);  
		   intent.putExtra("scale", true);		   
//		   intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//保存到相册
		   intent.putExtra("return-data", true);//若为false则表示不返回数据
		   intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		   intent.putExtra("noFaceDetection", true); 
		   return intent;
    }
    
    /**
     * 获取相册图片的Uri
     * @param context
     * @param data
     * @return
     */
    public static Uri getGalleryUri(Activity context, Intent data){
    	Uri originalUri = null;
    	try {
    		
			ContentResolver resolver = context.getContentResolver();
    		originalUri = data.getData();
//    		if(PublicTools.getSystemVersion()>=19){
//    			File file = new File(getPath(context, originalUri));
//    			originalUri = Uri.fromFile(file);
//    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return originalUri;
		}
    			
		
    }
    
    /**
     * 获取相册图片的原始资源地址
     * @param context
     * @param data
     * @return
     */
    public static String getGalleryUrl(Activity context, Intent data){
    	String result = "";
    	try {    		
        	ContentResolver resolver = context.getContentResolver();
    		if(data==null){
    			return "";
    		}
    		// 照片的原始资源
    		Uri originalUri = data.getData();	
    		if(originalUri!=null)
    		{
//    			if(PublicTools.getSystemVersion()>=19){
//    				result = getPath(context, originalUri);
//    			}else{
    				String[] proj = {MediaStore.Images.Media.DATA};
	       			 Cursor cursor = resolver.query(originalUri, proj, null, null, null);
	       			 int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       			 if (cursor.moveToFirst()) {
	       				 result = cursor.getString(column_index);
	       			 }
	       			 cursor.close();
//    			}
    			 
    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}
    	
    }
    
    /**
     * 获取拍照的照片的Uri
     * @return
     */
    public static Uri getTakeUri(){
    	
		return imageUri;
    }
    
    /**
     * 获取裁剪后的bitmap
     * @param data
     * @return
     */
    public static Bitmap getCropBitmap(Intent data){
    	Bitmap bitmap = null;
    	try {
			bitmap = data.getParcelableExtra("data");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return bitmap;
		}
    	
    }
    
    /**  
	* 读取路径中的图片，然后将其转化为缩放后的bitmap  
	*   
	* @param path  
	*/  
	public static String saveScaleImage(String path) {  
		String result = "";
    	try {
    		String fileSavePath ="";
    		long time = System.currentTimeMillis();
    		if(!sdCardExist()||path.equals("")){
    			return "";
    		}		
    		fileSavePath = imagePathFolder +time+".jpg";
    		File folder = new File(imagePathFolder);
    		if(!folder.exists()){
    			folder.mkdirs();
    		}		
    		BitmapFactory.Options options = new BitmapFactory.Options();   
    		options.inJustDecodeBounds = true;   		
    		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空   
    		// 获取这个图片的宽和高   
    		int width = options.outWidth;
    		int height = options.outHeight;
    		options.inJustDecodeBounds = false;   
    		// 计算缩放比   
    		int be = caculateInSampleSize(width,height);			 
    		options.inSampleSize =be; 
    		try{
    			bitmap = BitmapFactory.decodeFile(path, options);
    		}catch(Exception e){
    			e.printStackTrace();
                return "";
    		}catch(OutOfMemoryError error){
    			error.printStackTrace();
    			return "";
    		}
    		if(bitmap==null){
    			return "";
    		} 
    		boolean saveResult = saveJPGE_After(bitmap,100, fileSavePath);
    		if(!bitmap.isRecycled()){
    			bitmap.recycle();
    		}
    		bitmap=null;
    		if(saveResult){
    			
    			result = fileSavePath;
    		}else{
    			
    			result = "";
    		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}
		

	}
	
	private static int caculateInSampleSize(int width,int height){
		int be = 1;//be=1表示不缩放
    	try {
    		if (width > height && height > SCALE_WIDTH) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (width / SCALE_WIDTH);
            } else if (width < height && height > SCALE_HEIGHT) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (height / SCALE_HEIGHT);
            }
    		if(be<=1){
    			be = 1; 
    		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return be;
		}
		
        			
        
	}
	
	/**
	 * 获取按比例压缩后的照片路径
	 * @param
	 * @return
	 */
	public static String getPictureScaleUrl(String imageUrl) {
		String result = "";
    	try {
    		result = saveScaleImage(imageUrl);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}

	}		

	/**  
	* 保存图片为JPEG  
	*   
	* @param bitmap  
	* @param path  
	*/  
	public static Boolean saveJPGE_After(Bitmap bitmap, int arg,String path) { 
		boolean result = false;
    	try {
    		File photoFile = new File(path);  
    		if(!photoFile.exists()){
    			try {
    				photoFile.createNewFile();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    		try {   
    			FileOutputStream out = new FileOutputStream(photoFile);  
    			if (bitmap.compress(Bitmap.CompressFormat.JPEG, arg, out)) {
    				out.flush();   
    				out.close();   
    			}   
    		} catch (FileNotFoundException e) {  
    			photoFile.delete();
    			e.printStackTrace();
    			result =  false;
    		} catch (IOException e) {  
    			photoFile.delete();
    			e.printStackTrace();
    			result =  false;
    		}catch (Exception e){
    			e.printStackTrace();
    			result =   false;
    		}
    		result = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}
		
	}  
	
	/**
	 * 获取sd卡路径
	 * @return
	 */
	public static String getSDPath(){
		String result = "";
    	try {
    		File sdDir = null;
    		boolean sdCardExist = Environment.getExternalStorageState()
    		.equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
    		if (sdCardExist)
    		{
    			sdDir = Environment.getExternalStorageDirectory();//获取跟目录
    		}
    		result = sdDir.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}

	}

	/**
	 * Android中判断SD卡是否存在，并且可以进行写操作，可以使用如下代码
	 * @return
	 */
	public static Boolean sdCardExist(){
		boolean result = false;
    	try {
    		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
			//TODO:
    			result =  true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return result;
		}
		

	}
	/**
	* 加载本地图片
	* http://bbs.3gstdy.com
	* @param url
	* @return
	*/
	public static Bitmap getLoacalBitmap(String url) {
	     try {
	          FileInputStream fis = new FileInputStream(url);
	          return BitmapFactory.decodeStream(fis);
	     } catch (FileNotFoundException e) {
	          e.printStackTrace();
	          return null;
	     }
	}
	
	/**
	 * 截取图片的中间的200X200的区域
	 * @param bm
	 * 
	 * @return
	 */
	public static  Bitmap cropCenter(Bitmap bm,int width,int height)
	{
		int dstWidth = width;
        int dstHeight = height;
        int startWidth = (bm.getWidth() - dstWidth)/2;
        int startHeight = ((bm.getHeight() - dstHeight) / 2);
        Rect src = new Rect(startWidth, startHeight, startWidth + dstWidth, startHeight + dstHeight);
        return dividePart(bm, src);
	}
	
	/**
	 * 剪切图片
	 * @param bmp 被剪切的图片
	 * @param src 剪切的位置
	 * @return 剪切后的图片
	 */
	public static  Bitmap dividePart(Bitmap bmp, Rect src)
	{
		int width = src.width();
		int height = src.height();
		Rect des = new Rect(0, 0, width, height);
		Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(croppedImage);
		canvas.drawBitmap(bmp, src, des, null);
		return croppedImage;
	}
	
	//4.4以上处理图库选择图片
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {

	        // Return the remote address
	        if (isGooglePhotosUri(uri))
	            return uri.getLastPathSegment();

	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return "";
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
	
}