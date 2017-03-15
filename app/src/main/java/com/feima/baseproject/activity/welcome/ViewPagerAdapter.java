package com.feima.baseproject.activity.welcome;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> viewList;
	
	
	public ViewPagerAdapter(List<View> imageViewList){
		this.viewList = imageViewList;
	}
	
	public List<View> getViews() {
		return viewList;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public void finishUpdate(ViewGroup container) {
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(viewList.get(position), 0);
		return viewList.get(position);
	}

	@Override
	public int getCount() {
		if(viewList != null){
			return viewList.size();
		}
		return 0;
	}
	

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override 
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	 } 


	public void clear() {
		viewList.clear();
	}

	public void addView(ImageView view){
		viewList.add(view);
	}
}
