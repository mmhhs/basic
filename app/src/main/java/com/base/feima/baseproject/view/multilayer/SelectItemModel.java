package com.base.feima.baseproject.view.multilayer;

import java.util.ArrayList;
import java.util.List;

public class SelectItemModel{
    public String itemId;
    public String itemName;
    public boolean isSelected=false;
    public String itemIcon;
    public int itemIconId=0;
    public int itemBackgrountId;

    public List<SelectItemModel> selectItemModelList = new ArrayList<SelectItemModel>();

    public SelectItemModel() {
    }

    public SelectItemModel(String itemId, String itemName, boolean isSelected, String itemIcon, int itemIconId, int itemBackgrountId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.isSelected = isSelected;
        this.itemIcon = itemIcon;
        this.itemIconId = itemIconId;
        this.itemBackgrountId = itemBackgrountId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public int getItemIconId() {
        return itemIconId;
    }

    public void setItemIconId(int itemIconId) {
        this.itemIconId = itemIconId;
    }

    public int getItemBackgrountId() {
        return itemBackgrountId;
    }

    public void setItemBackgrountId(int itemBackgrountId) {
        this.itemBackgrountId = itemBackgrountId;
    }

    public List<SelectItemModel> getSelectItemModelList() {
        return selectItemModelList;
    }

    public void setSelectItemModelList(List<SelectItemModel> selectItemModelList) {
        this.selectItemModelList = selectItemModelList;
    }
}