package com.base.feima.baseproject.util.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.adapter.PopupwindowListAdapter;
import com.base.feima.baseproject.listener.IOnIgnoreListener;
import com.base.feima.baseproject.listener.IOnItemClickListener;
import com.base.feima.baseproject.listener.IOnSureListener;
import com.base.feima.baseproject.util.AnimUtil;
import com.base.feima.baseproject.util.OptionUtil;
import com.base.feima.baseproject.util.tool.StringUtil;
import com.base.feima.baseproject.view.PageIndicatorView;
import com.base.feima.baseproject.view.chooseimages.ChooseImagesPreviewAdapter;

import java.util.List;

public class PopupwindowUtil {
    private IOnItemClickListener iOnItemClickListener;
    private IOnSureListener iOnSureListener;
    private IOnIgnoreListener iOnIgnoreListener;
    private Activity activity;
    private float showAlpha = 0.5f;
    private float hideAlpha = 1f;

    public PopupwindowUtil(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setiOnItemClickListener(
            IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    private void doOnListItemClickListener(int position){
        if(iOnItemClickListener !=null){
            this.iOnItemClickListener.onItemClick(position);
        }
    }

    public void setiOnSureListener(
            IOnSureListener iOnSureListener) {
        this.iOnSureListener = iOnSureListener;
    }

    private void doOnSureClickListener(){
        if(iOnSureListener !=null){
            this.iOnSureListener.onSureClick();
        }
    }

    public void setiOnIgnoreListener(IOnIgnoreListener iOnIgnoreListener) {
        this.iOnIgnoreListener = iOnIgnoreListener;
    }

    /**
     * 选择界面-详细
     * @param context           上下文
     * @param view              父类视图
     * @param title             标题
     * @param itemArray         每项数组
     * @param dismissOutside    点击消失
     * @param dismissKeyback    返回消失
     * @param animStyle         动画样式
     */
    public void showListWindow(Context context,View view,String title,String[] itemArray,boolean dismissOutside,boolean dismissKeyback,int animStyle){
        PopupWindow pictureWindow = getListWindow(context,title,itemArray,dismissOutside,dismissKeyback,animStyle);
        pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 选择界面-简单
     * @param context           上下文
     * @param view              父类视图
     * @param title             标题
     * @param itemArray         每项数组
     */
    public void showListWindow(Context context,View view,String title,String[] itemArray){
        PopupWindow pictureWindow = getListWindow(context,title,itemArray,true,true,0);
        pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public PopupWindow getListWindow(Context context,String title,String[] itemArray,final boolean dismissOutside,boolean dismissKeyback,int animStyle) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_pop_list,null, false);
        ListView listView = (ListView) view.findViewById(R.id.base_pop_list_listview);
        TextView titleText = (TextView) view.findViewById(R.id.base_pop_list_title);
        LinearLayout containLayout = (LinearLayout) view.findViewById(R.id.base_pop_list_contain);
        PopupwindowListAdapter adapter = new PopupwindowListAdapter(context,itemArray);
        listView.setAdapter(adapter);
        if(!StringUtil.isEmpty(title)){
            titleText.setText(title);
        }
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        listView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                doOnListItemClickListener(arg2);
                popupWindow.dismiss();
            }

        });
        containLayout.setOnClickListener(new OnClickListener() {

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
     * 确定界面 - 详细
     * @param context                上下文
     * @param view                   父类视图
     * @param title                  标题
     * @param message                内容
     * @param dismissOutside         点击消失
     * @param dismissKeyback         返回消失
     * @param showOne                显示一个按钮
     * @param animStyle              动画样式
     */
    public void showSureWindow(Context context,View view,String title,String message,boolean dismissOutside,boolean dismissKeyback,boolean showOne,int animStyle){
        PopupWindow pictureWindow = getSureWindow(context,title,message,dismissOutside,dismissKeyback,showOne,animStyle);
        pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 确定界面 - 简单
     * @param context                上下文
     * @param view                   父类视图
     * @param title                  标题
     * @param message                内容
     */
    public void showSureWindow(Context context,View view,String title,String message){
        PopupWindow pictureWindow = getSureWindow(context,title,message,true,true,false,0);
        pictureWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public PopupWindow getSureWindow(Context context,String title,String message,final boolean dismissOutside,boolean dismissKeyback,boolean showOne,int animStyle) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_pop_sure,null, false);
        TextView titleText = (TextView) view.findViewById(R.id.base_pop_sure_title);
        TextView messageText = (TextView) view.findViewById(R.id.base_pop_sure_content);
        TextView cancleText = (TextView) view.findViewById(R.id.base_pop_sure_item1);
        TextView sureText = (TextView) view.findViewById(R.id.base_pop_sure_item2);
        View line = (View) view.findViewById(R.id.base_pop_sure_line);
        TextView ignoreText = (TextView) view.findViewById(R.id.base_pop_sure_item3);
        View line2 = (View) view.findViewById(R.id.base_pop_sure_line2);
        LinearLayout containLayout = (LinearLayout) view.findViewById(R.id.base_pop_sure_contain);
        if(!StringUtil.isEmpty(title)){
            titleText.setText(title);
        }
        if(!StringUtil.isEmpty(message)){
            messageText.setText(message);
        }
        if(showOne){
            cancleText.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        if (context.getString(R.string.tip5).equals(""+title)){
            ignoreText.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            messageText.setGravity(Gravity.CENTER_VERTICAL);
        }
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        sureText.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                doOnSureClickListener();
                popupWindow.dismiss();
            }

        });
        cancleText.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }

        });
        containLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dismissOutside) {
                    popupWindow.dismiss();
                }
            }

        });
        ignoreText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if (iOnIgnoreListener !=null){
                    iOnIgnoreListener.onIgnoreClick();
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
     * 遮挡界面-完整信息
     * @param context         上下文
     * @param view            父类视图
     * @param loadsString     加载文字
     * @param animStyle       PopupWindow动画
     * @param dismissOutside  点击消失
     * @param dismissKeyback  返回消失
     * @return
     */
    public PopupWindow showLoadWindow(Context context, View view, String loadsString, int animStyle,final boolean dismissOutside,final boolean dismissKeyback){
        PopupWindow loadPopupWindow = getLoadWindow(context, loadsString,  animStyle, dismissOutside, dismissKeyback);
        loadPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return loadPopupWindow;
    }

    /**
     * 遮挡界面-简单
     * @param context 上下文
     * @param view    父类视图
     * @return
     */
    public PopupWindow showLoadWindow(Context context,View view){
        PopupWindow loadPopupWindow = getLoadWindow(context, context.getString(R.string.task_item2), 0, true, true);
        loadPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return loadPopupWindow;
    }

    public PopupWindow getLoadWindow(Context context, String loadString,int animStyle,final boolean dismissOutside,final boolean dismissKeyback) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_pop_dialog,null, false);
        TextView loadText = (TextView) view.findViewById(R.id.base_pop_load_text);
        LinearLayout containLayout = (LinearLayout) view.findViewById(R.id.base_pop_dialog_contain);
        if(!loadString.isEmpty()){
            loadText.setText(loadString);
        }
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        if(animStyle>0){
            popupWindow.setAnimationStyle(animStyle);
        }
        if(dismissKeyback){
            popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        }
        containLayout.setOnClickListener(new OnClickListener() {

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
     * 下载界面
     * @param context       上下文
     * @param view          父类视图
     * @param title         标题
     * @param canCancel     终止
     * @return
     */
    public PopupWindow showDownloadWindow(Context context,View view,String title,boolean canCancel){
        PopupWindow popupWindow = getDownloadWindow(context, title, canCancel);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    ProgressBar progressBar;
    TextView contentText;
    public PopupWindow getDownloadWindow(Context context,String title,boolean canCancel) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_pop_download,null, false);
        TextView titleText = (TextView) view.findViewById(R.id.base_pop_download_title);
        progressBar = (ProgressBar) view.findViewById(R.id.base_pop_download_progressBar);
        contentText = (TextView) view.findViewById(R.id.base_pop_download_content);
        TextView cancleText = (TextView) view.findViewById(R.id.base_pop_download_cancel);
        LinearLayout containLayout = (LinearLayout) view.findViewById(R.id.base_pop_download_layout);
        LinearLayout cancelLayout = (LinearLayout) view.findViewById(R.id.base_pop_download_cancel_layout);
        titleText.setText(title);
        progressBar.setProgress(0);
        contentText.setText("");
        if (canCancel){
            cancelLayout.setVisibility(View.VISIBLE);
        }
        AnimUtil.setBackgroundAlpha(activity, showAlpha);
        final PopupWindow popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        cancleText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (iOnSureListener != null) {
                    iOnSureListener.onSureClick();
                }
                popupWindow.dismiss();
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
                contentText.setText(progress + "%");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 显示大图预览
     * @param activity
     * @param view
     * @param position 当前图片索引
     * @param chooseImageList 图片路径列表
     * @return
     */
    public PopupWindow showPreviewWindow(Activity activity,View view,int position,final List<String> chooseImageList){
        PopupWindow errorWindow = getPreviewWindow(activity,position,chooseImageList);
        errorWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return errorWindow;
    }

    public PopupWindow getPreviewWindow(final Activity activity,int position,final List<String> chooseImageList) {
        View view = LayoutInflater.from(activity).inflate(R.layout.base_choose_images_pop_preview,null, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.base_choose_images_pop_preview_viewPager);
        final LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_title_layout);
        final LinearLayout footerLayout = (LinearLayout) view.findViewById(R.id.base_choose_images_footer_layout);
        final PageIndicatorView pageIndicatorView  = (PageIndicatorView) view.findViewById(R.id.base_choose_images_pop_preview_pageIndicatorView);
        ChooseImagesPreviewAdapter chooseImagesPreviewAdapter = new ChooseImagesPreviewAdapter(activity,chooseImageList);
        viewPager.setAdapter(chooseImagesPreviewAdapter);
        titleLayout.setVisibility(View.GONE);
        footerLayout.setVisibility(View.GONE);
        pageIndicatorView.setVisibility(View.VISIBLE);
        if (chooseImageList.size()>position){
            pageIndicatorView.setTotalPage(chooseImageList.size());
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
        popupWindow.setBackgroundDrawable(new BitmapDrawable()); //使按返回键能够消失
        popupWindow.setAnimationStyle(R.style.base_anim_alpha);
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


}