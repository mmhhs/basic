package com.feima.baseproject.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseFragment;
import com.feima.baseproject.view.chooseimages.ChooseImagesSampleActivity;
import com.feima.baseproject.view.pickerview.popwindow.DatePickerPopWin;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


public class DemoFragment extends BaseFragment {
    public View rootView;
    public DemoFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setTaskTag(getClass().getSimpleName());
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.sample,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            rootView.setLayoutParams(params);
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
    public void onResume(){
        super.onResume();
        try {
            ButterKnife.inject(this, rootView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

        //延迟加载
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible){
            return;
        }
        //TODO 判断是否加载过数据

        //TODO 网络加载

    }

    @Optional
    @OnClick(R.id.buttonbbb)
    public void setChooseImagesIntent(){
        startActivity(new Intent(getActivity(), ChooseImagesSampleActivity.class));
    }

    void chooseDate(){
        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = time.month+1;
        int day = time.monthDay;
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(getActivity(),onDatePickedListener)
                .textConfirm(getString(R.string.confirm)) //text of confirm button
                .textCancel(getString(R.string.cancel)) //text of cancel button
                .btnTextSize(14) // button text size
                .viewTextSize(20) // pick view text size
                .colorCancel(getResources().getColor(R.color.text_grey_light)) //color of cancel button
                .colorConfirm(getResources().getColor(R.color.text_red))//color of confirm button
                .minYear(1949) //min year in loop
                .maxYear(year+1) // max year in loop
                .dateChose(""+year+"-"+month+"-"+day) // date chose when init popwindow
                .showHour(false)
                .build();
        pickerPopWin.showPopWin(getActivity());
    }

    DatePickerPopWin.OnDatePickedListener onDatePickedListener = new DatePickerPopWin.OnDatePickedListener() {
        @Override
        public void onDatePickCompleted(int year, int month, int day, String dateDesc) {

        }

        @Override
        public void onDatePickCompleted(int year, int month, int day, String hour, String dateDesc) {


        }
    };

}