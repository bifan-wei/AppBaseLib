package com.bifan.applib.weidges;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;

/**
 * create by bifan-wei
 * 2017/9/19
 */
public class AnimationRecyclerView extends RecyclerView {
    public AnimationRecyclerView(Context context) {
        super(context);
    }

    public AnimationRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {
        if (getAdapter() != null){
            LayoutAnimationController.AnimationParameters animationParameters = params.layoutAnimationParameters;
            if (animationParameters == null){
                AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
                alphaAnimation.setDuration(1000);
                animationParameters = new LayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParameters;
            }
            animationParameters.count = count;
            animationParameters.index = index;
        }else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }
}