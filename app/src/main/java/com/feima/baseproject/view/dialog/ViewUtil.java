package com.feima.baseproject.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnTryClickListener;
import com.feima.baseproject.util.tool.StringUtil;


public class ViewUtil {
	private IOnTryClickListener onTryClickListener;
    private View loadView;
    private View errorView;
	private View emptyView;

    public ViewUtil(){

    }

	/**
	 * 添加加载视图
	 * @param context
	 * @param loadString
	 * @param contentView
	 * @param loadLayout
     */
	public void addLoadView(Context context,String loadString,View contentView,LinearLayout loadLayout){
		try {
			contentView.setVisibility(View.GONE);
			loadLayout.setVisibility(View.VISIBLE);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			loadView = getLoadView(context, loadString);
			
			loadLayout.removeAllViews();
			loadLayout.addView(loadView, layoutParams);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void removeLoadView(View contentView,LinearLayout loadLayout){
		try {
			contentView.setVisibility(View.VISIBLE);
			loadLayout.setVisibility(View.GONE);
			loadLayout.removeAllViews();
			loadView = null;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 添加错误视图
	 * @param context
	 * @param errorString
	 * @param contentView
	 * @param loadLayout
	 * @param onTryClickListener
     */
	public void addErrorView(Context context,String errorString,View contentView,LinearLayout loadLayout,IOnTryClickListener onTryClickListener){
		try {
			contentView.setVisibility(View.GONE);
			loadLayout.setVisibility(View.VISIBLE);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			errorView = getErrorView(context, errorString);
			loadLayout.removeAllViews();
			loadLayout.addView(errorView, layoutParams);
			this.onTryClickListener = onTryClickListener;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void removeErrorView(View contentView,LinearLayout loadLayout){
		try {
			contentView.setVisibility(View.VISIBLE);
			loadLayout.setVisibility(View.GONE);
			loadLayout.removeAllViews();
			this.onTryClickListener = null;
			errorView = null;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 添加空视图
	 * @param context
	 * @param str
	 * @param imageResourceId
	 * @param contentView
	 * @param loadLayout
	 * @param onTryClickListener
     */
	public void addEmptyView(Context context, String title, String str, int imageResourceId, View contentView, LinearLayout loadLayout, IOnTryClickListener onTryClickListener){
		try {
			contentView.setVisibility(View.GONE);
			loadLayout.setVisibility(View.VISIBLE);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			emptyView = getEmptyView(context, title,str,imageResourceId);
			loadLayout.removeAllViews();
			loadLayout.addView(emptyView, layoutParams);
			this.onTryClickListener = onTryClickListener;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeEmptyView(View contentView,LinearLayout loadLayout){
		try {
			contentView.setVisibility(View.VISIBLE);
			loadLayout.setVisibility(View.GONE);
			loadLayout.removeAllViews();
			this.onTryClickListener = null;
			emptyView = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public View getLoadView(Context context,String loadsString) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_view_link_load,null, false);
	    TextView loadText = (TextView) view.findViewById(R.id.base_view_link_load_item_text);
        if(!loadsString.isEmpty()){
        	loadText.setText(loadsString);
        }
		return view;
	}
	
	public View getErrorView(Context context,String errorString) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_view_link_error,null, false);
	    TextView titleText = (TextView) view.findViewById(R.id.base_view_link_error_title);
        LinearLayout containLayout = (LinearLayout) view.findViewById(R.id.base_view_link_error_container);
        if(!StringUtil.isEmpty(errorString)){
        	titleText.setText(errorString);
        }
		containLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                doOnTryClickListener();
            }

        });
		return view;
	}

	public View getEmptyView(Context context,String title,String str,int imageResourceId) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_view_link_empty,null, false);
		ImageView contentImage = (ImageView) view.findViewById(R.id.base_view_link_empty_image);
		TextView titleText = (TextView) view.findViewById(R.id.base_view_link_empty_title);
		TextView contentText = (TextView) view.findViewById(R.id.base_view_link_empty_content);
		LinearLayout containLayout = (LinearLayout) view.findViewById(R.id.base_view_link_empty_container);
		if(!StringUtil.isEmpty(title)){
			titleText.setText(title);
		}
		if(!StringUtil.isEmpty(str)){
			contentText.setText(str);
		}
		if (imageResourceId!=0){
			contentImage.setImageResource(imageResourceId);
		}
		containLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOnTryClickListener();
			}

		});
		return view;
	}

    public void setOnTryClickListener(
            IOnTryClickListener onTryClickListener) {
        this.onTryClickListener = onTryClickListener;
    }

    private void doOnTryClickListener(){
        if(onTryClickListener!=null){
            this.onTryClickListener.onTry();
        }
    }

	
}