package com.base.feima.baseproject.view.multilayer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.base.feima.baseproject.R;
import com.base.feima.baseproject.base.BaseActivity;
import com.base.feima.baseproject.view.chooseimages.ChooseImagesActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public class PopActivity extends BaseActivity {
    @InjectView(R.id.sample_multilayerChooseView)
    public MultilayerChooseView multilayerChooseView;
    private List<SelectMenuModel> selectMenuModelList = new ArrayList<SelectMenuModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTaskTag(getClass().getSimpleName());
        setContentView(R.layout.sample);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        initMenu();
        initMenu2();
        multilayerChooseView.init(selectMenuModelList);
    }

    private void initMenu(){
        List<Integer> listViewBackgroundIdList = new ArrayList<Integer>();
        listViewBackgroundIdList.add(R.color.white);
        listViewBackgroundIdList.add(R.color.bg_grey);
        SelectMenuModel selectMenuModel = new SelectMenuModel(0,R.drawable.base_style_selector,0,"item1","0",2,listViewBackgroundIdList);

        List<SelectItemModel> selectItemModelList = new ArrayList<SelectItemModel>();
        SelectItemModel selectItemModel = new SelectItemModel("123","11",false,"",0,R.drawable.base_style_selector);

        List<SelectItemModel> selectItemModelList11 = new ArrayList<SelectItemModel>();
        SelectItemModel selectItemModel11 = new SelectItemModel("123","111",false,"",0,R.drawable.base_style_selector);
        selectItemModelList11.add(selectItemModel11);
        selectItemModel.setSelectItemModelList(selectItemModelList11);

        selectItemModelList.add(selectItemModel);

        SelectItemModel selectItemModel2 = new SelectItemModel("123","21",false,"",0,R.drawable.base_style_selector);

        List<SelectItemModel> selectItemModelList21 = new ArrayList<SelectItemModel>();
        SelectItemModel selectItemModel21 = new SelectItemModel("123","211",false,"",0,R.drawable.base_style_selector);
        selectItemModelList21.add(selectItemModel21);
        selectItemModel2.setSelectItemModelList(selectItemModelList21);

        selectItemModelList.add(selectItemModel2);
        selectMenuModel.setSelectItemModelList(selectItemModelList);

        selectMenuModelList.add(selectMenuModel);
    }

    private void initMenu2(){
        List<Integer> listViewBackgroundIdList = new ArrayList<Integer>();
        listViewBackgroundIdList.add(R.color.white);
        listViewBackgroundIdList.add(R.color.bg_grey);
        SelectMenuModel selectMenuModel = new SelectMenuModel(0,R.drawable.base_style_selector,0,"item2","0",1,listViewBackgroundIdList);

        List<SelectItemModel> selectItemModelList = new ArrayList<SelectItemModel>();
        for (int i=0;i<24;i++){

            SelectItemModel selectItemModel = new SelectItemModel("123","1122222"+i,false,"",0,R.drawable.base_style_selector);
            selectItemModelList.add(selectItemModel);
        }

        selectMenuModel.setSelectItemModelList(selectItemModelList);

        selectMenuModelList.add(selectMenuModel);
    }

    @OnClick(R.id.texttt)
    public void show1(){
        Intent intent = new Intent(this, ChooseImagesActivity.class);
        intent.putExtra(ChooseImagesActivity.BROADCASTFLAG,9);
        startActivity(intent);
    }

    ChooseImagesBroadcastReciver reciver;

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

                }
            }
        }
    }



}