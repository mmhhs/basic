package com.base.feima.baseproject.view.dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.adapter.PopupwindowListAdapter;
import com.base.feima.baseproject.listener.IOnDialogListener;
import com.base.feima.baseproject.listener.IOnItemClickListener;
import com.base.feima.baseproject.util.AnimUtil;
import com.base.feima.baseproject.util.OptionUtil;
import com.base.feima.baseproject.util.tool.StringUtil;
import com.base.feima.baseproject.view.PageIndicatorView;
import com.base.feima.baseproject.view.chooseimages.ChooseImagesPreviewAdapter;

import java.util.List;

public class DialogUtil{
    private Activity activity;
    private float showAlpha = 0.5f;//显示对话框时界面半透明度
    private float hideAlpha = 1f;//对话框关闭后界面恢复

    private boolean dismissOutside = false;//点击界面关闭
    private boolean dismissKeyback = false;//点击返回键关闭
    private int animStyle = 0;//动画资源ID
    private String title = "";//对话框标题

    //提示对话框
    private int optionCount = 2;//提示对话框操作数量
    private String confirmStr = "";//提示对话框确定按钮名称
    private String cancelStr = "";//提示对话框取消按钮名称
    private String otherStr = "";//提示对话框其他按钮名称

    //下载框
    private ProgressBar progressBar;//进度条
    private TextView contentText;//进度

    private IOnItemClickListener iOnItemClickListener;
    private IOnDialogListener iOnDialogListener;

    public DialogUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示列表对话框
     * @param view 父视图
     * @param itemArray 数据
     */
    public PopupWindow showListDialog(View view, String[] itemArray){
        PopupWindow popupWindow = getListDialog(itemArray);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public PopupWindow getListDialog(String[] itemArray){
        View view = LayoutInflater.from(activity).inflate(R.layout.base_view_dialog_list,null, false);
        ListView listView = (ListView) view.findViewById(R.id.base_view_dialog_list_listview);
        LinearLayout containerLayout = (LinearLayout) view.findViewById(R.id.base_view_dialog_list_container);
        PopupwindowListAdapter adapter = new PopupwindowListAdapter(activity,itemArray);
        listView.setAdapter(adapter);
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
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
                if (iOnItemClickListener!=null){
                    iOnItemClickListener.onItemClick(arg2);
                }
                popupWindow.dismiss();
            }

        });
        containerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dismissOutside) {
                    popupWindow.dismiss();
                }
            }

        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AnimUtil.setBackgroundAlpha(activity, hideAlpha);
            }
        });
        return popupWindow;
    }

    /**
     * 显示提示对话框
     * @param view 父视图
     * @param message 提示内容
     */
    public PopupWindow showTipDialog(View view, String message){
        PopupWindow popupWindow = getTipDialog(message);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public PopupWindow getTipDialog(String message) {
        View view = LayoutInflater.from(activity).inflate(R.layout.base_view_dialog_tip,null, false);
        TextView titleText = (TextView) view.findViewById(R.id.base_view_dialog_tip_title);
        TextView messageText = (TextView) view.findViewById(R.id.base_view_dialog_tip_content);
        TextView confirmText = (TextView) view.findViewById(R.id.base_view_dialog_tip_confirm);
        TextView cancelText = (TextView) view.findViewById(R.id.base_view_dialog_tip_cancel);
        TextView otherText = (TextView) view.findViewById(R.id.base_view_dialog_tip_other);
        LinearLayout containerLayout = (LinearLayout) view.findViewById(R.id.base_view_dialog_tip_container);
        if(!StringUtil.isEmpty(title)){
            titleText.setText(title);
            titleText.setVisibility(View.VISIBLE);
        }else {
            titleText.setVisibility(View.GONE);
        }
        if(!StringUtil.isEmpty(message)){
            messageText.setText(message);
        }
        if (!StringUtil.isEmpty(confirmStr)){
            confirmText.setText(confirmStr);
        }
        if (!StringUtil.isEmpty(cancelStr)){
            cancelText.setText(cancelStr);
        }
        if (!StringUtil.isEmpty(otherStr)){
            otherText.setText(otherStr);
        }
        switch (optionCount){
            case 0:
                confirmText.setVisibility(View.GONE);
                cancelText.setVisibility(View.GONE);
                otherText.setVisibility(View.GONE);
                break;
            case 1:
                confirmText.setVisibility(View.VISIBLE);
                cancelText.setVisibility(View.GONE);
                otherText.setVisibility(View.GONE);
                break;
            case 2:
                confirmText.setVisibility(View.VISIBLE);
                cancelText.setVisibility(View.VISIBLE);
                otherText.setVisibility(View.GONE);
                break;
            case 3:
                confirmText.setVisibility(View.VISIBLE);
                cancelText.setVisibility(View.VISIBLE);
                otherText.setVisibility(View.VISIBLE);
                break;
        }
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        confirmText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (iOnDialogListener!=null){
                    iOnDialogListener.onConfirm();
                }
                popupWindow.dismiss();
            }

        });
        cancelText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (iOnDialogListener!=null){
                    iOnDialogListener.onCancel();
                }
                popupWindow.dismiss();
            }

        });
        otherText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (iOnDialogListener!=null){
                    iOnDialogListener.onOther();
                }
                popupWindow.dismiss();
            }

        });
        containerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dismissOutside) {
                    popupWindow.dismiss();
                }
            }

        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AnimUtil.setBackgroundAlpha(activity, hideAlpha);
            }
        });
        return popupWindow;
    }


    /**
     * 显示遮挡界面
     * @param view 父视图
     * @param loadStr 加载文字
     * @return
     */
    public PopupWindow showLoadDialog(View view,String loadStr){
        PopupWindow popupWindow = getLoadDialog(loadStr);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public PopupWindow getLoadDialog(String loadStr) {
        View view = LayoutInflater.from(activity).inflate(R.layout.base_view_dialog_load,null, false);
        TextView loadText = (TextView) view.findViewById(R.id.base_view_link_load_item_text);
        LinearLayout containerLayout = (LinearLayout) view.findViewById(R.id.base_view_dialog_load_container);
        if(!StringUtil.isEmpty(loadStr)){
            loadText.setText(loadStr);
        }
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
        containerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dismissOutside) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AnimUtil.setBackgroundAlpha(activity, hideAlpha);
            }
        });
        return popupWindow;
    }


    /**
     * 下载框
     * @param view 父视图
     * @param canCancel 是否可以取消
     * @return
     */
    public PopupWindow showDownloadDialog(View view,boolean canCancel){
        PopupWindow popupWindow = getDownloadDialog(canCancel);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public PopupWindow getDownloadDialog(boolean canCancel) {
        View view = LayoutInflater.from(activity).inflate(R.layout.base_view_dialog_download,null, false);
        TextView titleText = (TextView) view.findViewById(R.id.base_view_dialog_download_title);
        progressBar = (ProgressBar) view.findViewById(R.id.base_view_dialog_download_progressBar);
        contentText = (TextView) view.findViewById(R.id.base_view_dialog_download_content);
        TextView cancelText = (TextView) view.findViewById(R.id.base_view_dialog_download_cancel);
        LinearLayout containerLayout = (LinearLayout) view.findViewById(R.id.base_view_dialog_download_container);
        LinearLayout cancelLayout = (LinearLayout) view.findViewById(R.id.base_view_dialog_download_cancel_layout);
        if (!StringUtil.isEmpty(title)){
            titleText.setText(title);
        }
        progressBar.setProgress(0);
        contentText.setText("0 %");
        if (canCancel){
            cancelLayout.setVisibility(View.VISIBLE);
        }else {
            cancelLayout.setVisibility(View.GONE);
        }
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
        cancelText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (iOnDialogListener != null) {
                    iOnDialogListener.onCancel();
                }
                popupWindow.dismiss();
            }

        });
        containerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dismissOutside) {
                    popupWindow.dismiss();
                }
            }

        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AnimUtil.setBackgroundAlpha(activity, hideAlpha);
            }
        });

        return popupWindow;
    }

    public void updateProgressInfo(String transferedBytes,long totalBytes){
        try {
            if (progressBar!=null&&contentText!=null){
                long tran = Long.parseLong(transferedBytes);
                int progress = (int)(100*tran/totalBytes);
                progressBar.setProgress(progress);
                contentText.setText(progress + " %");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 显示预览大图
     * @param view 父视图
     * @param imageList 图片路径
     * @param position 位置
     * @return
     */
    public PopupWindow showPreviewDialog(View view,List<String> imageList, int position){
        PopupWindow popupWindow = getPreviewDialog(imageList, position);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public PopupWindow getPreviewDialog(final List<String> imageList, int position) {
        View view = LayoutInflater.from(activity).inflate(R.layout.base_choose_images_pop_preview,null, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.base_choose_images_pop_preview_viewPager);
        final LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_layout);
        final LinearLayout footerLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_footer_layout);
        final PageIndicatorView pageIndicatorView  = (PageIndicatorView) view.findViewById(R.id.base_choose_images_pop_preview_pageIndicatorView);
        ChooseImagesPreviewAdapter chooseImagesPreviewAdapter = new ChooseImagesPreviewAdapter(activity,imageList);
        viewPager.setAdapter(chooseImagesPreviewAdapter);
        titleLayout.setVisibility(View.GONE);
        footerLayout.setVisibility(View.GONE);
        pageIndicatorView.setVisibility(View.VISIBLE);
        if (imageList.size()>position){
            pageIndicatorView.setTotalPage(imageList.size());
            viewPager.setCurrentItem(position);
        }
        OptionUtil.startFullScreen(activity);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
//        popupWindow.setAnimationStyle(R.style.base_anim_alpha);
        chooseImagesPreviewAdapter.setiOnItemClickListener(new IOnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                OptionUtil.quitFullScreen(activity);
                new Handler().postDelayed(new Runnable()
                {
                    public void run()
                    {
                        popupWindow.dismiss();
                    }
                }, 500);

            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AnimUtil.setBackgroundAlpha(activity, hideAlpha);
            }
        });
        return popupWindow;
    }

    public void setDismissKeyback(boolean dismissKeyback) {
        this.dismissKeyback = dismissKeyback;
    }

    public void setDismissOutside(boolean dismissOutside) {
        this.dismissOutside = dismissOutside;
    }

    public void setAnimStyle(int animStyle) {
        this.animStyle = animStyle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOptionCount(int optionCount) {
        this.optionCount = optionCount;
    }

    public void setConfirmStr(String confirmStr) {
        this.confirmStr = confirmStr;
    }

    public void setCancelStr(String cancelStr) {
        this.cancelStr = cancelStr;
    }

    public void setOtherStr(String otherStr) {
        this.otherStr = otherStr;
    }

    public void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    public void setiOnDialogListener(IOnDialogListener iOnDialogListener) {
        this.iOnDialogListener = iOnDialogListener;
    }
}