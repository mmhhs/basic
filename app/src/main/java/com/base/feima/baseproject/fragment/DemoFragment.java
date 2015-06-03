package com.base.feima.baseproject.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.base.BaseFragment;

import butterknife.ButterKnife;

public class DemoFragment extends BaseFragment{
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
            ButterKnife.inject(this, rootView);
            initView();
            initData();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

}