package com.feima.baseproject.listener;


import com.feima.baseproject.view.multilayer.SelectMenuModel;

import java.util.List;

public interface ISelectedListener{
    void onSelected(List<SelectMenuModel> selectMenuModelList);
}