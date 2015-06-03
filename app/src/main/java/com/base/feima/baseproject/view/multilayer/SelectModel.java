package com.base.feima.baseproject.view.multilayer;

import java.util.ArrayList;
import java.util.List;

public class SelectModel{
    public List<SelectItemModel> selectItemModelList = new ArrayList<SelectItemModel>();
    public int listVIewBackgroundId;


    public SelectModel() {
    }

    public SelectModel(List<SelectItemModel> selectItemModelList, int listVIewBackgroundId) {
        this.selectItemModelList = selectItemModelList;
        this.listVIewBackgroundId = listVIewBackgroundId;

    }

    public List<SelectItemModel> getSelectItemModelList() {
        return selectItemModelList;
    }

    public void setSelectItemModelList(List<SelectItemModel> selectItemModelList) {
        this.selectItemModelList = selectItemModelList;
    }

    public int getListVIewBackgroundId() {
        return listVIewBackgroundId;
    }

    public void setListVIewBackgroundId(int listVIewBackgroundId) {
        this.listVIewBackgroundId = listVIewBackgroundId;
    }


}