package com.example.zls.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Practice10HistogramView extends View {

    private Paint mPaint;

    private float[] mPoints;

    private DataSet[] mDataSet;

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPoints = new float[]{100, 100, 100, 900, 100, 900, 1000, 900};
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        //现在写死的，之后有时间优化

        mPaint.setColor(Color.GREEN);
        canvas.drawRect(150, 870, 250, 900, mPaint);
        canvas.drawRect(300, 770, 400, 900, mPaint);
        canvas.drawRect(450, 600, 550, 900, mPaint);
        canvas.drawRect(600, 200, 700, 900, mPaint);
        canvas.drawRect(750, 110, 850, 900, mPaint);
        canvas.drawRect(900, 333, 1000, 900, mPaint);
        canvas.drawRect(1050, 666, 1150, 900, mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(28);
        canvas.drawText("Froyo", 150, 925, mPaint);
        canvas.drawText("GB", 300, 925, mPaint);
        canvas.drawText("ICS", 450, 925, mPaint);
        canvas.drawText("JB", 600, 925, mPaint);
        canvas.drawText("Kitkat", 750, 925, mPaint);
        canvas.drawText("LLLsfds", 900, 925, mPaint);
        canvas.drawText("Msdfh", 1050, 925, mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);
        canvas.drawLines(mPoints, mPaint);

    }

    public void setDataSet(DataSet[] dataSet) {
        mDataSet = dataSet;
    }

    public class DataSet {

        private String mTitle;

        private double mData;

        public DataSet(String title, double data) {
            mTitle = title;
            mData = data;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public double getData() {
            return mData;
        }

        public void setData(double data) {
            mData = data;
        }
    }
}
