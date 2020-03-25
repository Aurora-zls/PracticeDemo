package com.example.zls.widget.custom_view.animator_view;

import com.example.zls.R;
import com.example.zls.utils.ScreenUtils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaichuanfa on 2019-10-18
 */
public class RewardCollectLayout extends RelativeLayout {

    public static final int REWARD_TYPE_CASH = 0;

    public static final int REWARD_TYPE_ENERGY = 1;

    public static final int REWARD_TYPE_JOY = 2;

    public static final int REWARD_TYPE_CHIP = 3;

    private List<ImageView> mRewardViews;

    private Path mPath;

    private Paint mPaint;

    private Path mLinePath;

    public RewardCollectLayout(Context context) {
        this(context, null);
    }

    public RewardCollectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RewardCollectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRewardViews = new ArrayList<>();
        mPath = new Path();
        mPaint = new Paint();
        mLinePath = new Path();
        mLinePath.moveTo(200, 1500);
        mLinePath.quadTo(900, 1400, 900, 100);
    }

    private void createRewardView(int rewardType) {
        for (int i = 0; i < 3; i++) {
            ImageView rewardIcon = new ImageView(getContext());
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            switch (rewardType) {
                case REWARD_TYPE_CASH:
                    rewardIcon.setImageResource(R.drawable.ic_collect_rupee_icon);
                    break;
                case REWARD_TYPE_JOY:
                    rewardIcon.setImageResource(R.drawable.ic_collect_joy_icon);
                    break;
                case REWARD_TYPE_ENERGY:
                    rewardIcon.setImageResource(R.drawable.ic_chicken_energy_small);
                    break;
                case REWARD_TYPE_CHIP:
                    rewardIcon.setImageResource(R.drawable.ic_chip_icon_96_96);
                    break;
                default:
                    break;
            }
            rewardIcon.setVisibility(INVISIBLE);
            addView(rewardIcon, params);
            mRewardViews.add(rewardIcon);
        }
    }

    public void showRewardAddAnimation(int rewardType, int[] startLocation, int[] endLocation) {
        if (mRewardViews.size() == 0) {
            createRewardView(rewardType);
        }
        int startX = startLocation[0] + ScreenUtils.dip2px(getContext(), 5);
        int startY = startLocation[1] + ScreenUtils.dip2px(getContext(), 3);
        long startDelay = 0;
        for (ImageView icon : mRewardViews) {
            icon.setVisibility(VISIBLE);
            icon.setTranslationX(startX);
            icon.setTranslationY(startY);
            AnimatorSet animatorSet = new AnimatorSet();
            mPath.moveTo(startLocation[0], startLocation[1]);
            mPath.quadTo(900, 1400, endLocation[0], endLocation[1]);

            ObjectAnimator scaleXLargeAnimator = ObjectAnimator.ofFloat(icon, "scaleX", 0.0f, 2.0f);
            ObjectAnimator scaleYLargeAnimator = ObjectAnimator.ofFloat(icon, "scaleY", 0.0f, 2.0f);
            scaleXLargeAnimator.setDuration(1000);
            scaleYLargeAnimator.setDuration(1000);
            scaleXLargeAnimator.setInterpolator(new OvershootInterpolator());
            scaleYLargeAnimator.setInterpolator(new OvershootInterpolator());
            scaleXLargeAnimator.setStartDelay(startDelay);
            scaleYLargeAnimator.setStartDelay(startDelay);

            ObjectAnimator translationAnimator = ObjectAnimator
                    .ofFloat(icon, "translationX", "translationY", mPath);
            translationAnimator.setDuration(10000);
            translationAnimator.setInterpolator(new LinearInterpolator());
            translationAnimator.setRepeatCount(0);
            translationAnimator.setStartDelay(startDelay);

            ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(icon, "rotation", 0f, -90.0f);
            rotationAnimator.setInterpolator(new AccelerateInterpolator());
            rotationAnimator.setStartDelay(startDelay);
            rotationAnimator.setDuration(10000);

            ObjectAnimator sXLargeAnimator = ObjectAnimator.ofFloat(icon, "scaleX", 2.0f, 0.8f);
            ObjectAnimator sYLargeAnimator = ObjectAnimator.ofFloat(icon, "scaleY", 2.0f, 0.8f);
            sXLargeAnimator.setDuration(10000);
            sYLargeAnimator.setDuration(10000);
            sXLargeAnimator.setInterpolator(new AccelerateInterpolator());
            sYLargeAnimator.setInterpolator(new AccelerateInterpolator());
            sXLargeAnimator.setStartDelay(startDelay);
            sYLargeAnimator.setStartDelay(startDelay);

            animatorSet.play(scaleXLargeAnimator).with(scaleYLargeAnimator)
                    .before(rotationAnimator)
                    .before(sXLargeAnimator)
                    .before(sYLargeAnimator)
                    .before(translationAnimator);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    playAnimationEnd();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.start();
            startDelay += 100;
        }
    }

    public void playAnimationEnd() {
//        setVisibility(INVISIBLE);
        for (ImageView icon : mRewardViews) {
            removeView(icon);
        }
        mRewardViews.clear();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(10.0f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mLinePath, mPaint);
    }
}
