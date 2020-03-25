package com.example.zls.widget.custom_view.shape_view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ShapedConstraintLayout extends ConstraintLayout {

    private ShapedDelegate mDelegate;

    public ShapedConstraintLayout(Context context) {
        this(context, null);
    }

    public ShapedConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapedConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDelegate = ShapedDelegate.obtain(this, attrs, defStyleAttr, 0);
    }

    public void setColor(@ColorInt int argb) {
        mDelegate.setColor(argb);
    }

    public void setColors(@ColorInt int[] colors) {
        mDelegate.setColors(colors);
    }

    public void setGradientOrientation(GradientDrawable.Orientation orientation) {
        mDelegate.setGradientOrientation(orientation);
    }

    public void setStroke(int strokeWidth, @ColorInt int argb) {
        mDelegate.setStroke(strokeWidth, argb);
    }

    public void setCornerRadiis(int cornerRadii) {
        mDelegate.setCornerRadiis(cornerRadii);
    }
}
