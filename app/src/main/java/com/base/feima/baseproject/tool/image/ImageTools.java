package com.base.feima.baseproject.tool.image;

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

import com.base.feima.baseproject.util.BaseConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageTools {
	private static final String tag = "ImageTools";
	private Context context;

	public ImageTools() {

	}


	/**
	 * ����ͼƬѹ������
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
	 * dpת��Ϊpx
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * pxת��Ϊdp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * android��ȡsd��·��������
	 * 
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdDir.toString();
	}

	/**
	 * Android���ж�SD���Ƿ���ڣ����ҿ��Խ���д����������ʹ�����´���
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
	 * ͼƬ�����ŷ���
	 * 
	 * @param bgimage
	 *            ��ԴͼƬ��Դ
	 * @param newWidth
	 *            �����ź���
	 * @param newHeight
	 *            �����ź�߶�
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// ��ȡ���ͼƬ�Ŀ�͸�
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		// ������������
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}
	
	/**
	 * viewתbitmap
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
	 * ��Bitmapת����ָ����С
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
	 * Drawable ת Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap ת Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * byte[] ת bitmap
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
	 * bitmap ת byte[]
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
	* ����ͼƬΪJPEG  
	*   
	* @param bitmap  
	* @param arg   ��������
	* @param path  ����·��
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
     * ��ȡ·���е�ͼƬ��Ȼ����ת��Ϊ���ź��bitmap
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
            Bitmap bitmap = BitmapFactory.decodeFile(path, options); // ��ʱ����bmΪ��
            // ��ȡ���ͼƬ�Ŀ�͸�
            int width = options.outWidth;
            int height = options.outHeight;
            options.inJustDecodeBounds = false;
            // �������ű�
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
        int be = 1;//be=1��ʾ������
        try {
            if (width > height && height > BaseConstant.SCALE_WIDTH) {//�����ȴ�Ļ����ݿ�ȹ̶���С����
                be = (int) (width / BaseConstant.SCALE_WIDTH);
            } else if (width < height && height > BaseConstant.SCALE_HEIGHT) {//����߶ȸߵĻ����ݿ�ȹ̶���С����
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