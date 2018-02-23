package com.bifan.applib.weidges;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class CircleView extends View {

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private float Radius = 0;
    private int CoverColor = Color.parseColor("#66ffffff");


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(CoverColor);
    }

    public void setCoverColor(int CoverColor) {
        this.CoverColor = CoverColor;
        mPaint.setColor(CoverColor);
        postInvalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int vWidth = getWidth();
        int vHeight = getHeight();

        int radius = Math.max(vHeight, vWidth) / 2;

        float Cx = vWidth / 2;
        float Cy = vHeight / 2;

        canvas.drawCircle(Cx, Cy, radius, mPaint);

    }

}
