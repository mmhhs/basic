package com.feima.baseproject.view;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.feima.baseproject.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PageView extends LinearLayout {
	private final String TAGS = "PageView";
	private LayoutParams defaultTabLayoutParams;
	private Context context;
	private PageViewHolder pageViewHolder;
	private Handler autoHandler;
	private Timer autoTimer;
	private int pagePosition = 0;
	private int totalPage = 0;
	private int intervalTime = 4000;
	private boolean isPause = false;

	public PageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public PageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public PageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}
	
	public void init(){
		defaultTabLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		defaultTabLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		addView();
	}

	private void addView(){
		removeAllViews();
		View view = LayoutInflater.from(context).inflate(R.layout.base_view_page,null);
		pageViewHolder = new PageViewHolder(view);
		addView(view,defaultTabLayoutParams);
		pageViewHolder.viewPager.setOnPageChangeListener(onPageChangeListener);
	}

	public void setLayoutHeight(int height){
		pageViewHolder.frameLayout.getLayoutParams().height = height;
	}

	public void setAdapter(PagerAdapter adapter){
		pageViewHolder.viewPager.setAdapter(adapter);
	}

	public void setOnTouchListener(OnTouchListener onTouchListener){
		pageViewHolder.viewPager.setOnTouchListener(onTouchListener);
	}

	public void setIndicatorCurrent(int index){
		pageViewHolder.pageIndicatorView.setCurrentPage(index);
	}

	public void setIndicatorTotal(){
		pageViewHolder.pageIndicatorView.setTotalPage(totalPage);
	}

	public void setIndicatorVisibility(int visibility){
		pageViewHolder.pageIndicatorView.setVisibility(visibility);
	}

	public void setCurrentPage(int index){
		pageViewHolder.viewPager.setCurrentItem(index);
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		setIndicatorTotal();
		setIndicatorCurrent(0);
	}

	/**
	 * 启动轮播图定时器
	 */
	public Timer setAutoTimer() {
		pagePosition = 0;
		try {
			if (autoTimer != null) {
				autoTimer.cancel();
			}
			autoHandler = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 1;
				autoHandler.sendMessage(message);
			}
		};
		autoTimer = new Timer(true);
		autoTimer.schedule(task, 10, intervalTime);
		autoHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					if (!isPause){
						try {
							if (totalPage > 0) {
								int current = pagePosition % totalPage;
								pageViewHolder.viewPager.setCurrentItem(current, true);
							}
							pagePosition++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}
		};
		return autoTimer;
	}

	private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			try {
				pagePosition = position;
				setIndicatorCurrent(position);
//				pageViewHolder.pageIndicatorView.setCurrentPage(position);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	public void setIsPause(boolean isPause) {
		this.isPause = isPause;
	}

	public PageViewHolder getPageViewHolder() {
		return pageViewHolder;
	}

	public class PageViewHolder {
		@InjectView(R.id.base_view_page_viewPager)
		public ViewPager viewPager;
		@InjectView(R.id.base_view_page_pageIndicatorView)
		public PageIndicatorView pageIndicatorView;
		@InjectView(R.id.base_view_page_layout)
		public FrameLayout frameLayout;

		public PageViewHolder(View itemView) {
			ButterKnife.inject(this, itemView);
		}
	}
}