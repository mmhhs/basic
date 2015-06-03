package com.base.feima.baseproject.view.chooseimages;

import java.io.Serializable;
import java.util.List;

/**
 * GridView的每个item的数据对象
 * 
 * @author len
 *
 */
public class ImageBean implements Serializable{
	/**
	 * 文件夹的第一张图片路径
	 */
	private String topImagePath;
	/**
	 * 文件夹名
	 */
	private String folderName; 
	/**
	 * 文件夹中的图片数
	 */
	private int imageCounts;
	
	private Boolean isSelected = false;

    private List<String> imagePathList;
	
	public String getTopImagePath() {
		return topImagePath;
	}
	public void setTopImagePath(String topImagePath) {
		this.topImagePath = topImagePath;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public int getImageCounts() {
		return imageCounts;
	}
	public void setImageCounts(int count) {
		this.imageCounts = count;
	}
	
	public Boolean getSelected() {
		return isSelected;
	}
	public void setSelected(Boolean selected) {
		this.isSelected = selected;
	}


    public List<String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(List<String> imagePathList) {
        this.imagePathList = imagePathList;
    }
}
