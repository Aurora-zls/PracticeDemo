package com.example.zls.types;

import androidx.annotation.DrawableRes;

public class RecyclerTestItem {

    private String content;

    @DrawableRes
    private int mDrawableRes;

    public RecyclerTestItem(String content, int drawableRes) {
        this.content = content;
        mDrawableRes = drawableRes;
    }

    public int getDrawableRes() {
        return mDrawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        mDrawableRes = drawableRes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
