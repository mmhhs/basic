package com.base.feima.baseproject.tool.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.base.feima.baseproject.R;


public class ViewTool {
	private static ViewTool viewTool = null;
	private Context context;
	private OnTryClickListener onTryClickListener;
	
	public interface OnTryClickListener{
		public void onClick(int position);
	}	

	public void setOnTryClickListener(
			OnTryClickListener onTryClickListener) {
		this.onTryClickListener = onTryClickListener;
	}

	private void doOnTryClickListener(int position){
		if(onTryClickListener!=null){
			this.onTryClickListener.onClick(position);
		}		
	}
	
	private View loadView;
	private View errorView;
	
	public ViewTool(){
		
	}
	
	public ViewTool(Context paramContext){
		this.context = paramContext;
	}
	
	public static synchronized ViewTool getInstanceViewTool(Context paramContext)
	  {
	      if (viewTool == null)
	    	  viewTool = new ViewTool(paramContext);
	      ViewTool viewTools = viewTool;
	      return viewTools;
	  }
	
	public void addLoadView(Context context,String loadsString,View pullToRefreshView,LinearLayout linearLayout){
		try {
			pullToRefreshView.setVisibility(View.GONE);
			linearLayout.setVisibility(View.VISIBLE);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			loadView = getLoadView(context, loadsString);
			
			linearLayout.removeAllViews();
			linearLayout.addView(loadView, layoutParams);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void removeLoadView(View pullToRefreshView,LinearLayout linearLayout){
		try {
			pullToRefreshView.setVisibility(View.VISIBLE);
			linearLayout.setVisibility(View.GONE);			
			linearLayout.removeAllViews();		
			loadView = null;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void addErrorView(Context context,String errorString,View pullToRefreshView,LinearLayout linearLayout,OnTryClickListener onTryClickListener){
		try {
			pullToRefreshView.setVisibility(View.GONE);
			linearLayout.setVisibility(View.VISIBLE);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			
			errorView = getErrorView(context, errorString);
			
			linearLayout.removeAllViews();
			linearLayout.addView(errorView, layoutParams);
			this.onTryClickListener = onTryClickListener;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void removeErrorView(View pullToRefreshView,LinearLayout linearLayout){
		try {
			pullToRefreshView.setVisibility(View.VISIBLE);
			linearLayout.setVisibility(View.GONE);			
			linearLayout.removeAllViews();	
			this.onTryClickListener = null;
			errorView = null;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public View getLoadView(Context context,String loadsString) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_popupwindow_load,null, false);
	    ImageView loadImageView = (ImageView) view.findViewById(R.id.pop_load_image);
	    TextView loadText = (TextView) view.findViewById(R.id.pop_load_text);
        LinearLayout popupwindowLinear = (LinearLayout) view.findViewById(R.id.pop_load_linear);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.base_load_rotate);
//      loadImageView.setImageResource(R.anim.animation_rotate);
//      AnimationDrawable animationDrawable = (AnimationDrawable)animation_rotate.getDrawable();
        if(!loadsString.isEmpty()){
        	loadText.setText(loadsString);
        }    
        loadImageView.startAnimation(anim);
              					
		return view;
	}
	
	public View getErrorView(Context context,String errorString) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_popupwindow_error,null, false);
	    TextView titleText = (TextView) view.findViewById(R.id.pop_error_item1);
        LinearLayout popupwindowLinear = (LinearLayout) view.findViewById(R.id.pop_error_linear);
        if(!errorString.isEmpty()){
        	titleText.setText(errorString);
        }
		
		popupwindowLinear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				doOnTryClickListener(0);				
			}
			
		});
		
		return view;
	}
	
}