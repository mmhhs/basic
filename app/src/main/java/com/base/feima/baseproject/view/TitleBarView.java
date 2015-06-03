package com.base.feima.baseproject.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class TitleBarView extends LinearLayout{
	private final String TAGS = "TitleBarView";
	private final PageListener pageListener = new PageListener();
	public OnPageChangeListener delegatePageListener;
	private ViewPager pager;
	private List<ImageButton> imageButtonList = new ArrayList<ImageButton>();
	private List<Integer> drawableList;
	private LayoutParams defaultTabLayoutParams;
	private Context context;
	private boolean moveAble = true;
	public OnChangeListener onChangeListener;

	public TitleBarView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		defaultTabLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		defaultTabLayoutParams.gravity = Gravity.CENTER_VERTICAL;
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		defaultTabLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		defaultTabLayoutParams.gravity = Gravity.CENTER_VERTICAL;
	}

	public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		defaultTabLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		defaultTabLayoutParams.gravity = Gravity.CENTER_VERTICAL;
	}
	
	public void init(Context context,List<Integer> drawableList,ViewPager pager,int index){
		this.context = context;
		this.drawableList = drawableList;
		setViewPager(pager);
		addImageButtonList();		
		setImageButtonStatus(index);
		setPager(index);
	}
	
	public void addImageButtonList(){
		if(drawableList!=null&&drawableList.size()>0){
			for(int i=0;i<drawableList.size();i++){
				addImageButton(i);
			}
		}else{
			Log.i(TAGS, "drawableList null or empty");
		}
	}
	
	private void addImageButton(final int position){
		ImageButton imageButton = new ImageButton(context);
		imageButton.setBackgroundResource(drawableList.get(position));
		imageButton.setLayoutParams(defaultTabLayoutParams);		
		imageButton.setSelected(false);
		imageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(moveAble){
					setImageButtonStatus(position);
					setPager(position);
				}
				
			}
			
		});
		imageButtonList.add(imageButton);
		addView(imageButton);
	}
	
	private void setImageButtonStatus(int position){
		if(imageButtonList!=null&&position<imageButtonList.size()){
			for(int i=0;i<imageButtonList.size();i++){
				imageButtonList.get(i).setSelected(false);
			}
			imageButtonList.get(position).setSelected(true);
		}
//		doOnChangeListener(position);
	}
	
	private void setPager(int position){
		if(this.pager!=null&&this.pager.getAdapter()!=null&&position<this.pager.getAdapter().getCount()){
			this.pager.setCurrentItem(position, true);
		}
	}
	
	public void setViewPager(ViewPager pager) {
		this.pager = pager;

		if (pager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}

		pager.setOnPageChangeListener(pageListener);

		notifyDataSetChanged();
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	public void notifyDataSetChanged() {

		

		
	}
	
	
	
	public boolean isMoveAble() {
		return moveAble;
	}

	public void setMoveAble(boolean moveAble) {
		this.moveAble = moveAble;
	}



	private class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if(moveAble){
				setImageButtonStatus(position);
				doOnChangeListener(position);				
			}
		}
		
	}
	
	public interface OnChangeListener {
 		  public void   onClick(int position);
 	}
 	public void doOnChangeListener(int position) {
 		if(onChangeListener!=null){
 			onChangeListener.onClick(position);
 		}
 	    
 	  }
 	 
    public void setOnChangeListener(OnChangeListener callback) {
        this.onChangeListener = callback;
    }
	
}