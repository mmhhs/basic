package com.base.feima.baseproject.view.chooseimages;


// add by liumy  2013 7-19

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.tool.OptionTools;
import com.base.feima.baseproject.view.multilayer.IOnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class ChooseImagesPreviewAdapter extends PagerAdapter {

    private List<String> list;
    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private IOnItemClickListener iOnItemClickListener;

    public ChooseImagesPreviewAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public void destroyItem(View view, int position, Object object) {
        // TODO Auto-generated method stub

        ((ViewPager) view).removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
        // TODO Auto-generated method stub
    }

    @Override
    public Object instantiateItem(View view, final int position) {
        View convertView = null;
        convertView = LayoutInflater.from(context).inflate(R.layout.base_adapter_choose_images_preview, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.base_adapter_choose_images_preview_imageView);
        imageLoader.displayImage("file://"+list.get(position),imageView, OptionTools.getNoDiscOptions(context));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnItemClickListener!=null){
                    iOnItemClickListener.onItemClick(position);
                }
            }
        });
        ((ViewPager) view).addView(convertView, 0);
        return convertView;
    }

    @Override
    public int getCount() {
        if(list != null){
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

    public IOnItemClickListener getiOnItemClickListener() {
        return iOnItemClickListener;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }
}
