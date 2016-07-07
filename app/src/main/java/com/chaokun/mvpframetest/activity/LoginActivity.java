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

