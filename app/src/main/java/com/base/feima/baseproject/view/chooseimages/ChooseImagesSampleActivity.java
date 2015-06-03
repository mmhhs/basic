package com.base.feima.baseproject.view.chooseimages;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.base.BaseActivity;
import com.base.feima.baseproject.tool.PublicTools;
import com.base.feima.baseproject.tool.popupwindow.PopupwindowTool;
import com.base.feima.baseproject.view.multilayer.IOnItemClickListener;

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
    private PopupwindowTool popupwindowTool = new PopupwindowTool();

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
    public void initView() {

    }

    @Override
    public void initData() {
        screenWidth = PublicTools.getScreenWidth(this);
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
                        PopupWindow popupWindow = getPreviewWindow(ChooseImagesSampleActivity.this,position);
                        popupWindow.showAtLocation(gridView, Gravity.CENTER, 0, 0);
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
            PublicTools.addToast(this,""+getString(R.string.choose_images_max)+maxSize);
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

    int  imageIndex = 0;
    boolean showPreviewTitle = true;
    ChooseImagesPreviewAdapter chooseImagesPreviewAdapter;

    public PopupWindow getPreviewWindow(final Context context,int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_choose_images_pop_preview,null, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.base_choose_images_pop_preview_viewPager);
        final LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_layout);
        final LinearLayout footerLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_footer_layout);
        LinearLayout backLayout = (LinearLayout) view.findViewById(R.id.base_title_back);
        RelativeLayout containLayout = (RelativeLayout) view.findViewById(R.id.base_choose_images_pop_preview_layout);
        final TextView doneText = (TextView) view.findViewById(R.id.base_choose_images_title_done);
        final LinearLayout deleteLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_delete);
        final TextView indexText = (TextView) view.findViewById(R.id.base_choose_images_title_index);
        containLayout.setPadding(0,PublicTools.getStatusBarHeight(ChooseImagesSampleActivity.this),0,0);
        chooseImagesPreviewAdapter = new ChooseImagesPreviewAdapter(context,chooseImageList);
        chooseImagesPreviewAdapter.setiOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (showPreviewTitle){
                    titleLayout.setVisibility(View.GONE);
                    footerLayout.setVisibility(View.GONE);
                    showPreviewTitle = false;
                }else {
                    titleLayout.setVisibility(View.VISIBLE);
                    footerLayout.setVisibility(View.GONE);
                    showPreviewTitle = true;
                }
            }
        });
        viewPager.setAdapter(chooseImagesPreviewAdapter);
        deleteLayout.setVisibility(View.VISIBLE);
        footerLayout.setVisibility(View.GONE);
        doneText.setVisibility(View.GONE);
        if (chooseImageList.size()>position){
            indexText.setText(""+(position+1)+"/"+chooseImageList.size());
            imageIndex = position;
            viewPager.setCurrentItem(position);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indexText.setText(""+(position+1)+"/"+chooseImageList.size());
                imageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                showPreviewTitle = true;
            }
        });
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowTool.showSureWindow(context,gridView,"",context.getString(R.string.choose_images_delete));
                popupwindowTool.setOnSureClickListener(new PopupwindowTool.OnSureClickListener() {
                    @Override
                    public void onClick(int position) {
                        chooseImageList.remove(imageIndex);
                        chooseImagesSampleAdapter.notifyDataSetChanged();
                        chooseImagesPreviewAdapter.notifyDataSetChanged();
                        if (chooseImageList.size()==0){
                            popupWindow.dismiss();
                        }else {
                            if ((imageIndex)<chooseImageList.size()){

                            }else {
                                imageIndex = imageIndex-1;
                            }
                            indexText.setText(""+(imageIndex+1)+"/"+chooseImageList.size());
                            viewPager.setCurrentItem(imageIndex);
                        }

                    }
                });
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
}