# MVPframe
MVP模式

在Android项目中，Activity和Fragment占据了大部分的开发工作。如果有一种设计模式（或者说代码结构）专门是为优化Activity和Fragment的代码而产生的，你说这种模式重要不？这就是MVP设计模式。


我们先看看一张图：

图中我们可以看到Model层不能直接与View层进行操作，而是通过Presenter进行交互。这也是MVC与MVP模式最大的区别。



MVP模式的核心思想

MVP把Activity中的UI逻辑抽象成View接口，把业务逻辑抽象成Presenter接口，Model类还是原来的Model。



MVP的作用

    分离了视图逻辑和业务逻辑，降低了耦合。
    Activity代码变得更加简洁。
    避免Activity的内存泄漏，导致OOM。



MVP模式的简单例子

一个简单的登入例子，点击登入验证用户名，密码是否为空是否正确，点击清空，可以清空输入内容。



下面看下项目的目录结构：


可以看到有model，view，presenter代表MVP。



我们先看看LoginActivity：

package com.chaokun.mvpframetest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaokun.mvpframetest.R;
import com.chaokun.mvpframetest.activity.presenter.ILoginPresenter;
import com.chaokun.mvpframetest.activity.presenter.LoginPresenterCompl;
import com.chaokun.mvpframetest.activity.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *Created by linchaokun on 2016/7/7.
 * MVP模式简单例子
 * 
 */
public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    ILoginPresenter iLoginPresenter;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_clear)
    Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        iLoginPresenter = new LoginPresenterCompl(this);

        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                iLoginPresenter.doLogin(etName.getText().toString(),etPass.getText().toString());
                break;
            case R.id.btn_clear:
                iLoginPresenter.clear();
                break;
        }
    }

    @Override
    public void clear() {
        etName.setText("");
        etPass.setText("");
    }

    @Override
    public void onLoginResult(String result) {
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }
}


我们可以看到里面包含了一个ILoginPresenter，所有的业务逻辑都是通过他来进行调用，Activity代码看起来是不是很简洁，还有这里我们还实现了ILoginView接口。


我们在来看下model层的代码：

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


IUser接口里面有一个checkUserIsNull方法，判断用户名密码是否为空，具体的实现通过UserModel来具体实现。


下面我们看下Presenter层代码：

package com.chaokun.mvpframetest.activity.presenter;

/**
 * Created by wcb1 on 2016/7/7.
 */
public interface ILoginPresenter {
    void clear();
    void doLogin(String name,String pass);
}


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


从代码可以看出，LoginPresenterCompl保留了ILoginView的引用，因此在LoginPresenterCompl里就可以直接进行UI操作了，而不用在Activity里完成。这里使用了ILoginView引用，而不是直接使用Activity，这样一来，如果在别的Activity里也需要用到相同的业务逻辑，就可以直接复用LoginPresenterCompl类了，这也是MVP的核心思想。


最后我们来看下View层代码：

package com.chaokun.mvpframetest.activity.view;

/**
 * Created by linchaokun on 2016/7/7.
 */
public interface ILoginView {
    void clear();
    void onLoginResult(String result);
}



View层的可以最上面的LoginActivity的实现。


最后附上完整的demo：

https://github.com/lchaokun/MVPframe


Android学习交流①群 152643026





