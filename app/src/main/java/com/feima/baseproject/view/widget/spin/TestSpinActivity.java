package com.feima.baseproject.view.widget.spin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.feima.baseproject.R;

public class TestSpinActivity extends Activity {

	private ProgressWheel pwOne, pwTwo;
	private PieProgress mPieProgress1, mPieProgress2;
	boolean wheelRunning, pieRunning;
	int wheelProgress = 0, pieProgress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_progress);

		pwOne = (ProgressWheel) findViewById(R.id.progress_bar_one);
		pwOne.spin();
		pwTwo = (ProgressWheel) findViewById(R.id.progress_bar_two);
		new Thread(r).start();

		mPieProgress1 = (PieProgress) findViewById(R.id.pie_progress1);
		mPieProgress2 = (PieProgress) findViewById(R.id.pie_progress2);
		new Thread(indicatorRunnable).start();

		Button startBtn = (Button) findViewById(R.id.btn_start);
		startBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!wheelRunning) {
					wheelProgress = 0;
					pwTwo.resetCount();
					new Thread(r).start();
				}
			}
		});

		Button pieStartBtn = (Button) findViewById(R.id.btn_start2);
		pieStartBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!pieRunning) {
					pieProgress = 0;
					new Thread(indicatorRunnable).start();
				}
			}
		});
	}

	final Runnable r = new Runnable() {
		public void run() {
			wheelRunning = true;
			while (wheelProgress < 361) {
				pwTwo.incrementProgress();
				wheelProgress++;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			wheelRunning = false;
		}
	};

	final Runnable indicatorRunnable = new Runnable() {
		public void run() {
			pieRunning = true;
			while (pieProgress < 361) {
				mPieProgress1.setProgress(pieProgress);
				mPieProgress2.setProgress(pieProgress);
				pieProgress += 2;;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			pieRunning = false;
		}
	};
}
