package com.jamtech.jobinfo.model;

/**
 * Created by Rahul on 18-09-2015.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int iconId;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
        this.iconId=iconId;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public int getIconId(){
        return iconId;
    }

    public void setIconId(int iconId){
        this.iconId=iconId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
