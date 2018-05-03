package com.bifan.applib.weidges.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Size;

import com.bifan.applib.utils.DisPlayUtil;


public class LevelLoadingRenderer extends LoadingRenderer {
    private static final float DEFAULT_CENTER_RADIUS = 12.5f;
    private static final float DEFAULT_STROKE_WIDTH = 2.5f;

    private static final int[] DEFAULT_LEVEL_COLORS = new int[]{Color.parseColor("#55656aef"),
            Color.parseColor("#b1656aef"), Color.parseColor("#ff656aef")};

    private final Paint mPaint = new Paint();
    private final RectF mTempBounds = new RectF();

    private final Animator.AnimatorListener mAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationRepeat(Animator animator) {
            super.onAnimationRepeat(animator);
            storeOriginals();

        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            mRotationCount = 0;
        }
    };

    @Size(3)
    private int[] mLevelColors;
    @Size(3)
    private float[] mLevelSwipeDegrees;

    private float mStrokeInset;

    private float mRotationCount;
    private float mGroupRotation;

    private float mEndDegrees;
    private float mStartDegrees;
    private float mOriginEndDegrees;
    private float mOriginStartDegrees;

    private float mStrokeWidth;
    private float mCenterRadius;

    private LevelLoadingRenderer(Context context) {
        super(context);
        init(context);
        setupPaint();
        addRenderListener(mAnimatorListener);
    }

    private void init(Context context) {
        mStrokeWidth = DisPlayUtil.dip2px(context, DEFAULT_STROKE_WIDTH);
        mCenterRadius = DisPlayUtil.dip2px(context, DEFAULT_CENTER_RADIUS);

        mLevelSwipeDegrees = new float[3];
        mLevelColors = DEFAULT_LEVEL_COLORS;
    }

    private void setupPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        initStrokeInset((int) mWidth, (int) mHeight);
    }

    @Override
    protected void draw(Canvas canvas) {
        int saveCount = canvas.save();

        mTempBounds.set(mBounds);
        mTempBounds.inset(mStrokeInset, mStrokeInset);
        canvas.rotate(mGroupRotation, mTempBounds.centerX(), mTempBounds.centerY());

        for (int i = 0; i < 3; i++) {
            if (mLevelSwipeDegrees[i] != 0) {
                mPaint.setColor(mLevelColors[i]);
                canvas.drawArc(mTempBounds, mEndDegrees, mLevelSwipeDegrees[i], false, mPaint);
            }
        }

        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void computeRender(float renderProgress) {
        onMyCompute(renderProgress);

    }


    private void onMyCompute(float renderProgress) {
        int max = 360;
        mGroupRotation = renderProgress * max;
        float percent = renderProgress;
        float rate = 0.7f;//速率限制在1以下，不然与mGroupRotation速率一样，在percent < 0.5f,减速速度与mGroupRotation旋转速度一样
        //出现不会旋转的情况，相对速度的抵消的效果
        if (percent > 0.5f) {
            percent = 1 - renderProgress;
        }
        percent += 0.1f;//加多0.2，说明最大伸开范围为0.7f
        percent *= rate;
        float degree = percent * max;
        mLevelSwipeDegrees[1] = degree;
        mLevelSwipeDegrees[2] = degree;
        mLevelSwipeDegrees[0] = max;
    }


    @Override
    protected void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    protected void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    protected void reset() {
        resetOriginals();
    }

    private void initStrokeInset(float width, float height) {
        float minSize = Math.min(width, height);
        float strokeInset = minSize / 2.0f - mCenterRadius;
        float minStrokeInset = (float) Math.ceil(mStrokeWidth / 2.0f);
        mStrokeInset = strokeInset < minStrokeInset ? minStrokeInset : strokeInset;
    }

    private void storeOriginals() {
        mOriginEndDegrees = mEndDegrees;
        mOriginStartDegrees = mEndDegrees;

    }

    private void resetOriginals() {
        mOriginEndDegrees = 0;
        mOriginStartDegrees = 0;

        mEndDegrees = 0;
        mStartDegrees = 0;

        mRotationCount = 5;

        //开始的时候总是会有点问题，看看通过这样避免一下
        mStartDegrees = 360 * mRotationCount;
        mEndDegrees = mStartDegrees;

        storeOriginals();

        mLevelSwipeDegrees[0] = 0;
        mLevelSwipeDegrees[1] = 0;
        mLevelSwipeDegrees[2] = 0;
    }

    private void apply(Builder builder) {
        this.mWidth = builder.mWidth > 0 ? builder.mWidth : this.mWidth;
        this.mHeight = builder.mHeight > 0 ? builder.mHeight : this.mHeight;
        this.mStrokeWidth = builder.mStrokeWidth > 0 ? builder.mStrokeWidth : this.mStrokeWidth;
        this.mCenterRadius = builder.mCenterRadius > 0 ? builder.mCenterRadius : this.mCenterRadius;

        this.mDuration = builder.mDuration > 0 ? builder.mDuration : this.mDuration;

        this.mLevelColors = builder.mLevelColors != null ? builder.mLevelColors : this.mLevelColors;

        setupPaint();
        initStrokeInset(this.mWidth, this.mHeight);
    }

    public static class Builder {
        private Context mContext;

        private int mWidth;
        private int mHeight;
        private int mStrokeWidth;
        private int mCenterRadius;

        private int mDuration;

        @Size(3)
        private int[] mLevelColors;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder setStrokeWidth(int strokeWidth) {
            this.mStrokeWidth = strokeWidth;
            return this;
        }

        public Builder setCenterRadius(int centerRadius) {
            this.mCenterRadius = centerRadius;
            return this;
        }

        public Builder setDuration(int duration) {
            this.mDuration = duration;
            return this;
        }

        public Builder setLevelColors(@Size(3) int[] colors) {
            this.mLevelColors = colors;
            return this;
        }

        public Builder setLevelColor(int color) {
            return setLevelColors(new int[]{oneThirdAlphaColor(color), twoThirdAlphaColor(color), color});
        }

        public LevelLoadingRenderer build() {
            LevelLoadingRenderer loadingRenderer = new LevelLoadingRenderer(mContext);
            loadingRenderer.apply(this);
            return loadingRenderer;
        }

        private int oneThirdAlphaColor(int colorValue) {
            int startA = (colorValue >> 24) & 0xff;
            int startR = (colorValue >> 16) & 0xff;
            int startG = (colorValue >> 8) & 0xff;
            int startB = colorValue & 0xff;

            return (startA / 3 << 24) | (startR << 16) | (startG << 8) | startB;
        }

        private int twoThirdAlphaColor(int colorValue) {
            int startA = (colorValue >> 24) & 0xff;
            int startR = (colorValue >> 16) & 0xff;
            int startG = (colorValue >> 8) & 0xff;
            int startB = colorValue & 0xff;

            return (startA * 2 / 3 << 24) | (startR << 16) | (startG << 8) | startB;
        }
    }

}
