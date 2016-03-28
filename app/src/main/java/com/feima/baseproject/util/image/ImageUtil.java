package com.feima.baseproject.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.feima.baseproject.util.BaseConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageUtil {
	private static final String tag = "ImageTools";
	private Context context;

	public ImageUtil() {

	}


	/**
	 * 计算图片压缩比例
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	/**
	 * dp转换为px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转换为dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * android获取sd卡路径方法：
	 *
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();
	}

	/**
	 * Android中判断SD卡是否存在，并且可以进行写操作，可以使用如下代码
	 *
	 * @return
	 */
	public static Boolean sdCardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// TODO:
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 图片的缩放方法
	 *
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
								   double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	/**
	 * view转bitmap
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		Bitmap bitmap = null;
		try {
			int width = view.getWidth();
			int height = view.getHeight();
			Log.i("count", "width=" + width + "##" + height);
			if (width != 0 && height != 0) {

				bitmap = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				view.layout(0, 0, width, height);
				view.draw(canvas);
			}
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		return bitmap;
	}


	/**
	 * 将Bitmap转换成指定大小
	 *
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	/**
	 * Drawable 转 Bitmap
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap 转 Drawable
	 *
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * byte[] 转 bitmap
	 *
	 * @param b
	 * @return
	 */
	public static Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}


	/**
	 * bitmap 转 byte[]
	 *
	 * @param bm
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}


	/**
	 * 保存图片为JPEG
	 *
	 * @param bitmap
	 * @param arg   保存质量
	 * @param path  保存路径
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
	 * 读取路径中的图片，然后将其转化为缩放后的bitmap
	 * 360*640
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
			fileSavePath = BaseConstant.IMAGETAMPPATH +time+".jpg";
			File folder = new File(BaseConstant.IMAGETAMPPATH);
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
			if (width > height && height > BaseConstant.SCALE_WIDTH) {//如果宽度大的话根据宽度固定大小缩放
				be = (int) (width / BaseConstant.SCALE_WIDTH);
			} else if (width < height && height > BaseConstant.SCALE_HEIGHT) {//如果高度高的话根据宽度固定大小缩放
				be = (int) (height / BaseConstant.SCALE_HEIGHT);
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

}