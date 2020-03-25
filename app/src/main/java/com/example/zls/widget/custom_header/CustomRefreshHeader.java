package com.example.zls.widget.custom_header;

import com.example.zls.R;
import com.example.zls.utils.ScreenUtils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by chaichuanfa on 2018/4/11.
 */

@SuppressLint("RestrictedApi")
public class CustomRefreshHeader extends FrameLayout implements RefreshHeader {

    private TextView mTvLoad;

    private RefreshLoadView mRefreshLoadView;

    public CustomRefreshHeader(@NonNull Context context) {
        this(context, null);
    }

    public CustomRefreshHeader(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMinimumHeight(ScreenUtils.dip2px(60));
        mTvLoad = new TextView(getContext());
        mTvLoad.setTextColor(Color.parseColor("#A4B4C1"));
        mTvLoad.setTextSize(12);
        mTvLoad.setGravity(Gravity.CENTER);
        mTvLoad.setText(R.string.pull_down_to_refresh);
        addView(mTvLoad, MATCH_PARENT, MATCH_PARENT);

        mRefreshLoadView = new RefreshLoadView(getContext());
        mRefreshLoadView.setVisibility(INVISIBLE);
        LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER);
        addView(mRefreshLoadView, params);
    }

    public void setTextColor(@ColorInt int color) {
        if (mTvLoad != null) {
            mTvLoad.setTextColor(color);
        }
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Scale;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height,
            int extendHeight) {
        mRefreshLoadView.startAnim();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mTvLoad.setVisibility(VISIBLE);
        mRefreshLoadView.setVisibility(INVISIBLE);
        if (success) {
            mTvLoad.setText(R.string.refresh_success);
        } else {
            mTvLoad.setText(R.string.refresh_fail);
        }
        return 300;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState,
            RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mTvLoad.setVisibility(VISIBLE);
                mRefreshLoadView.setVisibility(INVISIBLE);
                mTvLoad.setText(R.string.pull_down_to_refresh);
                break;
            case Refreshing:
                mTvLoad.setVisibility(INVISIBLE);
                mRefreshLoadView.setVisibility(VISIBLE);
                break;
            case ReleaseToRefresh:
                mTvLoad.setVisibility(VISIBLE);
                mRefreshLoadView.setVisibility(INVISIBLE);
                mTvLoad.setText(R.string.release_immediately_refresh);
                break;
        }
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height,
            int maxDragHeight) {

    }


}
