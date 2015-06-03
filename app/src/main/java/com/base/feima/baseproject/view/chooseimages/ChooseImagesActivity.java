package com.base.feima.baseproject.view.chooseimages;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.base.BaseActivity;
import com.base.feima.baseproject.task.ShowDialogTask;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.tool.image.ImageChooseTools;
import com.base.feima.baseproject.util.BaseConstant;
import com.base.feima.baseproject.view.multilayer.IOnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class ChooseImagesActivity extends BaseActivity {
    public final int CHOOSEIMAGESSCREENFLAG_GRIDLIST         = 3001;//小缩略图列表
    public final int CHOOSEIMAGESSCREENFLAG_PREVIEWFOLDER    = 3002;//文件夹内所有图片大图预览
    public final int CHOOSEIMAGESSCREENFLAG_PREVIEWCHOOSE    = 3003;//选中图片大图预览
    public final int CHOOSEIMAGESSCREENFLAG_PREVIEWEDIT      = 3004;//编辑界面大图预览
    public final int CHOOSEIMAGESSCREENFLAG_PREVIEWTAKE      = 3005;//拍照结果大图预览

    public static final String BROADCASTFLAG = "CHOOSEIMAGES";
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private ArrayList<String> allImageList = new ArrayList<String>();//所有图片路径列表
    private ArrayList<String> chooseImageList = new ArrayList<String>();//选中图片路径列表
    private List<ImageBean> folderImageBeanList = new ArrayList<ImageBean>();

    @InjectView(R.id.base_choose_images_gridView)
    public GridView gridView;
    @InjectView(R.id.base_choose_images_title_done)
    public TextView doneText;
    @InjectView(R.id.base_choose_images_footer_folder)
    public TextView folderText;
    @InjectView(R.id.base_choose_images_footer_preview)
    public TextView previewText;
    @InjectView(R.id.base_choose_images_footer_layout)
    public LinearLayout footerLayout;

    private int screenWidth = 0;
    private ChooseImagesGridAdapter chooseImagesGridAdapter;
    private int maxSize = 9;//最多能选择的图片数
    private int folderShowIndex = 0;
    private ImageChooseTools imageChooseTools = new ImageChooseTools();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTaskTag(getClass().getSimpleName());
        setContentView(R.layout.base_choose_images);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        screenWidth = PublicTools.getScreenWidth(this);
        maxSize = getIntent().getExtras().getInt(BROADCASTFLAG,9);
        queryData();
    }



    public void queryData(){
        ShowDialogTask task = new ShowDialogTask(this,taskTag,gridView,getString(R.string.choose_images_query),true);
        task.setOnBackgroundListener(new ShowDialogTask.OnBackgroundListener() {
            @Override
            public BaseConstant.TaskResult onBackground(ShowDialogTask showDialogTask) {
                BaseConstant.TaskResult taskResult = BaseConstant.TaskResult.NOTHING;
                Boolean res = queryLocalImages();
                if(res){
                    taskResult = BaseConstant.TaskResult.OK;
                }else{
                    taskResult = BaseConstant.TaskResult.ERROR;
                }
                return taskResult;
            }
        });
        task.setOnOKListener(new ShowDialogTask.OnOKListener() {
            @Override
            public void onOK(ShowDialogTask showDialogTask) {
                folderImageBeanList = subGroupOfImage(mGruopMap);
                setFolderShow(0);
            }
        });
        task.execute();
    }

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap){
        if(mGruopMap.size() == 0){
            return null;
        }
        List<ImageBean> list = new ArrayList<ImageBean>();
        ImageBean mImageBeanAll = new ImageBean();
        String keyAll = getString(R.string.choose_images_all);
        List<String> valueAll = allImageList;
        mImageBeanAll.setSelected(true);
        mImageBeanAll.setFolderName(keyAll);
        mImageBeanAll.setImageCounts(valueAll.size());
        mImageBeanAll.setTopImagePath(valueAll.get(0));//获取该组的第一张图片
        mImageBeanAll.setImagePathList(valueAll);
        list.add(mImageBeanAll);
        mGruopMap.put(getString(R.string.choose_images_all), allImageList);
        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            mImageBean.setSelected(false);
            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片
            mImageBean.setImagePathList(value);
            if (!key.equals(keyAll)){
                list.add(mImageBean);
            }
        }
        return list;
    }

    private Boolean queryLocalImages(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            PublicTools.addToast(this, getString(R.string.choose_images_sd));
            return false;
        }
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = ChooseImagesActivity.this.getContentResolver();
        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED+ " desc");
		allImageList.add("takePhoto");
        while (mCursor.moveToNext()) {
            //获取图片的路径
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
            allImageList.add(path);
            //获取该图片的父路径名
            String parentName = new File(path).getParentFile().getName();
            //根据父路径名将图片放入到mGruopMap中
            if (!mGruopMap.containsKey(parentName)) {
                List<String> childList = new ArrayList<String>();
                childList.add(path);
                mGruopMap.put(parentName, childList);
            } else {
                mGruopMap.get(parentName).add(path);
            }
        }
//		mGruopMap.put("全部图片", allImageList);
        mCursor.close();
        return true;
    }

    @OnClick(R.id.base_choose_images_title_done)
    public void onDone(){
        sendChooseImagesBroad(chooseImageList);
        finish();
    }

    @OnClick(R.id.base_choose_images_footer_preview)
    public void onPreview(){
        if (chooseImageList.size()>0){
            List<String> preList = new ArrayList<String>();
            for (String pre:chooseImageList){
                preList.add(pre);
            }
            PopupWindow popupWindow = getPreviewWindow(ChooseImagesActivity.this,CHOOSEIMAGESSCREENFLAG_PREVIEWCHOOSE,preList,0);
            popupWindow.showAtLocation(gridView, Gravity.CENTER, 0, 0);
        }
    }

    @OnClick(R.id.base_choose_images_footer_folder)
    public void onFolder(){
        PopupWindow folderPopupWindow = getFolderWindow(ChooseImagesActivity.this,true,true,0);
//        folderPopupWindow.showAsDropDown(footerLayout);
        folderPopupWindow.showAtLocation(footerLayout, Gravity.BOTTOM, 0, 0);
    }


    private IOnCheckListener iOnCheckListener = new IOnCheckListener() {
        @Override
        public void onCheck(List<String> chooseList) {
            if (chooseList!=null&&chooseList.size()>0){
                doneText.setText(""+getString(R.string.choose_images_done)+"("+chooseList.size()+"/"+maxSize+")");
                previewText.setText(""+getString(R.string.choose_images_preview)+"("+chooseList.size()+")");
            }else {
                doneText.setText(""+getString(R.string.choose_images_done));
                previewText.setText(""+getString(R.string.choose_images_preview));
            }
        }
    };

    private IOnItemClickListener iOnItemClickListener = new IOnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            try{
                if (position==0&&folderShowIndex==0){
                    imageChooseTools.doTakePhoto(ChooseImagesActivity.this);
                }else {
                    if (folderImageBeanList.get(folderShowIndex).getImagePathList().size()>0){
                        PopupWindow popupWindow = getPreviewWindow(ChooseImagesActivity.this,CHOOSEIMAGESSCREENFLAG_PREVIEWFOLDER,folderImageBeanList.get(folderShowIndex).getImagePathList(),position);
                        popupWindow.showAtLocation(gridView, Gravity.CENTER, 0, 0);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    /**
     * 发送结果广播
     * @param imageList 选中的图片列表
     */
    private void sendChooseImagesBroad(ArrayList<String> imageList){
        Intent intent = new Intent();
        intent.setAction(BROADCASTFLAG);
        intent.putStringArrayListExtra(BROADCASTFLAG, imageList);
        ChooseImagesActivity.this.sendBroadcast(intent);
    }

    public PopupWindow getFolderWindow(Context context,final boolean dismissOutside,boolean dismissKeyback,int animStyle) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_choose_images_pop_folder,null, false);
        ListView listView = (ListView) view.findViewById(R.id.base_choose_images_pop_folder_listView);
        LinearLayout popupwindowLinear = (LinearLayout) view.findViewById(R.id.base_choose_images_pop_folder_layout);
        ChooseImagesFolderAdapter adapter = new ChooseImagesFolderAdapter(context,folderImageBeanList);
        listView.setAdapter(adapter);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                setFolderShow(arg2);
                popupWindow.dismiss();
            }

        });
        popupwindowLinear.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(dismissOutside){
                    popupWindow.dismiss();
                }
            }

        });

        return popupWindow;
    }

    /**
     * 切换显示文件夹图片
     * @param position
     */
    private void setFolderShow(int position){
        folderShowIndex = position;
        for (ImageBean imageBean:folderImageBeanList){
            imageBean.setSelected(false);
        }
        folderImageBeanList.get(position).setSelected(true);
        folderText.setText(folderImageBeanList.get(position).getFolderName());
        chooseImagesGridAdapter = new ChooseImagesGridAdapter(ChooseImagesActivity.this,folderImageBeanList.get(position).getImagePathList(),chooseImageList,screenWidth,maxSize,folderShowIndex);
        chooseImagesGridAdapter.setiOnCheckListener(iOnCheckListener);
        chooseImagesGridAdapter.setiOnItemClickListener(iOnItemClickListener);
        gridView.setAdapter(chooseImagesGridAdapter);
        chooseImagesGridAdapter.notifyDataSetChanged();
    }

    String previewPath = "";
    boolean showPreviewTitle = true;
    ChooseImagesPreviewAdapter chooseImagesPreviewAdapter;

    public PopupWindow getPreviewWindow(Context context,final int screenTag, final List<String> previewList,int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_choose_images_pop_preview,null, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.base_choose_images_pop_preview_viewPager);
        final LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_layout);
        final LinearLayout footerLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_footer_layout);
        LinearLayout backLayout = (LinearLayout) view.findViewById(R.id.base_title_back);
        RelativeLayout containLayout = (RelativeLayout) view.findViewById(R.id.base_choose_images_pop_preview_layout);
        final TextView doneText = (TextView) view.findViewById(R.id.base_choose_images_title_done);
        TextView previewText = (TextView) view.findViewById(R.id.base_choose_images_footer_preview);
        TextView folderText = (TextView) view.findViewById(R.id.base_choose_images_footer_folder);
        final TextView indexText = (TextView) view.findViewById(R.id.base_choose_images_title_index);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.base_choose_images_footer_choose);
        containLayout.setPadding(0,PublicTools.getStatusBarHeight(ChooseImagesActivity.this),0,0);
        chooseImagesPreviewAdapter = new ChooseImagesPreviewAdapter(context,previewList);
        chooseImagesPreviewAdapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (showPreviewTitle){
                    titleLayout.setVisibility(View.GONE);
                    footerLayout.setVisibility(View.GONE);
                    showPreviewTitle = false;
                }else {
                    titleLayout.setVisibility(View.VISIBLE);
                    footerLayout.setVisibility(View.VISIBLE);
                    showPreviewTitle = true;
                }
            }
        });
        viewPager.setAdapter(chooseImagesPreviewAdapter);
        if (screenTag!=CHOOSEIMAGESSCREENFLAG_PREVIEWTAKE){
            setPreviewDoneText(doneText);
        }
        switch (screenTag){
            case CHOOSEIMAGESSCREENFLAG_PREVIEWFOLDER:
                previewText.setVisibility(View.GONE);
                folderText.setVisibility(View.GONE);
                checkBox.setVisibility(View.VISIBLE);
                break;
            case CHOOSEIMAGESSCREENFLAG_PREVIEWCHOOSE:
                previewText.setVisibility(View.GONE);
                folderText.setVisibility(View.GONE);
                checkBox.setVisibility(View.VISIBLE);
                break;
            case CHOOSEIMAGESSCREENFLAG_PREVIEWTAKE:
                previewText.setVisibility(View.GONE);
                folderText.setVisibility(View.GONE);
                footerLayout.setVisibility(View.INVISIBLE);
                break;
        }

        if (previewList.size()>position){
            indexText.setText(""+(position+1)+"/"+previewList.size());
            previewPath = previewList.get(position);
            if (isSelected(previewList.get(position))){
                checkBox.setChecked(true);
            }else {
                checkBox.setChecked(false);
            }
            viewPager.setCurrentItem(position);
        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indexText.setText(""+(position+1)+"/"+previewList.size());
                previewPath = previewList.get(position);
                if (isSelected(previewList.get(position))){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected(previewPath)){
                    setSelected(previewPath,false,doneText);
                }else {
                    setSelected(previewPath,true,doneText);
                }
            }
        });
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        if(true){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
//        if(animStyle>0){
//            popupWindow.setAnimationStyle(animStyle);
//        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (chooseImagesGridAdapter!=null){
                    chooseImagesGridAdapter.notifyDataSetChanged();
                }
                showPreviewTitle = true;
            }
        });
        doneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    popupWindow.dismiss();
                    if (screenTag!=CHOOSEIMAGESSCREENFLAG_PREVIEWTAKE){
                        sendChooseImagesBroad(chooseImageList);
                    }else {
                        ArrayList<String> preList = new ArrayList<String>();
                        preList.add(previewList.get(0));
                        sendChooseImagesBroad(preList);
                    }
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    private boolean isSelected(String path){
        boolean result = false;
        for(String imagePath:chooseImageList){
            if (path.equals(imagePath)){
                result = true;
            }
        }
        return result;
    }

    private void setSelected(String path,boolean isChecked,TextView doneText){
        if (isChecked){
            if (!isSelected(path)){
                if (chooseImageList.size()<maxSize){
                    chooseImageList.add(path);
                }else {
                    PublicTools.addToast(this,""+getString(R.string.choose_images_max)+maxSize);
                }
            }
        }else {
            if (isSelected(path)){
                chooseImageList.remove(path);
            }
        }
        setPreviewDoneText(doneText);
        if (iOnCheckListener!=null){
            iOnCheckListener.onCheck(chooseImageList);
        }
    }

    private void setPreviewDoneText(TextView doneText){
        if (chooseImageList!=null&&chooseImageList.size()>0){
            doneText.setText(""+getString(R.string.choose_images_done)+"("+chooseImageList.size()+"/"+maxSize+")");
            doneText.setEnabled(true);
        }else {
            doneText.setText(""+getString(R.string.choose_images_done));
            doneText.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageChooseTools.PHOTO_WITH_CAMERA:
                    String path = imageChooseTools.getTakePhotoScaleUrl();//获取拍照压缩路径
                    List<String> pathList = new ArrayList<String>();
                    pathList.add(path);
                    PopupWindow popupWindow = getPreviewWindow(ChooseImagesActivity.this,CHOOSEIMAGESSCREENFLAG_PREVIEWTAKE,pathList,0);
                    popupWindow.showAtLocation(gridView, Gravity.CENTER, 0, 0);
                    break;
                case ImageChooseTools.CHOOSE_PICTURE:
                    break;
            }

        }
    }

}