package com.feima.baseproject.util;

import android.app.Activity;

import com.feima.baseproject.R;
import com.feima.baseproject.view.ultimate.UltimateRecyclerView;
import com.feima.baseproject.view.ultimate.divideritemdecoration.HorizontalDividerItemDecoration;


public class RecyclerUtil {
    /**
     * 添加分割线
     * @param ultimateRecyclerView
     * @param activity
     */
    public static void addItemDecoration(UltimateRecyclerView ultimateRecyclerView, Activity activity){
        ultimateRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(activity)
                        .color(activity.getResources().getColor(R.color.line_color))
                        .size(activity.getResources().getDimensionPixelSize(R.dimen.line_h))
                        .build());
    }

}