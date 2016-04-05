package com.feima.baseproject.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseFragment;
import com.feima.baseproject.view.chooseimages.ChooseImagesSampleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


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

    }

    @OnClick(R.id.buttonbbb)
    public void setChooseImagesIntent(){
        startActivity(new Intent(getActivity(), ChooseImagesSampleActivity.class));
    }

}