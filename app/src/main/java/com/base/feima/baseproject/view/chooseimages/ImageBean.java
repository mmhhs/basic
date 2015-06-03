package com.base.feima.baseproject.view.chooseimages;

import java.io.Serializable;
import java.util.List;

/**
 * GridView��ÿ��item�����ݶ���
 * 
 * @author len
 *
 */
public class ImageBean implements Serializable{
	/**
	 * �ļ��еĵ�һ��ͼƬ·��
	 */
	private String topImagePath;
	/**
	 * �ļ�����
	 */
	private String folderName; 
	/**
	 * �ļ����е�ͼƬ��
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
