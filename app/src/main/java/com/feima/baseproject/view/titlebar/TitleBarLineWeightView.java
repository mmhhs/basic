package com.feima.baseproject.view.titlebar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feima.baseproject.R;
import com.feima.baseproject.util.tool.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class TitleBarLineWeightView extends LinearLayout {
	private final String TAGS = "TitleBarLineWeightView";
	private final PageListener pageListener = new PageListener();
	public OnPageChangeListener delegatePageListener;
	private ViewPager pager;
	private List<TextView> textViewList = new ArrayList<TextView>();
	private List<TitleItem> titleItemList;
	private LayoutParams defaultTabLayoutParams,lineLayoutParams;
	private Context context;
	private boolean moveAble = true;
	public OnChangeListener onChangeListener;

	public TitleBarLineWeightView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public TitleBarLineWeightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public TitleBarLineWeightView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	public void init(Context context, List<TitleItem> titleItemList,
					 ViewPager pager, int index) {
        defaultTabLayoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        defaultTabLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        defaultTabLayoutParams.weight = 1;
		lineLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		this.context = context;
		this.titleItemList = titleItemList;
		setViewPager(pager);
		addTextViewList();
		setItemStatus(index);
		setPager(index);
	}

	public void addTextViewList() {
		if (titleItemList != null && titleItemList.size() > 0) {
			for (int i = 0; i < titleItemList.size(); i++) {
				addItem(i);
			}
		} else {
			Log.i(TAGS, "drawableList null or empty");
		}
	}

	private void addItem(final int position) {
		TitleItem titleItem = titleItemList.get(position);
		TextView textView = new TextView(context);
		textView.setGravity(Gravity.CENTER);
		textView.setLineSpacing(DensityUtil.dip2px(context, 2),1);
		textView.setLayoutParams(defaultTabLayoutParams);
		textView.setText(titleItem.getName());
        textView.setBackgroundResource(titleItem.getBackgroundResourseId());
		textView.setTextAppearance(context, titleItem.getTextStyle());
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (moveAble) {
					setItemStatus(position);
					setPager(position);
				}

			}

		});
		textViewList.add(textView);
		addView(textView);
        if (position!=(titleItemList.size()-1)){
            View lineView = LinearLayout.inflate(context, R.layout.base_include_line,null);
            addView(lineView,lineLayoutParams);
        }
	}

	public void setTitles(List<TitleItem> list){
		for (int i=0;i<textViewList.size();i++){
			textViewList.get(i).setText(list.get(i).getName());
		}
	}

	private void setItemStatus(int position) {
		if (textViewList != null && position < textViewList.size()) {
			for (int i = 0; i < textViewList.size(); i++) {
				textViewList.get(i).setSelected(false);
			}
			textViewList.get(position).setSelected(true);
		}
	}

	private void setPager(int position) {
		if (this.pager != null && this.pager.getAdapter() != null
				&& position < this.pager.getAdapter().getCount()) {
			this.pager.setCurrentItem(position, true);
		}
	}

	public void setViewPager(ViewPager pager) {
		this.pager = pager;

		if (pager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
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
			if (moveAble) {
				setItemStatus(position);
				doOnChangeListener(position);
			}
		}

	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static class TitleItem {
		public String name;
		public int textStyle;
        public int backgroundResourseId;
		public TitleItem() {

		}

        public TitleItem(String name, int textStyle) {
            this.name = name;
            this.textStyle = textStyle;
        }

        public TitleItem(String name, int textStyle, int backgroundResourseId) {
            this.name = name;
            this.textStyle = textStyle;
            this.backgroundResourseId = backgroundResourseId;
        }

        public int getBackgroundResourseId() {
            return backgroundResourseId;
        }

        public void setBackgroundResourseId(int backgroundResourseId) {
            this.backgroundResourseId = backgroundResourseId;
        }



        public int getTextStyle() {
			return textStyle;
		}


		public void setTextStyle(int textStyle) {
			this.textStyle = textStyle;
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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