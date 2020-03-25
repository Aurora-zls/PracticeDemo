package com.example.zls.widget.custom_view;

import com.example.zls.R;
import com.example.zls.utils.RxUtils;
import com.example.zls.utils.ScreenUtils;
import com.example.zls.utils.TimeUtil;
import com.example.zls.widget.action.Action0;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by chaichuanfa on 2019-10-24
 */
public class CustomCountdownView extends View {

    private Paint mPaint;

    private Bitmap mBitmap;

    private Disposable mCountdownDisposable;

    private long mCountdown;

    public CustomCountdownView(Context context) {
        this(context, null);
    }

    public CustomCountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_countdown_span_bg);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#392626"));
        mPaint.setTextSize(ScreenUtils.sp2px(context, 17));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setTextAlign(Paint.Align.CENTER);      //文字居中
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(ScreenUtils.dip2px(getContext(), 128),
                mBitmap.getHeight());
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String time = TimeUtil.duration2String(mCountdown);
        if (time.contains(":")) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
            canvas.drawBitmap(mBitmap, ScreenUtils.dip2px(getContext(), 50), 0, null);
            canvas.drawBitmap(mBitmap, ScreenUtils.dip2px(getContext(), 100), 0, null);
            drawTextCenter(canvas, ":", new RectF(ScreenUtils.dip2px(getContext(), 28), 0,
                    ScreenUtils.dip2px(getContext(), 50), getHeight()));
            drawTextCenter(canvas, ":", new RectF(ScreenUtils.dip2px(getContext(), 78), 0,
                    ScreenUtils.dip2px(getContext(), 100), getHeight()));
            //hh:mm::ss   或   mm:ss
            String[] times = time.split(":");
            if (times.length == 2) {
                drawTextCenter(canvas, "00",
                        new RectF(0, 0, ScreenUtils.dip2px(getContext(), 28), getHeight()));
                drawTextCenter(canvas, times[0],
                        new RectF(ScreenUtils.dip2px(getContext(), 50), 0,
                                ScreenUtils.dip2px(getContext(), 78), getHeight()));
                drawTextCenter(canvas, times[1],
                        new RectF(ScreenUtils.dip2px(getContext(), 100), 0,
                                ScreenUtils.dip2px(getContext(), 128), getHeight()));
            } else {
                drawTextCenter(canvas, times[0],
                        new RectF(0, 0, ScreenUtils.dip2px(getContext(), 28), getHeight()));
                drawTextCenter(canvas, times[1],
                        new RectF(ScreenUtils.dip2px(getContext(), 50), 0,
                                ScreenUtils.dip2px(getContext(), 78), getHeight()));
                drawTextCenter(canvas, times[2],
                        new RectF(ScreenUtils.dip2px(getContext(), 100), 0,
                                ScreenUtils.dip2px(getContext(), 128), getHeight()));
            }
        } else {
            drawTextCenter(canvas, time, new RectF(0, 0, getWidth(), getHeight()));
        }
    }

    /**
     * 文字居中
     */
    private void drawTextCenter(Canvas canvas, String text, RectF rectF) {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);
        canvas.drawText(text, rectF.centerX(), baseLineY, mPaint);
    }

    public void startCountdown(long countdown, Action0 endAction) {
        release();
        mCountdown = countdown;
        invalidate();
        mCountdownDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(countdown)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (aLong == countdown - 1) {
                        if (endAction != null) {
                            endAction.call();
                        }
                    } else {
                        mCountdown = countdown - aLong - 1;
                        invalidate();
                    }
                }, RxUtils.IgnoreErrorProcessor);
    }

    public void release() {
        if (mCountdownDisposable != null && !mCountdownDisposable.isDisposed()) {
            mCountdownDisposable.dispose();
        }
        mCountdownDisposable = null;
    }
}
