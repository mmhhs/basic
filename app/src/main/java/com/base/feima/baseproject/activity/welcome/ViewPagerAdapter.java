package com.base.feima.baseproject.activity.welcome;
// add by liumy  2013 7-19
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> viewst;
	
	
	public ViewPagerAdapter(List<View> imageViewList){
		this.viewst = imageViewList;
	}
	
	public List<View> getViews() {
		return viewst;
	}
	@Override
	public void destroyItem(View view, int position, Object object) {
		// TODO Auto-generated method stub
		
		((ViewPager) view).removeView((View) object);
	}

	@Override
	public void finishUpdate(View container) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object instantiateItem(View view, int position) {
		
		
		((ViewPager) view).addView(viewst.get(position), 0);
		
		return viewst.get(position);
	}

	@Override
	public int getCount() {
		if(viewst != null){
			return viewst.size();
		}
		return 0;
	}
	

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}
	@Override 
	public int getItemPosition(Object object) {  

		   

		    return POSITION_NONE;  

	 } 


	public void clear() {
		// TODO Auto-generated method stub
		
		viewst.clear();
		
		
	}
	public void addView(ImageView view){
		viewst.add(view);
	}
}
