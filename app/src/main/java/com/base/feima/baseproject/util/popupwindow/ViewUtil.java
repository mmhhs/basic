package com.base.feima.baseproject.util.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnTryClickListener;
import com.base.feima.baseproject.util.tool.StringUtil;


public class ViewUtil {
	private IOnTryClickListener onTryClickListener;
    private View loadView;
    private View errorView;

    public ViewUtil(){

    }

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

	public void addEmptyView(Context context,String loadString,View contentView,LinearLayout loadLayout){
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

	
	public View getLoadView(Context context,String loadsString) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_pop_load,null, false);
	    TextView loadText = (TextView) view.findViewById(R.id.base_pop_load_text);
        if(!loadsString.isEmpty()){
        	loadText.setText(loadsString);
        }
		return view;
	}
	
	public View getErrorView(Context context,String errorString) {
		View view = LayoutInflater.from(context).inflate(R.layout.base_pop_error,null, false);
	    TextView titleText = (TextView) view.findViewById(R.id.base_pop_error_item1);
        LinearLayout containLayout = (LinearLayout) view.findViewById(R.id.base_pop_error_contain);
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

    public void setOnTryClickListener(
            IOnTryClickListener onTryClickListener) {
        this.onTryClickListener = onTryClickListener;
    }

    private void doOnTryClickListener(){
        if(onTryClickListener!=null){
            this.onTryClickListener.onTry();
        }
    }

	public void addEmptyView(Context context,String loadString,View contentView,ViewGroup loadLayout){
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
	
}