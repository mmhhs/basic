package com.base.feima.baseproject.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.base.feima.baseproject.R;


public class PageIndicatorView extends View {
	private int mCurrentPage = 0;

	private int selectedColor;

	private int unselectedColor;

	private float redius;

	private int space;

	private int alpha;
	
	/**
	 * 选中颜色值
	 */
	private String selectedColorString = "#FF0000";
	/**
	 * 未选中颜色值
	 */
	private String unselectedColorString = "#778899";
	/**
	 * 总页数
	 */
	private int mTotalPage = 0;
	/**
	 * 半径
	 */
	private int rediusSize = 5;
	/**
	 * 间距
	 */
	private int spaceSize = 20;
	

	public PageIndicatorView(Context context) {
		super(context);
	}

	public PageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray customAttrs = context.obtainStyledAttributes(attrs,
				R.styleable.PageIndicatorView);
		redius = customAttrs.getFloat(R.styleable.PageIndicatorView_redius, rediusSize);
		space = customAttrs.getInt(R.styleable.PageIndicatorView_space, spaceSize);
		alpha = customAttrs.getInt(R.styleable.PageIndicatorView_alpha, 0x00);
		selectedColor = customAttrs.getColor(
				R.styleable.PageIndicatorView_selectedColor,
				Color.parseColor(selectedColorString));
		unselectedColor = customAttrs.getColor(
				R.styleable.PageIndicatorView_unselectedColor,
				Color.parseColor(unselectedColorString));
		customAttrs.recycle();
	}

	public void setTotalPage(int nPageNum) {
		mTotalPage = nPageNum;
		if (mCurrentPage >= mTotalPage)
			mCurrentPage = mTotalPage - 1;
	}

	public int getCurrentPage() {
		return mCurrentPage;
	}

	public void setCurrentPage(int nPageIndex) {
		if (nPageIndex < 0 || nPageIndex >= mTotalPage)
			return;

		if (mCurrentPage != nPageIndex) {
			mCurrentPage = nPageIndex;
			this.invalidate();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAlpha(alpha);

		Rect r = new Rect();
		getDrawingRect(r);

		canvas.drawRect(r, paint);

		if (mTotalPage == 1)
			return;

		float x = (r.width() - (redius * 2 * mTotalPage + space
				* (mTotalPage - 1))) / 2;
		float y = r.height() / 2;

		for (int i = 0; i < mTotalPage; i++) {
			if (i == mCurrentPage) {
				paint.setColor(selectedColor);
			} else {
				paint.setColor(unselectedColor);
			}

			canvas.drawCircle(x, y, redius, paint);

			x += redius * 2 + space;
		}
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public void setmCurrentPage(int mCurrentPage) {
		this.mCurrentPage = mCurrentPage;
	}

	public void setSelectedColor(int selectedColor) {
		this.selectedColor = selectedColor;
	}

	public void setUnselectedColor(int unselectedColor) {
		this.unselectedColor = unselectedColor;
	}

	public void setRedius(int redius) {
		this.redius = redius;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
}
