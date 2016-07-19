package com.feima.baseproject.manager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MFragmentsManager {
    private static List<Fragment> fragmentList = new ArrayList<Fragment>();
    private static MFragmentsManager instance;

    private MFragmentsManager() {
    }

    public static synchronized MFragmentsManager getFragmentManagerInstance() {
        if (instance == null) {
            instance = new MFragmentsManager();
        }
        return instance;
    }

    /**
     * 添加Fragment
     * @param fragment
     */
    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }

    /**
     * 删除一个Fragment
     * @param fm
     * @param fragment
     */
    public void removeOneFragment(FragmentManager fm,Fragment fragment){
        try {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
            ft=null;
            fm.executePendingTransactions();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 删除所有Fragment
     * @param fm
     */
    public void removeAllFragment(FragmentManager fm){
        try {
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:fragmentList){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}