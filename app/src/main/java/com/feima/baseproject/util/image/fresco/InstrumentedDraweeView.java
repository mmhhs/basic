
package com.feima.baseproject.util.image.fresco;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.feima.baseproject.listener.IOnLoadListener;


/**
 * {@link com.facebook.drawee.view.SimpleDraweeView} with instrumentation.
 */
public class InstrumentedDraweeView extends SimpleDraweeView {

  private ControllerListener<Object> mListener;
  private IOnLoadListener iOnLoadListener;

  public InstrumentedDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
    super(context, hierarchy);
    init();
  }

  public InstrumentedDraweeView(Context context) {
    super(context);
    init();
  }

  public InstrumentedDraweeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public InstrumentedDraweeView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    mListener = new BaseControllerListener<Object>() {
      @Override
      public void onSubmit(String id, Object callerContext) {
        if (iOnLoadListener!=null){
          iOnLoadListener.onStart();
        }
      }
      @Override
      public void onFinalImageSet(
        String id,
        @Nullable Object imageInfo,
        @Nullable Animatable animatable) {
        if (iOnLoadListener!=null){
          iOnLoadListener.onSuccess();
        }
      }
      @Override
      public void onFailure(String id, Throwable throwable) {
        if (iOnLoadListener!=null){
          iOnLoadListener.onFailure();
        }
      }
      @Override
      public void onRelease(String id) {
        if (iOnLoadListener!=null){
          iOnLoadListener.onCancel();
        }
      }
    };
  }



  @Override
  public void onDraw(final Canvas canvas) {
    super.onDraw(canvas);
  }

  @Override
  public void setImageURI(Uri uri, @Nullable Object callerContext) {
    SimpleDraweeControllerBuilder controllerBuilder = getControllerBuilder()
        .setUri(uri)
        .setCallerContext(callerContext)
        .setOldController(getController());
    if (controllerBuilder instanceof AbstractDraweeControllerBuilder) {
      ((AbstractDraweeControllerBuilder<?,?,?,?>) controllerBuilder)
          .setControllerListener(mListener);
    }
    setController(controllerBuilder.build());
  }

  public ControllerListener<Object> getListener() {
    return mListener;
  }



  public IOnLoadListener getiOnLoadListener() {
    return iOnLoadListener;
  }

  public void setiOnLoadListener(IOnLoadListener iOnLoadListener) {
    this.iOnLoadListener = iOnLoadListener;
  }
}
