package com.base.feima.baseproject.util.image.fresco;

import android.content.Context;
import android.net.Uri;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class FrescoUtils{

    public static void displayImage(final InstrumentedDraweeView view, String uri) {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setProgressiveRenderingEnabled(true)
                .setAutoRotateEnabled(true)
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .setControllerListener(view.getListener())
                .setAutoPlayAnimations(true)
                .build();
        view.setController(draweeController);
    }

    public static void displayImage(final InstrumentedDraweeView view, String uri, int viewWidth,int viewHeight) {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                        .setResizeOptions(
                                new ResizeOptions(viewWidth, viewHeight))
                        .setProgressiveRenderingEnabled(true)
                        .setAutoRotateEnabled(true)
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .setControllerListener(view.getListener())
                .setAutoPlayAnimations(true)
                .build();
        view.setController(draweeController);
    }

    public static void init(Context context){
        Fresco.initialize(context, ConfigConstants.getImagePipelineConfig(context));
        FLog.setMinimumLoggingLevel(FLog.WARN);
        Drawables.init(context.getResources());
    }

    public static void shutDown(){
        Fresco.shutDown();
    }


}