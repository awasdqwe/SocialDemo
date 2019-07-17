package com.ljc.socialdemo.ui;

import org.greenrobot.greendao.query.LazyList;

import java.util.List;

/**
 * Created by chejiangwei on 2017/9/30.
 * Describe:
 */

public  class IListDao<T extends Object> {
    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    private List<T> objects;
}
