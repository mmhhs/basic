package com.base.feima.baseproject.tool.popupwindow;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.base.feima.baseproject.R;


public class PopupwindowListAdapter extends BaseAdapter {
	public Context context;
	public String[] list;

	public PopupwindowListAdapter(Context context, String[] list) {
		this.context = context;
		this.list = list;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder ;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			
			convertView = LayoutInflater.from(context).inflate(
					R.layout.base_popupwindow_list_item, null);
			
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.pop_list_item_name);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		try {
			viewHolder.text.setText(list[position]);
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		return convertView;
	}

	public final static class ViewHolder {
		TextView text;
		
	}
}
