package com.feima.baseproject.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.feima.baseproject.R;

import java.util.List;


public class SingleViewPagerAdapter extends PagerAdapter {

    private List<Integer> list;
    private Context context;

    public final String Tag = "ViewPagerAdapter";
    public int width;


    public SingleViewPagerAdapter(Context ct, List<Integer> list) {
        this.context = ct;
        this.list = list;


    }


    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        // TODO Auto-generated method stub

        view.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        // TODO Auto-generated method stub
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View convertView = null;
        convertView = LayoutInflater.from(context).inflate(R.layout.base_adapter_viewpager_welcome, null);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.base_adapter_wel_imageView);
        ImageButton imgButton = (ImageButton) convertView.findViewById(R.id.base_adapter_wel_imageButton);

        imgView.setImageDrawable(context.getResources().getDrawable(list.get(position)));


        view.addView(convertView, 0);

        return convertView;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return (arg0 == arg1);
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    final static class ViewHolder {
        ImageButton imgButton;
        ImageView imgView;
    }


}
