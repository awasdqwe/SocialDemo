package com.ljc.socialdemo.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.utils.UIUtils;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by chejiangwei on 2018/2/5.
 * Describe:评论popupwindow
 */

public class CommentPop extends BasePopupWindow {
    private EditText    editText;
    private PubListener listener;

    public CommentPop(Activity context) {
        super(context);
        getPopupWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editText = (EditText) findViewById(R.id.et_comment);
        findViewById(R.id.tv_pub).setOnClickListener(v -> {
            if (listener != null) {
                String text = getText();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(context, "请输入评论", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onPub(text);
            }
            editText.setText("");
            dismiss();
        });
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateAnimation(0, 700 * 2, 300);
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_comment);
    }

    @Override
    public View initAnimaView() {
        //        return itemsVg;
        return findViewById(R.id.vg_bottom);
    }

    public void setHintText(String hintText) {
        editText.setHint(hintText);
        editText.setText("");
    }

    /**
     * 获取text
     *
     * @return
     */
    public String getText() {
        return editText.getText().toString();
    }

    public CommentPop setListener(PubListener listener) {
        this.listener = listener;
        return this;
    }

    public interface PubListener {
        void onPub(String text);
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        new Handler().postDelayed(() -> {
            editText.requestFocus();
            showSoftInput(editText);
        }, 100);
    }

    /**
     * 动态显示软键盘
     *
     * @param edit 输入框
     */
    public static void showSoftInput(EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) SociaDemoApplication.mAppContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, 0);
    }

}
