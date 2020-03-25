package com.example.zls.widget.custom_view;

import com.example.zls.R;
import com.example.zls.utils.ScreenUtils;
import com.example.zls.widget.action.Action0;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.TimeUnit;

import androidx.annotation.DrawableRes;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BackgroundCountdownView extends View {

    private final int ONE_BG = 1;

    private final int TWO_BG = 2;

    private final int THREE_BG = 3;

    private Disposable mDisposable;

    private long mDuration;

    private int mDigitalGap;

    private int mImgTextGap;

    private Paint mPaint;

    private Paint.FontMetrics mMetrics;

    private String mFirstLineText;

    private int mYears;

    private int mDays;

    private String mHours;

    private String mMinutes;

    private String mSeconds;

    private Bitmap mBackground;

    private int mLastBgNum;

    public BackgroundCountdownView(Context context) {
        this(context, null);
    }

    public BackgroundCountdownView(Context context,
            @androidx.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundCountdownView(Context context,
            @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackground = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.ic_countdown_span_bg);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstLineText = getContext().getString(R.string.chicken_dialog_still_wait);
        mDigitalGap = ScreenUtils.dip2px(getContext(), 22);
        mImgTextGap = ScreenUtils.dip2px(getContext(), 8);
        mPaint.setColor(Color.parseColor("#392626"));
        mPaint.setStrokeWidth(10);
        mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 18));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mMetrics = mPaint.getFontMetrics();
    }

    private void duration2String(long duration) {
        //如果duration为0，策略为显示两个背景，显示00：00
        int hour = (int) (duration / 3600);
        int minute = (int) ((duration % 3600) / 60);
        int second = (int) ((duration % 3600) % 60);
        int day = hour / 24;
        int year = day / 365;
        if (year != 0) {
            mYears = year;
        } else if (day != 0) {
            mYears = 0;
            mDays = day;
        } else if (hour == 0) {
            mYears = 0;
            mDays = 0;
            mHours = "";
            mMinutes = getResources().getString(R.string.minutes_formatter, minute % 60);
            mSeconds = getResources().getString(R.string.seconds_formatter, second % 60);
        } else {
            mYears = 0;
            mDays = 0;
            mHours = getResources().getString(R.string.hours_formatter, hour);
            mMinutes = getResources().getString(R.string.minutes_formatter, minute % 60);
            mSeconds = getResources().getString(R.string.seconds_formatter, second % 60);
        }
        Timber.d("calculate complete");
    }

    private int bgNumNeedDraw() {
        if (mDays != 0 || mYears != 0) { //需要一个背景
            return ONE_BG;
        } else if (mHours.equals("")) { //需要两个背景
            return TWO_BG;
        } else { //有 时分秒 需要三个背景
            return THREE_BG;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Timber.d("on measure");
        int measuredWidth, measureHeight;
        measureHeight = mBackground.getHeight() + (int) getCountdownDrawStartY();
        switch (bgNumNeedDraw()) {
            case ONE_BG:
                measuredWidth = Math.max((int) mPaint.measureText(mFirstLineText),
                        mBackground.getWidth() + mImgTextGap + (int) (mYears > 0 ? mPaint
                                .measureText(getResources().getString(R.string.text_time_year))
                                : mPaint.measureText(
                                        getResources().getString(R.string.text_time_day))));
                break;
            case TWO_BG:
                measuredWidth = Math.max((int) mPaint.measureText(mFirstLineText),
                        mBackground.getWidth() * 2 + mDigitalGap);
                break;
            case THREE_BG:
            default:
                measuredWidth = Math.max((int) mPaint.measureText(mFirstLineText),
                        mBackground.getWidth() * 3 + mDigitalGap * 2);
                break;
        }
        setMeasuredDimension(measuredWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Timber.d("on layout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Timber.d("on draw");
        super.onDraw(canvas);
//        float textHeight = mMetrics.bottom + Math.abs(mMetrics.top);
        float textWidth = mPaint.measureText(mFirstLineText);
        if (mLastBgNum != bgNumNeedDraw()) {
            requestLayout();
        }
        switch (bgNumNeedDraw()) {
            case ONE_BG:
                mLastBgNum = ONE_BG;
                drawOneBg(textWidth, canvas);
                break;
            case TWO_BG:
                mLastBgNum = TWO_BG;
                drawTwoBg(textWidth, canvas);
                break;
            case THREE_BG:
                mLastBgNum = THREE_BG;
                drawThreeBg(textWidth, canvas);
                break;
            default:
                break;
        }
        Timber.d("on draw complete");
    }

    private void drawThreeBg(float textWidth, Canvas canvas) {
        int countdownWidth = mBackground.getWidth() * 3 + mDigitalGap * 2;
        float endY = getCountdownDrawStartY() + mBackground.getHeight();
        if (textWidth > countdownWidth) { //首行文字宽度大于 倒计时宽度 让倒计时中心对齐文字中心
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(mFirstLineText, 0, Math.abs(mMetrics.top), mPaint);
            canvas.drawBitmap(mBackground,
                    textWidth / 2 - (mBackground.getWidth() * 1.5f + mDigitalGap),
                    getCountdownDrawStartY(), null);
            mPaint.setTextAlign(Paint.Align.CENTER);
            drawTextCenter(canvas, String.valueOf(mHours),
                    new RectF(textWidth / 2 - (mBackground.getWidth() * 1.5f + mDigitalGap),
                            getCountdownDrawStartY(),
                            textWidth / 2 - (mBackground.getWidth() * 1.5f + mDigitalGap)
                                    + mBackground.getWidth(), endY));
            drawTextCenter(canvas, ":",
                    new RectF(
                            textWidth / 2 - (mDigitalGap + (mBackground.getWidth() >> 1)),
                            getCountdownDrawStartY(),
                            textWidth / 2 - (mBackground.getWidth() >> 1),
                            endY));
            canvas.drawBitmap(mBackground, textWidth / 2 - (mBackground.getWidth() >> 1),
                    getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mMinutes),
                    new RectF(textWidth / 2 - (mBackground.getWidth() >> 1),
                            getCountdownDrawStartY(),
                            textWidth / 2 - (mBackground.getWidth() >> 1) + mBackground.getWidth(),
                            endY));
            drawTextCenter(canvas, ":", new RectF(
                    textWidth / 2 + (mBackground.getWidth() >> 1),
                    getCountdownDrawStartY(),
                    textWidth / 2 + (mBackground.getWidth() >> 1) + mDigitalGap,
                    endY));
            canvas.drawBitmap(mBackground,
                    textWidth / 2 + (mBackground.getWidth() >> 1) + mDigitalGap,
                    getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mSeconds),
                    new RectF(textWidth / 2 + (mBackground.getWidth() >> 1) + mDigitalGap,
                            getCountdownDrawStartY(),
                            textWidth / 2 + (mBackground.getWidth() >> 1) + mDigitalGap
                                    + mBackground.getWidth(), endY));
        } else { //首行文字宽度小于 倒计时宽度 让文字中心对齐倒计时中心
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(mFirstLineText, countdownWidth / 2, Math.abs(mMetrics.top), mPaint);
            canvas.drawBitmap(mBackground, 0, getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mHours),
                    new RectF(0, getCountdownDrawStartY(), mBackground.getWidth(), endY));
            drawTextCenter(canvas, ":", new RectF(
                    mBackground.getWidth(),
                    getCountdownDrawStartY(),
                    mBackground.getWidth() + mDigitalGap,
                    endY));
            canvas.drawBitmap(mBackground, mBackground.getWidth() + mDigitalGap,
                    getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mMinutes),
                    new RectF(mBackground.getWidth() + mDigitalGap,
                            getCountdownDrawStartY(), mBackground.getWidth() * 2 + mDigitalGap,
                            endY));
            drawTextCenter(canvas, ":", new RectF(
                    mBackground.getWidth() * 2 + mDigitalGap,
                    getCountdownDrawStartY(),
                    (mBackground.getWidth() + mDigitalGap) * 2,
                    endY));
            canvas.drawBitmap(mBackground,
                    (mBackground.getWidth() + mDigitalGap) * 2,
                    getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mSeconds),
                    new RectF((mBackground.getWidth() + mDigitalGap) * 2,
                            getCountdownDrawStartY(), mBackground.getWidth() * 3 + mDigitalGap * 2,
                            endY));
        }
    }

    private void drawTwoBg(float textWidth, Canvas canvas) {
        int countdownWidth = mBackground.getWidth() * 2 + mDigitalGap;
        float endY = getCountdownDrawStartY() + mBackground.getHeight();
        if (textWidth > countdownWidth) { //首行文字宽度大于 倒计时宽度 让倒计时中心对齐文字中心
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(mFirstLineText, 0, Math.abs(mMetrics.top), mPaint);
            canvas.drawBitmap(mBackground,
                    textWidth / 2 - (mBackground.getWidth() + (mDigitalGap >> 1)),
                    getCountdownDrawStartY(), null);
            mPaint.setTextAlign(Paint.Align.CENTER);
            drawTextCenter(canvas, String.valueOf(mMinutes),
                    new RectF(textWidth / 2 - (mBackground.getWidth() + (mDigitalGap >> 1)),
                            getCountdownDrawStartY(),
                            textWidth / 2 - (mBackground.getWidth() + (mDigitalGap >> 1))
                                    + mBackground.getWidth(),
                            getCountdownDrawStartY() + mBackground.getHeight()));
            drawTextCenter(canvas, ":",
                    new RectF(
                            textWidth / 2 - (mDigitalGap >> 1),
                            getCountdownDrawStartY(),
                            textWidth / 2 + (mDigitalGap >> 1),
                            endY));
            canvas.drawBitmap(mBackground, textWidth / 2 + (mDigitalGap >> 1),
                    getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mSeconds),
                    new RectF(textWidth / 2 + (mDigitalGap >> 1), getCountdownDrawStartY(),
                            textWidth / 2 + (mDigitalGap >> 1) + mBackground.getWidth(),
                            getCountdownDrawStartY() + mBackground.getHeight()));
        } else {  //首行文字宽度小于 倒计时宽度 让文字中心对齐倒计时中心
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(mFirstLineText, countdownWidth / 2, Math.abs(mMetrics.top), mPaint);
            canvas.drawBitmap(mBackground, 0, getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mMinutes),
                    new RectF(0, getCountdownDrawStartY(), mBackground.getWidth(),
                            getCountdownDrawStartY() + mBackground.getHeight()));
            drawTextCenter(canvas, ":", new RectF(
                    mBackground.getWidth(),
                    getCountdownDrawStartY(),
                    mBackground.getWidth() + mDigitalGap,
                    endY));
            canvas.drawBitmap(mBackground, mBackground.getWidth() + mDigitalGap,
                    getCountdownDrawStartY(), null);
            drawTextCenter(canvas, String.valueOf(mSeconds),
                    new RectF(mBackground.getWidth() + mDigitalGap, getCountdownDrawStartY(),
                            mBackground.getWidth() * 2 + mDigitalGap,
                            getCountdownDrawStartY() + mBackground.getHeight()));
        }
    }

    private void drawOneBg(float textWidth, Canvas canvas) {
        int countdownWidth = mBackground.getWidth() + mImgTextGap + (int) (mYears > 0 ? mPaint
                .measureText(getResources().getString(R.string.text_time_year))
                : mPaint.measureText(getResources().getString(R.string.text_time_day)));
        if (textWidth > countdownWidth) { //首行文字宽度大于 倒计时宽度 让倒计时中心对齐文字中心
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(mFirstLineText, 0, Math.abs(mMetrics.top), mPaint);
            canvas.drawBitmap(mBackground, textWidth / 2 - (mBackground.getWidth() >> 1),
                    getCountdownDrawStartY(), null);
            mPaint.setTextAlign(Paint.Align.CENTER);
            drawTextCenter(canvas, mYears > 0 ? String.valueOf(mYears) : String.valueOf(mDays),
                    new RectF(
                            textWidth / 2 - (mBackground.getWidth() >> 1),
                            getCountdownDrawStartY(),
                            textWidth / 2 - (mBackground.getWidth() >> 1) + mBackground.getWidth(),
                            getCountdownDrawStartY() + mBackground.getHeight()
                    ));
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(getResources().getString(mYears > 0 ? R.string.text_time_year
                            : R.string.text_time_day),
                    textWidth / 2 - (mBackground.getWidth() >> 1) + mBackground.getWidth()
                            + mImgTextGap,
                    getCountdownDrawStartY() + mBackground.getHeight() - mMetrics.descent, mPaint);
        } else { //首行文字宽度小于 倒计时宽度 让文字中心对齐倒计时中心
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(mFirstLineText, countdownWidth >> 1, Math.abs(mMetrics.top), mPaint);
            canvas.drawBitmap(mBackground, 0, getCountdownDrawStartY(), null);
            drawTextCenter(canvas, mYears > 0 ? String.valueOf(mYears) : String.valueOf(mDays),
                    new RectF(0, getCountdownDrawStartY(), mBackground.getWidth(),
                            getCountdownDrawStartY() + mBackground.getHeight()));
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(getResources().getString(mYears > 0 ? R.string.text_time_year
                            : R.string.text_time_day),
                    mBackground.getWidth() + mImgTextGap,
                    getCountdownDrawStartY() + mBackground.getHeight() - mMetrics.descent, mPaint);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private float getCountdownDrawStartY() {
        return mMetrics.bottom + Math.abs(mMetrics.top) + ScreenUtils.dip2px(getContext(), 9);
    }

    /**
     * 使文字矩形区域内居中绘制
     */
    private void drawTextCenter(Canvas canvas, String text, RectF rectF) {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);
        canvas.drawText(text, rectF.centerX(), baseLineY, mPaint);
    }

    public void startCountDown(long duration, Action0 endAction) {
        initCountDown(duration);
        mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mDuration = duration - aLong;
                    handleDrawCondition(aLong);
                    if (mDuration == aLong && endAction != null) {
                        endAction.call();
                    }
                }, throwable -> {
                    Timber.d("count down error");
                });
    }

    private void initCountDown(long duration) {
        duration2String(duration);
        invalidate();
        Timber.d("calculate first time");
    }

    private void handleDrawCondition(Long aLong) {
        duration2String(mDuration);
        invalidate();
        Timber.d("calculate countdown time : %s", aLong);
    }

    public void setFirstLineText(String firstLineText) {
        mFirstLineText = firstLineText;
        requestLayout();
    }

    public void setBackgroundResource(@DrawableRes int background) {
        mBackground = BitmapFactory.decodeResource(getResources(), background);
        requestLayout();
    }

    public void setDigitalGap(int digitalGap) {
        mDigitalGap = digitalGap;
        requestLayout();
    }

    public void release() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }
}
