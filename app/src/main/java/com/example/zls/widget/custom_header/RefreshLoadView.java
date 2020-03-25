package com.example.zls.widget.custom_header;

import com.example.zls.R;
import com.example.zls.utils.ScreenUtils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by chaichuanfa on 2018/4/11.
 */

public class RefreshLoadView extends View {

    private Paint mPaint;

    private static final int RADIUS = ScreenUtils.dip2px(8);

    private ValueAnimator mValueAnimator;

    private int mCircleX = -1;

    private boolean repeatIndex;

    private boolean mAutoAnim;

    private int mRadius;

    public RefreshLoadView(Context context) {
        this(context, null);
    }

    public RefreshLoadView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = null;
            try {
                a = context.obtainStyledAttributes(attrs, R.styleable.RefreshLoadView);
                mAutoAnim = a.getBoolean(R.styleable.RefreshLoadView_auto_anim, false);
                mRadius = a.getDimensionPixelSize(R.styleable.RefreshLoadView_point_radius, RADIUS);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (a != null) {
                    a.recycle();
                }
            }
        }
        if (mRadius == 0) {
            mRadius = RADIUS;
        }
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        if (mAutoAnim) {
            startAnim();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mRadius * 4, mRadius * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCircleX != -1) {
            int height = getHeight();
            if (repeatIndex) {
                mPaint.setColor(Color.parseColor("#816EFF"));
                canvas.drawCircle(mCircleX, height / 2, mRadius, mPaint);

                int cx = getWidth() - mCircleX;
                mPaint.setColor(Color.parseColor("#FF3B30"));
                canvas.drawCircle(cx, height / 2, mRadius, mPaint);
            } else {
                int cx = getWidth() - mCircleX;
                mPaint.setColor(Color.parseColor("#FF3B30"));
                canvas.drawCircle(cx, height / 2, mRadius, mPaint);

                mPaint.setColor(Color.parseColor("#816EFF"));
                canvas.drawCircle(mCircleX, height / 2, mRadius, mPaint);
            }
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility != VISIBLE) {
            if (mValueAnimator != null && mValueAnimator.isRunning()) {
                stopAnim();
            }
        }
        super.setVisibility(visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mValueAnimator != null) {
            mValueAnimator.end();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.removeAllListeners();
            mValueAnimator = null;
        }
        super.onDetachedFromWindow();
    }

    private void initAnim() {
        int width = getWidth();
        mValueAnimator = ValueAnimator.ofInt(width / 2 - mRadius, width / 2 + mRadius);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setDuration(400);
        mValueAnimator.addUpdateListener(animation -> {
            mCircleX = (int) animation.getAnimatedValue();
            invalidate();
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                repeatIndex = !repeatIndex;
                if (!isShown() && mValueAnimator != null) {
                    mValueAnimator.end();
                }
            }
        });
    }

    public void startAnim() {
        post(() -> {
            if (mValueAnimator == null) {
                initAnim();
            }
            mValueAnimator.start();
        });
    }

    public void stopAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.end();
        }
    }
}
