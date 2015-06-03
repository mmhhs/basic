package com.base.feima.baseproject.view.multilayer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.tool.OptionTools;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectItemAdapter extends BaseAdapter{
    public Context context;
    private List<SelectItemModel> list;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private IOnItemClickListener iOnItemClickListener;

    public SelectItemAdapter(Context context, List<SelectItemModel> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.base_adapter_select_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SelectItemModel itemModel = list.get(position);
        viewHolder.nameText.setText(itemModel.getItemName());
        viewHolder.icon.setVisibility(View.GONE);
        if (itemModel.getItemIcon()!=null&&!itemModel.getItemIcon().isEmpty()){
            imageLoader.displayImage(itemModel.getItemIcon(),viewHolder.icon, OptionTools.getBaseOptions(context));
            viewHolder.icon.setVisibility(View.VISIBLE);
        }
        if (itemModel.getItemIconId()>0){
            viewHolder.icon.setBackgroundResource(itemModel.getItemIconId());
            viewHolder.icon.setVisibility(View.VISIBLE);
        }
        if (itemModel.getItemBackgrountId()>0){
            viewHolder.layout.setBackgroundResource(itemModel.getItemBackgrountId());
        }
        if (itemModel.isSelected()){
            viewHolder.layout.setSelected(true);
        }else {
            viewHolder.layout.setSelected(false);
        }
        final int p = position;
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
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
        @InjectView(R.id.base_adapter_select_item_layout)
        public LinearLayout layout;
        @InjectView(R.id.base_adapter_select_item_text)
        public TextView nameText;
        @InjectView(R.id.base_adapter_select_item_icon)
        public ImageView icon;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }
}