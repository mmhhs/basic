package com.feima.baseproject.view.chooseimages;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnDeleteListener;
import com.feima.baseproject.listener.IOnDialogListener;
import com.feima.baseproject.listener.IOnItemClickListener;
import com.feima.baseproject.view.dialog.DialogUtil;

import java.util.ArrayList;


public class ImagesPreviewUtil{
    private Activity activity;
    private ArrayList<String> chooseImageList;
    private View containView;
    private int  imageIndex = 0;
    private boolean showPreviewTitle = true;
    private boolean showDelete = true;
    private ChooseImagesPreviewAdapter chooseImagesPreviewAdapter;
    private IOnDeleteListener iOnDeleteListener;
    private int statusBarHeight;

    public ImagesPreviewUtil(Activity activity, ArrayList<String> chooseImageList, View view) {
        this.activity = activity;
        this.chooseImageList = chooseImageList;
        this.containView = view;
    }

    public PopupWindow showImagesPreview(int index){
        PopupWindow popupWindow = getPreviewWindow(activity, index);
        popupWindow.showAtLocation(containView, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public PopupWindow getPreviewWindow(final Context context,int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_choose_images_pop_preview,null, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.base_choose_images_pop_preview_viewPager);
        final LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_layout);
        final LinearLayout footerLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_footer_layout);
        LinearLayout backLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_back);
        final RelativeLayout containLayout = (RelativeLayout) view.findViewById(R.id.base_choose_images_pop_preview_layout);
        final TextView doneText = (TextView) view.findViewById(R.id.base_choose_images_title_done);
        final LinearLayout deleteLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_delete);
        final TextView indexText = (TextView) view.findViewById(R.id.base_choose_images_title_index);
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
        if (showDelete){
            deleteLayout.setVisibility(View.VISIBLE);
        }else {
            deleteLayout.setVisibility(View.GONE);
        }
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    int[] xy = {1,1};
                    containLayout.getLocationOnScreen(xy);
                    if (xy[1]<statusBarHeight){
                        containLayout.setPadding(0,statusBarHeight-xy[1],0,0);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, 100);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showPreviewTitle = true;
            }
        });
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil dialogUtil = new DialogUtil(activity);
                dialogUtil.showTipDialog(containView,context.getString(R.string.choose_images_delete));
                dialogUtil.setiOnDialogListener(new IOnDialogListener() {
                    @Override
                    public void onConfirm() {
                        chooseImageList.remove(imageIndex);
                        chooseImagesPreviewAdapter.notifyDataSetChanged();
                        if (chooseImageList.size() == 0) {
                            popupWindow.dismiss();
                        } else {
                            if ((imageIndex) < chooseImageList.size()) {

                            } else {
                                imageIndex = imageIndex - 1;
                            }
                            indexText.setText("" + (imageIndex + 1) + "/" + chooseImageList.size());
                            viewPager.setCurrentItem(imageIndex);
                        }
                        if (iOnDeleteListener!=null){
                            iOnDeleteListener.onDelete(imageIndex);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onOther() {

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

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public void setiOnDeleteListener(IOnDeleteListener iOnDeleteListener) {
        this.iOnDeleteListener = iOnDeleteListener;
    }

    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }
}