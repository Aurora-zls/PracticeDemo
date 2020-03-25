package com.example.zls.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Practice8DrawArcView extends View {

    private Paint mPaint;

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(100, 100, 400, 600, 120, 100, true, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(100, 100, 400, 600, -140, 30, true, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(100, 100, 400, 600, 20, 60, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(100, 100, 400, 600, -60, 60, false, mPaint);
    }
}
