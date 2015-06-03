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
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.view.multilayer.IOnItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChooseImagesGridAdapter extends BaseAdapter{
    public Context context;
    private List<String> list;
    private List<String> chooseList;
    private int itemWidth = 0;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private IOnCheckListener iOnCheckListener;
    private IOnItemClickListener iOnItemClickListener;
    private int maxSize;
    private int folderShowIndex;

    public ChooseImagesGridAdapter(Context context, List<String> list, List<String> chooseList,int screenWidth,int maxSize,int folderShowIndex) {
        this.context = context;
        this.list = list;
        this.chooseList = chooseList;
        itemWidth = (screenWidth- 2* ImageTools.dip2px(context, 1))/3;
        this.maxSize = maxSize;
        this.folderShowIndex = folderShowIndex;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
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
        final String path = list.get(position);
        viewHolder.containLayout.getLayoutParams().width = itemWidth;
        viewHolder.containLayout.getLayoutParams().height = itemWidth;
        if (position==0&&folderShowIndex==0){
            viewHolder.contentImage.setImageResource(R.drawable.base_shape_choose_images);
            viewHolder.checkBox.setVisibility(View.GONE);
        }else {
            imageLoader.displayImage("file://"+path,viewHolder.contentImage, OptionTools.getNoDiscOptions(context));
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }
        if (isSelected(path)){
            viewHolder.selectorImage.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(true);
        }else {
            viewHolder.selectorImage.setVisibility(View.GONE);
            viewHolder.checkBox.setChecked(false);
        }
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected(path)){
                    setSelected(path,false);
                }else {
                    setSelected(path,true);
                }
            }
        });
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

    private boolean isSelected(String path){
        boolean result = false;
        for(String imagePath:chooseList){
            if (path.equals(imagePath)){
                result = true;
            }
        }
        return result;
    }

    private void setSelected(String path,boolean isChecked){
        if (isChecked){
            if (!isSelected(path)){
                if (chooseList.size()<maxSize){
                    chooseList.add(path);
                }else {
                    PublicTools.addToast(context, "" + context.getString(R.string.choose_images_max) + maxSize);
                }
            }
        }else {
            if (isSelected(path)){
                chooseList.remove(path);
            }
        }
        notifyDataSetChanged();
        if (iOnCheckListener!=null){
            iOnCheckListener.onCheck(chooseList);
        }
    }

    public IOnCheckListener getiOnCheckListener() {
        return iOnCheckListener;
    }

    public void setiOnCheckListener(IOnCheckListener iOnCheckListener) {
        this.iOnCheckListener = iOnCheckListener;
    }

    public IOnItemClickListener getiOnItemClickListener() {
        return iOnItemClickListener;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }
}