package com.feima.baseproject.util;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AnimUtil {

    public static void startAnim(Context context,LinearLayout linearLayout,int animId){
        try {
            Animation anim = AnimationUtils.loadAnimation(context, animId);
            linearLayout.startAnimation(anim);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startAnim(Context context,FrameLayout frameLayout,int animId){
        try {
            Animation anim = AnimationUtils.loadAnimation(context, animId);
            frameLayout.startAnimation(anim);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startAnim(Context context,ImageView imageView,int animId){
        try {
            Animation anim = AnimationUtils.loadAnimation(context, animId);
            imageView.startAnimation(anim);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha   //0.0-1.0
     */
    public static void setBackgroundAlpha(Activity activity,float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

}