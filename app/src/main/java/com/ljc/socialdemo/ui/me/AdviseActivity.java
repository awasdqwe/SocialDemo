package com.ljc.socialdemo.ui.me;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ljc.socialdemo.R;
import com.ljc.socialdemo.widget.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 18075 on 2018/11/9.
 * 建议
 */

public class AdviseActivity extends Activity {


    @BindView(R.id.toolbar)
    MyToolBar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise);
        ButterKnife.bind(this);
        toolbar.setTitleLeftClickListener(v -> finish()).setTitleRightClickListener(v -> {
            Toast.makeText(AdviseActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
