package com.feima.baseproject.util;

import android.content.Context;

import com.feima.baseproject.R;
import com.feima.baseproject.view.ultimate.UltimateRecyclerView;
import com.feima.baseproject.view.ultimate.divideritemdecoration.HorizontalDividerItemDecoration;


public class RecyclerUtil {
    /**
     * 添加分割线
     * @param ultimateRecyclerView
     * @param context
     */
    public static void addItemDecoration(UltimateRecyclerView ultimateRecyclerView, Context context){
        ultimateRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(context)
                        .color(context.getResources().getColor(R.color.line_color))
                        .size(context.getResources().getDimensionPixelSize(R.dimen.line_h))
                        .build());
    }

}