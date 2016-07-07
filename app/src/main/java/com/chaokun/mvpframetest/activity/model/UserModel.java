package com.chaokun.mvpframetest.activity.model;

import android.text.TextUtils;

/**
 * Created by linchaokun on 2016/7/7.
 * 用户实体类
 */
public class UserModel implements IUser{
    private String name;
    private String pass;

    public UserModel(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * 判断用户名或密码是否为空
     * @param name
     * @param pass
     * @return
     */
    @Override
    public int checkUserIsNull(String name, String pass) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)){
            return 1;
        }
        return 0;
    }
}
