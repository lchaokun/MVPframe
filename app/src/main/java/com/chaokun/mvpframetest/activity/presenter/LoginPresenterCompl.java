package com.chaokun.mvpframetest.activity.presenter;

import com.chaokun.mvpframetest.activity.model.IUser;
import com.chaokun.mvpframetest.activity.model.UserModel;
import com.chaokun.mvpframetest.activity.view.ILoginView;

/**
 * Created by linchaokun on 2016/7/7.
 */
public class LoginPresenterCompl implements ILoginPresenter{

    private IUser iUser;
    private ILoginView iLoginView;


    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        initUsre();
    }


    private void initUsre() {
        iUser = new UserModel("linchaokun","111111");
    }

    @Override
    public void clear() {
        iLoginView.clear();
    }

    @Override
    public void doLogin(String name,String pass) {
        String result;
        int isNull = iUser.checkUserIsNull(name, pass);
        if(isNull==1){
            if(iUser.getName().equals(name) && iUser.getPass().equals(pass)){
                result = "登入成功";
            }else{
                result = "用户名或密码错误";
            }
        }else{
            result = "用户名密码不能为空";
        }

        iLoginView.onLoginResult(result);
    }
}
