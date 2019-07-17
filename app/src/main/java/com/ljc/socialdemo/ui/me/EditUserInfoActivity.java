package com.ljc.socialdemo.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.widget.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.UserInfoDao;

/**
 * Created by 18075 on 2018/11/9.
 */

public class EditUserInfoActivity extends Activity {


    @BindView(R.id.toolbar)
    MyToolBar toolbar;
    @BindView(R.id.et_content)
    EditText  etContent;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituserinfo);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra(Constant.TYPE, 0);
        initEvent();
    }

    private void initEvent() {
        switch (type) {
            case 1: //名字
                toolbar.setTitleText("修改用户名");
                break;
            case 2://爱好
                toolbar.setTitleText("修改爱好");
                break;
        }
        toolbar.setTitleLeftClickListener(v -> finish()).setTitleRightClickListener(v -> {
            String content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(EditUserInfoActivity.this, "内容不能为空", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = getIntent();
            switch (type) {
                case 1: //名字
                    intent.putExtra(Constant.NAME,content);
                    break;
                case 2://爱好
                    intent.putExtra(Constant.HOBBY,content);
                    break;
            }
            setResult(Activity.RESULT_OK,intent);
            finish();
        });
    }

}
