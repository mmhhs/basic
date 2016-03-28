package com.feima.baseproject.view.chooseimages;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feima.baseproject.R;
import com.feima.baseproject.util.image.ImageUtil;
import com.feima.baseproject.util.image.fresco.FrescoUtils;
import com.feima.baseproject.util.image.fresco.InstrumentedDraweeView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChooseImagesFolderAdapter extends BaseAdapter{
    public Context context;
    private List<ImageBean> list;

    public ChooseImagesFolderAdapter(Context context, List<ImageBean> list) {
        this.context = context;
        this.list = list;
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
                    R.layout.base_adapter_choose_images_folder, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageBean imageBean = list.get(position);
        viewHolder.nameText.setText(imageBean.getFolderName());
        viewHolder.countText.setText(imageBean.getImageCounts()+context.getString(R.string.choose_images_unit));
//        imageLoader.displayImage("file://"+imageBean.getTopImagePath(),viewHolder.topImage, OptionTools.getNoDiscOptions(context));

        FrescoUtils.displayImage(viewHolder.topImage, "file://" + imageBean.getTopImagePath(), ImageUtil.dip2px(context, 80), ImageUtil.dip2px(context, 80));
        if (imageBean.getSelected()){
            viewHolder.selectImage.setVisibility(View.VISIBLE);
        }else {
            viewHolder.selectImage.setVisibility(View.GONE);
        }


        return convertView;
    }

    public final static class ViewHolder {
        @InjectView(R.id.base_fresco_fitcenter_imageview)
        public InstrumentedDraweeView topImage;
        @InjectView(R.id.base_adapter_choose_images_folder_select)
        public ImageView selectImage;
        @InjectView(R.id.base_adapter_choose_images_folder_name)
        public TextView nameText;
        @InjectView(R.id.base_adapter_choose_images_folder_count)
        public TextView countText;
        @InjectView(R.id.base_adapter_choose_images_folder_layout)
        public LinearLayout containLayout;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }
    }


}