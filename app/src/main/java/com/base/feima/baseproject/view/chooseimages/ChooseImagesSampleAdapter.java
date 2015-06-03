package com.base.feima.baseproject.view.chooseimages;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.tool.ImageTools;
import com.base.feima.baseproject.tool.OptionTools;
import com.base.feima.baseproject.view.multilayer.IOnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChooseImagesSampleAdapter extends BaseAdapter{
    public Context context;
    private List<String> list;
    private int itemWidth = 0;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private IOnItemClickListener iOnItemClickListener;

    public ChooseImagesSampleAdapter(Context context, List<String> list,int screenWidth) {
        this.context = context;
        this.list = list;
        itemWidth = (screenWidth- 2* ImageTools.dip2px(context, 1))/3;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView,final ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.base_adapter_choose_images_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        final String path = list.get(position);
        viewHolder.containLayout.getLayoutParams().width = itemWidth;
        viewHolder.containLayout.getLayoutParams().height = itemWidth;
        viewHolder.checkBox.setVisibility(View.GONE);
        if (position==(list.size())){
            viewHolder.contentImage.setImageResource(R.drawable.base_shape_choose_images);
        }else {
            imageLoader.displayImage("file://"+list.get(position),viewHolder.contentImage, OptionTools.getNoDiscOptions(context));
        }
        final int p = position;
        viewHolder.containLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOnItemClickListener!=null){
                    iOnItemClickListener.onItemClick(p);
                }
            }
        });
        return convertView;
    }

    public final static class ViewHolder {
        @InjectView(R.id.base_adapter_choose_images_grid_content)
        public ImageView contentImage;
        @InjectView(R.id.base_adapter_choose_images_grid_selector)
        public ImageView selectorImage;
        @InjectView(R.id.base_adapter_choose_images_grid_checkBox)
        public CheckBox checkBox;
        @InjectView(R.id.base_adapter_choose_images_grid_layout)
        public RelativeLayout containLayout;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }
    }

    public IOnItemClickListener getiOnItemClickListener() {
        return iOnItemClickListener;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }
}