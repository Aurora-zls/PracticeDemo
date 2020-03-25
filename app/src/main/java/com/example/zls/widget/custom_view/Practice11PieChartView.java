package com.example.zls.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Practice11PieChartView extends View {

    private Paint mPaint;

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(100, 100, 900, 900, -180, 120, true, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(100, 100, 900, 900, -58, 58, true, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(100, 100, 900, 900, 2, 22, true, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(100, 100, 900, 900, 27, 23, true, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(100, 100, 900, 900, 54, 60, true, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(100, 100, 900, 900, 116, 62, true, mPaint);
    }
}
