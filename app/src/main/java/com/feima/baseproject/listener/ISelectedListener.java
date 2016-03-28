package com.feima.baseproject.listener;


import com.feima.baseproject.view.multilayer.SelectMenuModel;

import java.util.List;

public interface ISelectedListener{
    public void onSelected(List<SelectMenuModel> selectMenuModelList);
}