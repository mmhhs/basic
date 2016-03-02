package com.base.feima.baseproject.view.chooseimages;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnItemClickListener;
import com.base.feima.baseproject.util.image.fresco.FrescoUtils;
import com.base.feima.baseproject.util.image.fresco.InstrumentedDraweeView;

import java.util.List;


public class ChooseImagesPreviewAdapter extends PagerAdapter {

    private List<String> list;
    private Context context;
    private IOnItemClickListener iOnItemClickListener;

    public ChooseImagesPreviewAdapter(Context context, List<String> list){
        this.context = context;
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
    public Object instantiateItem(ViewGroup view, final int position) {
        View convertView = null;
        convertView = LayoutInflater.from(context).inflate(R.layout.base_adapter_choose_images_preview, null);
        InstrumentedDraweeView imageView = (InstrumentedDraweeView)convertView.findViewById(R.id.base_fresco_fitcenter_imageview);
        FrescoUtils.displayImage(imageView,"file://"+list.get(position),720,1280);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnItemClickListener!=null){
                    iOnItemClickListener.onItemClick(position);
                }
            }
        });
        view.addView(convertView, 0);
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
