package com.feima.baseproject.activity.welcome;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseFragment;
import com.feima.baseproject.listener.IOnClickListener;
import com.feima.baseproject.view.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WelcomeFragment extends BaseFragment implements OnPageChangeListener {
    public View rootView;
    @InjectView(R.id.fragment_wel_view)
    public ViewPager mViewPager;
    @InjectView(R.id.base_fragment_wel_pageIndicatorView)
    public PageIndicatorView mPageIndicatorView;
	private ViewPagerAdapter adapter1;
	private List<Integer> imgList = new ArrayList<Integer>();
	private List<View> viewList = new ArrayList<View>();
	public IOnClickListener iOnClickListener;
	private boolean clickButton = false;
	
	public WelcomeFragment(){

	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.base_fragment_welcome,null);
            ButterKnife.inject(this, rootView);
            init();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void init(){
		imgList.add(R.color.title_color);
		for(int i=0;i<imgList.size();i++){
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.base_adapter_viewpager_welcome, null);
			ImageView img = (ImageView) view.findViewById(R.id.base_adapter_wel_imageView);
			img.setImageResource(imgList.get(i));
			ImageButton imgButton = (ImageButton) view.findViewById(R.id.base_adapter_wel_imageButton);
			if(i==(imgList.size()-1)){
				if(clickButton){
					imgButton.setVisibility(View.VISIBLE);
					imgButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (iOnClickListener!=null){
								iOnClickListener.onClick(0);
							}
						}
					});
				}else{
					view.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (iOnClickListener!=null){
								iOnClickListener.onClick(0);
							}
						}
						
					});
				}
			}else{
				imgButton.setVisibility(View.GONE);
			}
			
			viewList.add(view);
		}
//		adapter = new SingleViewPagerAdapter(context,imgList);
		adapter1 = new ViewPagerAdapter(viewList);
		mViewPager.setAdapter(adapter1);
		mViewPager.setOnPageChangeListener(this);
		mPageIndicatorView.setTotalPage(imgList.size());
		mPageIndicatorView.setCameraDistance(20);
		mPageIndicatorView.setRedius(6);
		mPageIndicatorView.setSpace(20);
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible){
			return;
		}
		//TODO 判断是否加载过数据

		//TODO 网络加载

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
        mPageIndicatorView.setCurrentPage(arg0);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub


	}

	public void setiOnClickListener(IOnClickListener iOnClickListener) {
		this.iOnClickListener = iOnClickListener;
	}
}
