package com.feima.baseproject.view.chooseimages;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.feima.baseproject.R;
import com.feima.baseproject.base.BaseActivity;
import com.feima.baseproject.listener.IOnDeleteListener;
import com.feima.baseproject.listener.IOnItemClickListener;
import com.feima.baseproject.util.OptionUtil;
import com.feima.baseproject.view.dialog.DialogUtil;

import java.util.ArrayList;

import butterknife.InjectView;


public class ChooseImagesSampleActivity extends BaseActivity {
    @InjectView(R.id.base_choose_images_sample_gridView)
    public GridView gridView;
    private ChooseImagesBroadcastReciver reciver;
    private ArrayList<String> chooseImageList = new ArrayList<String>();
    private ChooseImagesSampleAdapter chooseImagesSampleAdapter;
    private int screenWidth = 0;
    private int maxSize = 9;
    private DialogUtil dialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTaskTag(getClass().getSimpleName());
        setContentView(R.layout.base_choose_images_sample);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.unregisterReceiver(reciver);
    }

    @Override
    public void init() {
        dialogUtil = new DialogUtil(this);
        screenWidth = OptionUtil.getScreenWidth(this);
        registerBroadcast();
        chooseImagesSampleAdapter = new ChooseImagesSampleAdapter(this,chooseImageList,screenWidth);
        chooseImagesSampleAdapter.setiOnItemClickListener(iOnItemClickListener);
        gridView.setAdapter(chooseImagesSampleAdapter);
    }

    private IOnItemClickListener iOnItemClickListener = new IOnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            try{
                if (position==chooseImageList.size()){
                    setChooseImagesIntent();
                }else {
                    if (chooseImageList.size()>0){
                        ImagesPreviewUtil imagesPreviewUtil = new ImagesPreviewUtil(ChooseImagesSampleActivity.this,chooseImageList,gridView);
                        PopupWindow popupWindow = imagesPreviewUtil.getPreviewWindow(ChooseImagesSampleActivity.this,position);
                        popupWindow.showAtLocation(gridView, Gravity.CENTER, 0, 0);
                        imagesPreviewUtil.setiOnDeleteListener(new IOnDeleteListener() {
                            @Override
                            public void onDelete(int position) {
                                new Handler().postDelayed(new Runnable(){
                                    public void run() {
                                        chooseImagesSampleAdapter.notifyDataSetChanged();
                                    }
                                }, 500);
                            }
                        });
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };


    public void setChooseImagesIntent(){
        if (chooseImageList.size()<maxSize){
            Intent intent = new Intent(this, ChooseImagesActivity.class);
            intent.putExtra(ChooseImagesActivity.BROADCASTFLAG,maxSize-chooseImageList.size());
            startActivity(intent);
        }else {
            OptionUtil.addToast(this, "" + getString(R.string.choose_images_max) + maxSize);
        }

    }

    private void registerBroadcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ChooseImagesActivity.BROADCASTFLAG);
        reciver = new ChooseImagesBroadcastReciver();
        this.registerReceiver(reciver, intentFilter);
    }

    private class ChooseImagesBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ChooseImagesActivity.BROADCASTFLAG)) {
                ArrayList<String> imageList = intent.getStringArrayListExtra(ChooseImagesActivity.BROADCASTFLAG);
                for(int i=0;i<imageList.size();i++) {
                    chooseImageList.add(imageList.get(i));
                }
                chooseImagesSampleAdapter.notifyDataSetChanged();
            }
        }
    }

}