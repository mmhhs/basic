package com.base.feima.baseproject.view.multilayer;

import java.util.ArrayList;
import java.util.List;

public class SelectMenuModel{
    public int menuIndex;
    public int backgrountId;
    public int iconId;
    public String showText;
    public String showId;
    public List<SelectItemModel> selectItemModelList = new ArrayList<SelectItemModel>();
    public int multiCount;
    public List<Integer> listViewBackgroundIdList;

    public SelectMenuModel() {
    }

    public SelectMenuModel(int menuIndex, int backgrountId, int iconId, String showText, String showId, int multiCount, List<Integer> listViewBackgroundIdList) {
        this.menuIndex = menuIndex;
        this.backgrountId = backgrountId;
        this.iconId = iconId;
        this.showText = showText;
        this.showId = showId;
        this.multiCount = multiCount;
        this.listViewBackgroundIdList = listViewBackgroundIdList;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }

    public int getBackgrountId() {
        return backgrountId;
    }

    public void setBackgrountId(int backgrountId) {
        this.backgrountId = backgrountId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public List<SelectItemModel> getSelectItemModelList() {
        return selectItemModelList;
    }

    public void setSelectItemModelList(List<SelectItemModel> selectItemModelList) {
        this.selectItemModelList = selectItemModelList;
    }

    public int getMultiCount() {
        return multiCount;
    }

    public void setMultiCount(int multiCount) {
        this.multiCount = multiCount;
    }

    public List<Integer> getListViewBackgroundIdList() {
        return listViewBackgroundIdList;
    }

    public void setListViewBackgroundIdList(List<Integer> listViewBackgroundIdList) {
        this.listViewBackgroundIdList = listViewBackgroundIdList;
    }
}