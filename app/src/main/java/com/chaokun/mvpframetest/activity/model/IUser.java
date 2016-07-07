package com.chaokun.mvpframetest.activity.model;

/**
 * Created by linchaokun on 2016/7/7.
 * 用户接口
 */
public interface IUser {
    String getName();
    String getPass();
    int checkUserIsNull(String name,String pass);
}
