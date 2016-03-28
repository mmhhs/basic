package com.feima.baseproject.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnClickListener;
import com.feima.baseproject.util.tool.DensityUtil;


public class GuideUtil {
    private FrameLayout rootLayout;
    private Activity activity;
    private String[] classNames = {"PlayerHomeFragment0", "AccountActivity0",
            "PlayerServiceActivity0", "PlayerServiceActivity1"};
    private int[] guideResourceIds = {R.drawable.base_welcome};
    private IOnClickListener iOnClickListener;
    private float rate = 0.562f;
    private int screenHeight;

    public GuideUtil(FrameLayout rootLayout, Activity activity) {
        this.rootLayout = rootLayout;
        this.activity = activity;
        screenHeight = OptionUtil.getScreenHeight(activity)-OptionUtil.getStatusBarHeight(activity);
    }

    public void addGuideImage(final String className, final int index) {
//        View view = activity.getWindow().getDecorView().findViewById(contentView.getId());//查找通过setContentView上的根布局
        if (rootLayout == null)
            return;
        if (!needGuide(className, index) || SharedUtil.getFirst(activity, "" + className + index)) {//SharedUtil.getFirst(activity, "" + className + index)
            //不需要或引导过了
            return;
        }
//        ViewParent viewParent = rootLayout.getParent();
        if (rootLayout instanceof FrameLayout) {
            final FrameLayout frameLayout = (FrameLayout) rootLayout;
            int guideResourceId = getGuideResourceId(className, index);
            if (guideResourceId != 0) {//设置了引导图片
                final View view = LayoutInflater.from(activity).inflate(R.layout.base_view_guide,null);
                ImageView guideImage = (ImageView)view.findViewById(R.id.base_view_guide_image);
                guideImage.getLayoutParams().width = (int)(screenHeight*rate)- DensityUtil.dip2px(activity, 2);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(params);
                guideImage.setImageResource(guideResourceId);
                guideImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frameLayout.removeView(view);
                        SharedUtil.saveFisrt(activity, "" + className + index, true);//设为已引导
                        if (iOnClickListener != null) {
                            iOnClickListener.onClick(index);
                        }
                    }
                });
                frameLayout.addView(view);//添加引导图片

            }
        }
    }

    private int getGuideResourceId(String className, int index) {
        int resourceId = 0;
        for (int i = 0; i < classNames.length; i++) {
            if (classNames[i].equals("" + className + index)) {
                resourceId = guideResourceIds[i];
            }
        }
        return resourceId;
    }

    private boolean needGuide(String className, int index) {
        boolean need = false;
        for (String cls : classNames) {
            if (cls.equals("" + className + index)) {
                need = true;
            }
        }
        return need;
    }

    public void setiOnClickListener(IOnClickListener iOnClickListener) {
        this.iOnClickListener = iOnClickListener;
    }
}