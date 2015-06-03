package com.base.feima.baseproject.view.multilayer;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.base.feima.baseproject.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: document your custom view class.
 */
public class MultilayerChooseView extends LinearLayout {
    private final String TAGS = "MultilayerChooseView";
    private List<SelectMenuModel> selectMenuModelList = new ArrayList<SelectMenuModel>();
    private LayoutParams defaultTabLayoutParams;
    private List<TextView> menuTextViewList = new ArrayList<TextView>();
    private Map<String,PopupWindow> menuPopupWindowMap = new HashMap<String,PopupWindow>();
    private boolean showPopupWindow = false;//判断当前是否有PopupWindow显示
    private ISelectedListener iSelectedListener;//选择结果监听

    public MultilayerChooseView(Context context) {
        super(context);

    }

    public MultilayerChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MultilayerChooseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * 初始
     * @param selectMenuModelList
     */
    public void init(List<SelectMenuModel> selectMenuModelList) {
        this.selectMenuModelList = selectMenuModelList;
        defaultTabLayoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        defaultTabLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        defaultTabLayoutParams.weight = 1;

        addMenuItem();
    }

    /**
     * 添加菜单项
     */
    private void addMenuItem(){
        if (selectMenuModelList!=null){
            for (int i=0;i<selectMenuModelList.size();i++){
                SelectMenuModel menuModel = selectMenuModelList.get(i);
                View view = LinearLayout.inflate(getContext(), R.layout.base_adapter_select_item,null);
                TextView menuText = (TextView)view.findViewById(R.id.base_adapter_select_item_text);
                ImageView menuIcon = (ImageView)view.findViewById(R.id.base_adapter_select_item_icon);
                final LinearLayout menuLayout = (LinearLayout)view.findViewById(R.id.base_adapter_select_item_layout);
                menuTextViewList.add(menuText);
                menuText.setText(menuModel.getShowText());
                if (menuModel.getIconId()>0){
                    menuIcon.setBackgroundResource(menuModel.getIconId());
                    menuIcon.setVisibility(View.VISIBLE);
                }else {
                    menuIcon.setVisibility(View.GONE);
                }
                if (menuModel.getBackgrountId()>0){
                    menuLayout.setBackgroundResource(menuModel.getBackgrountId());
                }
                final int p = i;
                menuLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showChooseWindow(menuLayout,p);
                    }
                });
                view.setLayoutParams(defaultTabLayoutParams);
                addView(view);
            }
        }

    }

    /**
     * 显示PopupWindow
     * @param view
     * @param index
     */
    public void showChooseWindow(View view,int index){
        PopupWindow popupWindow ;
        if (menuPopupWindowMap.get(""+index)!=null){
            popupWindow = menuPopupWindowMap.get(""+index);
        }else {
            popupWindow = getChooseWindow(getContext(),index);
        }
        if (popupWindow.isShowing()){
            hideAllChooseWindow();
            showPopupWindow = false;
        }else {
            hideAllChooseWindow();
            popupWindow.showAsDropDown(view,0,0);
            showPopupWindow = true;
        }

    }

    /**
     * 隐藏所有PopupWindow
     */
    public void hideAllChooseWindow(){
        for (String key : menuPopupWindowMap.keySet()) {
            if (menuPopupWindowMap.get(key).isShowing()){
                menuPopupWindowMap.get(key).dismiss();
            }
        }
    }

    /**
     * 获得PopupWindow
     * @param context
     * @param index
     * @return
     */
    public PopupWindow getChooseWindow(final Context context,final int index) {
        final List<ListView> listViewList = new ArrayList<ListView>();
        final Map<String,List<SelectItemModel>> selectItemModelMap = new HashMap<String,List<SelectItemModel>>();
        final Map<String,SelectItemAdapter> selectItemAdapterMap = new HashMap<String,SelectItemAdapter>();
        LayoutParams popLayoutParams = new LayoutParams(
                0, LayoutParams.MATCH_PARENT);
        popLayoutParams.weight = 1;
        popLayoutParams.bottomMargin = 120;
        LinearLayout containerLayout = new LinearLayout(context);
        final PopupWindow popupWindow = new PopupWindow(containerLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        containerLayout.setOrientation(HORIZONTAL);
        containerLayout.setBackgroundResource(R.color.white);
        if (index<selectMenuModelList.size()&&selectMenuModelList.get(index)!=null){
            final SelectMenuModel selectMenuModel = selectMenuModelList.get(index);
            for (int i=0;i<selectMenuModel.getMultiCount();i++){
                ListView listView = new ListView(context);
                listViewList.add(listView);
                listView.setLayoutParams(popLayoutParams);
                listView.setBackgroundResource(selectMenuModel.getListViewBackgroundIdList().get(i));
                listView.setDivider(context.getResources().getDrawable(R.color.grey));
                listView.setDividerHeight(1);
                final int p = i;
                if (i==0){
                    selectItemModelMap.put("" + i, selectMenuModel.getSelectItemModelList());
                    SelectItemAdapter selectItemAdapter = new SelectItemAdapter(context,selectItemModelMap.get(""+i));
                    listView.setAdapter(selectItemAdapter);
                    selectItemAdapterMap.put("" +i,selectItemAdapter);
                    selectItemAdapterMap.get(""+i).setiOnItemClickListener(new IOnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            LogUtil.d("" + p);
                            if (p==(listViewList.size()-1)){
                                SelectItemModel selectItemModel = selectItemModelMap.get(""+p).get(position);
                                if (p==0){
                                    setItemSelected(selectItemModelMap.get(""+p),position);
                                    selectItemAdapterMap.get(""+p).notifyDataSetChanged();
                                }
                                selectMenuModelList.get(index).setShowId(selectItemModel.getItemId());
                                selectMenuModelList.get(index).setShowText(selectItemModel.getItemName());
                                setMenuText();
                                popupWindow.dismiss();
                                if (iSelectedListener!=null){
                                    iSelectedListener.onSelected(selectMenuModelList);
                                }
                            }else {
                                selectItemModelMap.put(""+(p+1),selectItemModelMap.get(""+p).get(position).getSelectItemModelList());
                                SelectItemAdapter selectItemAdapter = new SelectItemAdapter(context,selectItemModelMap.get(""+(p+1)));
                                listViewList.get(p+1).setAdapter(selectItemAdapter);
                                selectItemAdapterMap.put(""+(p+1), selectItemAdapter);
                                if (p==0){
                                    setItemSelected(selectItemModelMap.get(""+p), position);
                                    selectItemAdapterMap.get(""+p).notifyDataSetChanged();
                                }


                                final int d = p + 1;
                                selectItemAdapter.setiOnItemClickListener(new IOnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        if (d==(listViewList.size()-1)){
                                            SelectItemModel selectItemModel = selectItemModelMap.get(""+d).get(position);
                                            if (d==0){
                                                setItemSelected(selectItemModelMap.get(""+d),position);
                                                selectItemAdapterMap.get(""+d).notifyDataSetChanged();
                                            }
                                            selectMenuModelList.get(index).setShowId(selectItemModel.getItemId());
                                            selectMenuModelList.get(index).setShowText(selectItemModel.getItemName());
                                            setMenuText();
                                            popupWindow.dismiss();
                                            if (iSelectedListener!=null){
                                                iSelectedListener.onSelected(selectMenuModelList);
                                            }
                                        }else {
                                            selectItemModelMap.put(""+(d+1),selectItemModelMap.get(""+d).get(position).getSelectItemModelList());
                                            SelectItemAdapter selectItemAdapter = new SelectItemAdapter(context,selectItemModelMap.get(""+(d+1)));
                                            listViewList.get(d+1).setAdapter(selectItemAdapter);
                                            selectItemAdapterMap.put(""+(d+1), selectItemAdapter);
                                            if (d==0){
                                                setItemSelected(selectItemModelMap.get(""+d), position);
                                                selectItemAdapterMap.get(""+d).notifyDataSetChanged();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }


                containerLayout.addView(listView);
            }

        }
//        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.base_anim_popupWindowAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showPopupWindow = false;
            }
        });
        containerLayout.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }

        });

        return popupWindow;
    }

    /**
     * 设置选中项
     * @param list
     * @param position
     */
    private void setItemSelected(List<SelectItemModel> list,int position){
        try {
            for (int i=0;i<list.size();i++){
                list.get(i).setSelected(false);
            }
            list.get(position).setSelected(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置菜单显示文字
     */
    private void setMenuText(){
        for (int i=0;i<menuTextViewList.size();i++){
            menuTextViewList.get(i).setText(selectMenuModelList.get(i).getShowText());
        }
    }

    public ISelectedListener getiSelectedListener() {
        return iSelectedListener;
    }

    public void setiSelectedListener(ISelectedListener iSelectedListener) {
        this.iSelectedListener = iSelectedListener;
    }

    public boolean isShowPopupWindow() {
        return showPopupWindow;
    }

    public void setShowPopupWindow(boolean showPopupWindow) {
        this.showPopupWindow = showPopupWindow;
    }
}
