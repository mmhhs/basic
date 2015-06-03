package com.widget.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.widget.wheel.WheelMain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SampleWheelActivity extends Activity {
	WheelMain wheelMain;
	EditText txttime;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        txttime = (EditText)findViewById(R.id.txttime);
//	  	Calendar calendar = Calendar.getInstance();
//	  	txttime.setText(calendar.get(Calendar.YEAR) + "-" +
//	  				    (calendar.get(Calendar.MONTH) + 1 )+ "-" +
//	  				    calendar.get(Calendar.DAY_OF_MONTH) + "");
//        Button btnselecttime = (Button)findViewById(R.id.base_slide_frame_right);
//		btnselecttime.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
//				final View timepickerview=inflater.inflate(R.layout.base_timepicker, null);
//				ScreenInfo screenInfo = new ScreenInfo(MainActivity.this);
//				wheelMain = new WheelMain(timepickerview);
//				wheelMain.screenheight = screenInfo.getHeight();
//				String time = txttime.getText().toString();
//				Calendar calendar = Calendar.getInstance();
//				if(JudgeDate.isDate(time, "yyyy-MM-dd")){
//					try {
//						calendar.setTime(dateFormat.parse(time));
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				int year = calendar.get(Calendar.YEAR);
//				int month = calendar.get(Calendar.MONTH);
//				int day = calendar.get(Calendar.DAY_OF_MONTH);
//				wheelMain.initDateTimePicker(year,month,day);
//				new AlertDialog.Builder(MainActivity.this)
//				.setTitle("选择时间")
//				.setView(timepickerview)
//				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						txttime.setText(wheelMain.getTime());
//					}
//				})
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				})
//				.show();
//			}
//		});
    }
}