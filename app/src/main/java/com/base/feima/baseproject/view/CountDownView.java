package com.base.feima.baseproject.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.listener.IOnTimerListener;
import com.base.feima.baseproject.util.TimerLimit;
import com.base.feima.baseproject.util.tool.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CountDownView extends LinearLayout{
	private final String TAGS = "CountDownView";
	private LayoutParams defaultTabLayoutParams;
	private Context context;
	private CountDownViewHolder countDownViewHolder;
	private Timer countDownTimeTimer;
	private TimerLimit timerLimit;
	private Handler timerHandler;
	private String showTime ="00:00:00";
	private String countDownTime ="0";//倒计时毫秒值
	private boolean showDay = true;
	private List<TextView> timeTextList = new ArrayList<TextView>();

	private int timeTextColor;
	private int timeUnitTextColor;
	private int timeTextBackground;
	private float timeTextSize;
	private float timeUnitTextSize;
	private String timeTextColorString = "#ffffff";
	private String timeUnitTextColorString = "#ffffff";
	private int timeTextSizeDefault=12;
	private int timeUnitTextSizeDefault=12;

	private IOnTimerListener iOnTimerListener;

	public CountDownView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public CountDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		TypedArray customAttrs = context.obtainStyledAttributes(attrs,
				R.styleable.CountDownView);
		timeTextColor = customAttrs.getColor(
				R.styleable.CountDownView_timeTextColor,
				Color.parseColor(timeTextColorString));
		timeUnitTextColor = customAttrs.getColor(
				R.styleable.CountDownView_timeUnitTextColor,
				Color.parseColor(timeUnitTextColorString));
		timeTextBackground = customAttrs.getResourceId(
				R.styleable.CountDownView_timeTextBackground,
				R.color.clear);
		timeTextSize = customAttrs.getDimension(
				R.styleable.CountDownView_timeTextSize,
				timeTextSizeDefault);
		timeUnitTextSize = customAttrs.getDimension(
				R.styleable.CountDownView_timeUnitTextSize,
				timeUnitTextSizeDefault);
		customAttrs.recycle();
		init();
	}

	public CountDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		timeTextSizeDefault = DensityUtil.sp2px(context,12);
		timeUnitTextSizeDefault = DensityUtil.sp2px(context,12);
		TypedArray customAttrs = context.obtainStyledAttributes(attrs,
				R.styleable.CountDownView);
		timeTextColor = customAttrs.getColor(
				R.styleable.CountDownView_timeTextColor,
				Color.parseColor(timeTextColorString));
		timeUnitTextColor = customAttrs.getColor(
				R.styleable.CountDownView_timeUnitTextColor,
				Color.parseColor(timeUnitTextColorString));
		timeTextBackground = customAttrs.getResourceId(
				R.styleable.CountDownView_timeTextBackground,
				R.color.clear);
		timeTextSize = customAttrs.getDimension(
				R.styleable.CountDownView_timeTextSize,
				timeTextSizeDefault);
		timeUnitTextSize = customAttrs.getDimension(
				R.styleable.CountDownView_timeUnitTextSize,
				timeUnitTextSizeDefault);
		customAttrs.recycle();
		init();
	}
	
	public void init(){
		defaultTabLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		defaultTabLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		addView();
	}

	private void addView(){
		removeAllViews();
		View view = LayoutInflater.from(context).inflate(R.layout.base_view_countdown,null);
		countDownViewHolder = new CountDownViewHolder(view);
		countDownViewHolder.dayText.setTextColor(timeTextColor);
		countDownViewHolder.dayText.setTextSize(timeTextSize);
		countDownViewHolder.dayText.setBackgroundResource(timeTextBackground);
		countDownViewHolder.hourText.setTextColor(timeTextColor);
		countDownViewHolder.hourText.setTextSize(timeTextSize);
		countDownViewHolder.hourText.setBackgroundResource(timeTextBackground);
		countDownViewHolder.minuteText.setTextColor(timeTextColor);
		countDownViewHolder.minuteText.setTextSize(timeTextSize);
		countDownViewHolder.minuteText.setBackgroundResource(timeTextBackground);
		countDownViewHolder.secondText.setTextColor(timeTextColor);
		countDownViewHolder.secondText.setTextSize(timeTextSize);
		countDownViewHolder.secondText.setBackgroundResource(timeTextBackground);
		countDownViewHolder.dayUnitText.setTextColor(timeUnitTextColor);
		countDownViewHolder.dayUnitText.setTextSize(timeUnitTextSize);
		countDownViewHolder.hourUnitText.setTextColor(timeUnitTextColor);
		countDownViewHolder.hourUnitText.setTextSize(timeUnitTextSize);
		countDownViewHolder.minuteUnitText.setTextColor(timeUnitTextColor);
		countDownViewHolder.minuteUnitText.setTextSize(timeUnitTextSize);
		countDownViewHolder.secondUnitText.setTextColor(timeUnitTextColor);
		countDownViewHolder.secondUnitText.setTextSize(timeUnitTextSize);
		addView(view, defaultTabLayoutParams);
	}


	/**
	 * 启动倒计时
	 */
	public Timer startCountDownTimer(String time) {
		countDownTime = time;
		resetTextList();
		timerLimit = new TimerLimit();
		timerLimit.setNeedDay(showDay);
		timerHandler = new Handler(){
			public void handleMessage(Message msg) {
				try {
					switch (msg.what){
						case 0:
							String[] times = showTime.split(":");
							for (int i=0;i<times.length;i++){
								timeTextList.get(i).setText(times[i]);
							}
							if (iOnTimerListener!=null){
								iOnTimerListener.timeOneSecond(showTime);
							}
							break;
						case 1:
							if (iOnTimerListener!=null){
								iOnTimerListener.timeEnd();
							}
							break;
						case 2:
							if (iOnTimerListener!=null){
								iOnTimerListener.remainFiveMinutes();
							}
							break;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		timerLimit.setiOnTimerListener(new IOnTimerListener() {
			@Override
			public void timeEnd() {
				Message message = new Message();
				message.what = 1;
				timerHandler.sendMessage(message);

			}

			@Override
			public void remainFiveMinutes() {
				Message message = new Message();
				message.what = 2;
				timerHandler.sendMessage(message);
			}

			@Override
			public void timeOneSecond(String time) {
				showTime = time;
				Message message = new Message();
				message.what = 0;
				timerHandler.sendMessage(message);
			}
		});
		if (countDownTimeTimer !=null){
			countDownTimeTimer.cancel();
			countDownTimeTimer = null;
		}
		timerLimit.setTimeTotal(Long.parseLong(countDownTime));
		countDownTimeTimer = timerLimit.startTimer();
		return countDownTimeTimer;
	}

	public void resetTextList(){
		timeTextList.clear();
		if (showDay){
			countDownViewHolder.dayText.setVisibility(VISIBLE);
			countDownViewHolder.dayUnitText.setVisibility(VISIBLE);
			timeTextList.add(countDownViewHolder.dayText);
		}else {
			countDownViewHolder.dayText.setVisibility(GONE);
			countDownViewHolder.dayUnitText.setVisibility(GONE);
		}
		timeTextList.add(countDownViewHolder.hourText);
		timeTextList.add(countDownViewHolder.minuteText);
		timeTextList.add(countDownViewHolder.secondText);
	}

	public void setShowDay(boolean showDay) {
		this.showDay = showDay;
	}

	public void setiOnTimerListener(IOnTimerListener iOnTimerListener) {
		this.iOnTimerListener = iOnTimerListener;
	}

	public void setUnitText(String day,String hour,String minute,String second){
		countDownViewHolder.dayUnitText.setText(day);
		countDownViewHolder.hourUnitText.setText(hour);
		countDownViewHolder.minuteUnitText.setText(minute);
		countDownViewHolder.secondUnitText.setText(second);
	}

	public CountDownViewHolder getCountDownViewHolder() {
		return countDownViewHolder;
	}

	public class CountDownViewHolder {
		@InjectView(R.id.base_view_coountdown_day)
		public TextView dayText;
		@InjectView(R.id.base_view_coountdown_day_unit)
		public TextView dayUnitText;
		@InjectView(R.id.base_view_coountdown_hour)
		public TextView hourText;
		@InjectView(R.id.base_view_coountdown_hour_unit)
		public TextView hourUnitText;
		@InjectView(R.id.base_view_coountdown_minute)
		public TextView minuteText;
		@InjectView(R.id.base_view_coountdown_minute_unit)
		public TextView minuteUnitText;
		@InjectView(R.id.base_view_coountdown_second)
		public TextView secondText;
		@InjectView(R.id.base_view_coountdown_second_unit)
		public TextView secondUnitText;

		public CountDownViewHolder(View itemView) {
			ButterKnife.inject(this, itemView);
		}
	}
}