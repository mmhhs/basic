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

package com.base.feima.baseproject.util.image.fresco;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.base.feima.baseproject.listener.IOnLoadListener;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;



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
