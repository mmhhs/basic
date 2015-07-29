/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.base.feima.baseproject.fresco;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.fresco.instrumentation.InstrumentedDraweeView;
import com.base.feima.baseproject.util.BaseConstant;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

public class ConfigConstants {

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();//����Ŀ����ڴ�
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;//ʹ�õĻ�������

    public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB;//Сͼ���ʹ��̿ռ仺������ֵ�����ԣ��ɽ�������Сͼ�ŵ����������һ�����̿ռ��ֹ��ͼռ�ô��̿ռ��ɾ���˴�����Сͼ��
    public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB;//Сͼ�ʹ��̿ռ仺������ֵ�����ԣ��ɽ�������Сͼ�ŵ����������һ�����̿ռ��ֹ��ͼռ�ô��̿ռ��ɾ���˴�����Сͼ��
    public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;//Сͼ���̻�������ֵ�����ԣ��ɽ�������Сͼ�ŵ����������һ�����̿ռ��ֹ��ͼռ�ô��̿ռ��ɾ���˴�����Сͼ��

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB;//Ĭ��ͼ���ʹ��̿ռ仺������ֵ
    public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;//Ĭ��ͼ�ʹ��̿ռ仺������ֵ
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;//Ĭ��ͼ���̻�������ֵ


    private static  String IMAGE_PIPELINE_SMALL_CACHE_DIR = "imagepipeline_cache";//Сͼ����·�����ļ�����
    private static  String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";//Ĭ��ͼ����·�����ļ�����

    private static ImagePipelineConfig sImagePipelineConfig;

    private ConfigConstants(){

    }
    /**
     * ��ʼ�����ã�����
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            //��ʼ���洢ͼƬ·��
            BaseConstant.initImagePath(context);
            IMAGE_PIPELINE_SMALL_CACHE_DIR = BaseConstant.IMAGESAVEPATH;
            IMAGE_PIPELINE_CACHE_DIR = BaseConstant.IMAGESAVEPATH;
            sImagePipelineConfig = configureCaches(context);
        }
        return sImagePipelineConfig;
    }



    /**
     * ��ʼ������
     */
    private static ImagePipelineConfig configureCaches(Context context) {
        //�ڴ�����
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // �ڴ滺������ͼƬ������С,���ֽ�Ϊ��λ��
                Integer.MAX_VALUE,                     // �ڴ滺����ͼƬ�����������
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // �ڴ滺����׼���������δ��ɾ������ͼƬ������С,���ֽ�Ϊ��λ��
                Integer.MAX_VALUE,                     // �ڴ滺����׼���������ͼƬ�����������
                Integer.MAX_VALUE);                    // �ڴ滺���е���ͼƬ������С��

        //�޸��ڴ�ͼƬ�����������ռ���ԣ������ʽ�е���ģ�
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams= new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        //СͼƬ�Ĵ�������
        DiskCacheConfig diskSmallCacheConfig =  DiskCacheConfig.newBuilder()
                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())//����ͼƬ��·��
                .setBaseDirectoryName(BaseConstant.CACHENAME)//�ļ�����
                .setBaseDirectoryPath(new File(IMAGE_PIPELINE_SMALL_CACHE_DIR))//·��
//            .setCacheErrorLogger(cacheErrorLogger)//��־��¼��������־����Ļ��档
//            .setCacheEventListener(cacheEventListener)//�����¼���������
//            .setDiskTrimmableRegistry(diskTrimmableRegistry)//�ཫ����һ��ע���Ļ�����ٴ��̿ռ�Ļ�����
                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE)//Ĭ�ϻ��������С��
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//���������С,ʹ���豸ʱ�ʹ��̿ռ䡣
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//���������С,���豸���ʹ��̿ռ�
//            .setVersion(version)
                .build();

        //Ĭ��ͼƬ�Ĵ�������
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder()
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())//����ͼƬ��·��
                .setBaseDirectoryName(BaseConstant.CACHENAME)//�ļ�����
                .setBaseDirectoryPath(new File(IMAGE_PIPELINE_SMALL_CACHE_DIR))//·��
//            .setCacheErrorLogger(cacheErrorLogger)//��־��¼��������־����Ļ��档
//            .setCacheEventListener(cacheEventListener)//�����¼���������
//            .setDiskTrimmableRegistry(diskTrimmableRegistry)//�ཫ����һ��ע���Ļ�����ٴ��̿ռ�Ļ�����
                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE)//Ĭ�ϻ��������С��
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//���������С,ʹ���豸ʱ�ʹ��̿ռ䡣
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//���������С,���豸���ʹ��̿ռ�
//            .setVersion(version)
                .build();

        //����ͼƬ����
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
//            .setAnimatedImageFactory(AnimatedImageFactory animatedImageFactory)//ͼƬ���ض���
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)//�ڴ滺�����ã�һ�����棬�ѽ����ͼƬ��
//            .setCacheKeyFactory(cacheKeyFactory)//����Key����
//            .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)//�ڴ滺���δ������ڴ滺������ã��������棩
//            .setExecutorSupplier(executorSupplier)//�̳߳�����
//            .setImageCacheStatsTracker(imageCacheStatsTracker)//ͳ�ƻ����������
//            .setImageDecoder(ImageDecoder imageDecoder) //ͼƬ����������
//            .setIsPrefetchEnabledSupplier(Supplier<Boolean> isPrefetchEnabledSupplier)//ͼƬԤ��������ͼ��Ԥ����ͼ�ȣ�Ԥ���ص��ļ�����
                .setMainDiskCacheConfig(diskCacheConfig)//���̻������ã��ܣ��������棩
//            .setMemoryTrimmableRegistry(memoryTrimmableRegistry) //�ڴ�����������,��ʱ���ǿ��ܻ�����С�ڴ�����������Ӧ����������������Ҫռ���ڴ棬���ò���ͼƬ����������߼�С �����������鿴���ֻ��Ƿ��Ѿ��ڴ治���ˡ�
//            .setNetworkFetchProducer(networkFetchProducer)//�Զ�����������ã���OkHttp��Volley
//            .setPoolFactory(poolFactory)//�̳߳ع�������
//            .setProgressiveJpegConfig(progressiveJpegConfig)//����ʽJPEGͼ
//            .setRequestListeners(requestListeners)//ͼƬ�������
//            .setResizeAndRotateEnabledForNetwork(boolean resizeAndRotateEnabledForNetwork)//��������ת�Ƿ�֧������ͼƬ
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)//���̻������ã�СͼƬ����ѡ�����������Сͼ�Ż����棩
                ;
        return configBuilder.build();
    }
    //Բ�Σ�Բ����ͼ���Զ�ͼ��Ч
    public static RoundingParams getRoundingParams(){
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
//    roundingParams.asCircle();//Բ��
//    roundingParams.setBorder(color, width);//fresco:roundingBorderWidth="2dp"�߿�  fresco:roundingBorderColor="@color/border_color"
//    roundingParams.setCornersRadii(radii);//�뾶
//    roundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft)//fresco:roundTopLeft="true" fresco:roundTopRight="false" fresco:roundBottomLeft="false" fresco:roundBottomRight="true"
//    roundingParams. setCornersRadius(radius);//fresco:roundedCornerRadius="1dp"Բ��
//    roundingParams.setOverlayColor(overlayColor);//fresco:roundWithOverlayColor="@color/corner_color"
//    roundingParams.setRoundAsCircle(roundAsCircle);//Բ
//    roundingParams.setRoundingMethod(roundingMethod);
//    fresco:progressBarAutoRotateInterval="1000"�Զ���ת���
        // ���� fromCornersRadii �Լ� asCircle ����
        return roundingParams;
    }

    //Drawees   DraweeHierarchy  ��֯
    public static GenericDraweeHierarchy getGenericDraweeHierarchy(Context context){
        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(context.getResources())
//            .reset()//����
//            .setActualImageColorFilter(colorFilter)//��ɫ����
//            .setActualImageFocusPoint(focusPoint)//focusCrop, ��Ҫָ��һ�����е�
//            .setActualImageMatrix(actualImageMatrix)
//            .setActualImageScaleType(actualImageScaleType)//fresco:actualImageScaleType="focusCrop"��������
//            .setBackground(background)//fresco:backgroundImage="@color/blue"����ͼƬ
//            .setBackgrounds(backgrounds)
//            .setFadeDuration(fadeDuration)//fresco:fadeDuration="300"����ͼƬ����ʱ��
                .setFailureImage(ConfigConstants.sErrorDrawable)//fresco:failureImage="@drawable/error"ʧ��ͼ
//            .setFailureImage(failureDrawable, failureImageScaleType)//fresco:failureImageScaleType="centerInside"ʧ��ͼ��������
//            .setOverlay(overlay)//fresco:overlayImage="@drawable/watermark"����ͼ
//            .setOverlays(overlays)
                .setPlaceholderImage(ConfigConstants.sPlaceholderDrawable)//fresco:placeholderImage="@color/wait_color"ռλͼ
//            .setPlaceholderImage(placeholderDrawable, placeholderImageScaleType)//fresco:placeholderImageScaleType="fitCenter"ռλͼ��������
//            .setPressedStateOverlay(drawable)//fresco:pressedStateOverlayImage="@color/red"��ѹ״̬�µĵ���ͼ
                .setProgressBarImage(new ProgressBarDrawable())//������fresco:progressBarImage="@drawable/progress_bar"������
//            .setProgressBarImage(progressBarImage, progressBarImageScaleType)//fresco:progressBarImageScaleType="centerInside"����������
//            .setRetryImage(retryDrawable)//fresco:retryImage="@drawable/retrying"������¼���
//            .setRetryImage(retryDrawable, retryImageScaleType)//fresco:retryImageScaleType="centerCrop"������¼�����������
                .setRoundingParams(RoundingParams.asCircle())//Բ��/Բ��fresco:roundAsCircle="true"Բ��
                .build();
        return gdh;
    }


//DraweeView������SimpleDraweeView����UI���
//  public static SimpleDraweeView getSimpleDraweeView(Context context,Uri uri){
//    SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//    simpleDraweeView.setImageURI(uri);
//    simpleDraweeView.setAspectRatio(1.33f);//������ű�
//    return simpleDraweeView;
//  }

    //SimpleDraweeControllerBuilder
    public static SimpleDraweeControllerBuilder getSimpleDraweeControllerBuilder(SimpleDraweeControllerBuilder sdcb,Uri uri,  Object callerContext,DraweeController draweeController){
        SimpleDraweeControllerBuilder controllerBuilder = sdcb
                .setUri(uri)
                .setCallerContext(callerContext)
//              .setAspectRatio(1.33f);//������ű�
                .setOldController(draweeController);
        return controllerBuilder;
    }

    //ͼƬ����
    public static ImageDecodeOptions getImageDecodeOptions(){
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
//            .setBackgroundColor(Color.TRANSPARENT)//ͼƬ�ı�����ɫ
//            .setDecodeAllFrames(decodeAllFrames)//��������֡
//            .setDecodePreviewFrame(decodePreviewFrame)//����Ԥ����
//            .setForceOldAnimationCode(forceOldAnimationCode)//ʹ����ǰ����
//            .setFrom(options)//ʹ���Ѿ����ڵ�ͼ�����
//            .setMinDecodeIntervalMs(intervalMs)//��С����������λ��λ��
                .setUseLastFrameForPreview(true)//ʹ�����һ֡����Ԥ��
                .build();
        return decodeOptions;
    }

    //ͼƬ��ʾ
    public static ImageRequest getImageRequest(InstrumentedDraweeView view,String uri){
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
//            .setAutoRotateEnabled(true)//�Զ���תͼƬ����
//            .setImageDecodeOptions(getImageDecodeOptions())//  ͼƬ�����
//            .setImageType(ImageType.SMALL)//ͼƬ���ͣ����ú�ɵ���ͼƬ����Сͼ���̿ռ仹��Ĭ��ͼƬ���̿ռ�
//            .setLocalThumbnailPreviewsEnabled(true)//����ͼԤ����Ӱ��ͼƬ��ʾ�ٶȣ���΢��
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//���󾭹����漶��  BITMAP_MEMORY_CACHE��ENCODED_MEMORY_CACHE��DISK_CACHE��FULL_FETCH
//            .setPostprocessor(postprocessor)//�޸�ͼƬ
//            .setProgressiveRenderingEnabled(true)//�������أ���Ҫ���ڽ���ʽ��JPEGͼ��Ӱ��ͼƬ��ʾ�ٶȣ���ͨ��
                .setResizeOptions(new ResizeOptions(view.getLayoutParams().width, view.getLayoutParams().height))//������С
//            .setSource(Uri uri)//����ͼƬ��ַ
                .build();
        return imageRequest;
    }

    //DraweeController ���� DraweeControllerBuilder
    public static DraweeController getDraweeController(ImageRequest imageRequest,InstrumentedDraweeView view){
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//            .reset()//����
                .setAutoPlayAnimations(true)//�Զ�����ͼƬ����
//            .setCallerContext(callerContext)//�ص�
                .setControllerListener(view.getListener())//����ͼƬ������ϵ�
//            .setDataSourceSupplier(dataSourceSupplier)//����Դ
//            .setFirstAvailableImageRequests(firstAvailableImageRequests)//����ͼƬ���ã��ɼ���ImageRequest����
                .setImageRequest(imageRequest)//���õ���ͼƬ���󡫡���������setFirstAvailableImageRequests���ã����setLowResImageRequestΪ�߷ֱ��ʵ�ͼ
//            .setLowResImageRequest(ImageRequest.fromUri(lowResUri))//��������ʾ�ͷֱ��ʵ�ͼ
                .setOldController(view.getController())//DraweeController����
                .setTapToRetryEnabled(true)//������¼���ͼ
                .build();
        return draweeController;
    }

    //Ĭ�ϼ���ͼƬ��ʧ��ͼƬ
    public static Drawable sPlaceholderDrawable;
    public static Drawable sErrorDrawable;

    @SuppressWarnings("deprecation")
    public static void init(final Resources resources) {
        if (sPlaceholderDrawable == null) {
            sPlaceholderDrawable = resources.getDrawable(R.color.grey);
        }
        if (sErrorDrawable == null) {
            sErrorDrawable = resources.getDrawable(R.color.red);
        }
    }

}
