package com.feima.baseproject.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feima.baseproject.R;

import java.util.List;


public class PopupwindowListAdapter extends BaseAdapter {
	public Context context;
	public List<String> list;

	public PopupwindowListAdapter(Context context, List<String> list) {
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
		ViewHolder viewHolder ;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.base_view_dialog_list_item, null);
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.base_view_dialog_list_item_name);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			viewHolder.text.setText(list.get(position));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	public final static class ViewHolder {
		TextView text;

	}
}
