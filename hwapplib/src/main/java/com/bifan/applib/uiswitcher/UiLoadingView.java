package com.bifan.applib.uiswitcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;

public abstract class UiLoadingView implements IUiView {
    protected View RootView;
    protected Context context;
    protected ImageView Pic;

    UiLoadingView(Context context) {
        this.context = context;
    }

    @Override
    public View getView() {
        if (RootView == null) {
            RootView = LayoutInflater.from(context).inflate(getLayout(), null);
            findPic();
            LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            RootView.setLayoutParams(p);
            RootView.setVisibility(View.GONE);
            afterInflateView();
        }
        return RootView;
    }

    protected void findPic() {

    }

    protected void afterInflateView() {

    }

    @Override
    public Boolean IsVisible() {
        return (getView().getVisibility() == View.VISIBLE) && (getView().getAlpha() > 0);
    }

    @Override
    public Boolean IsTotallyVisible() {

        return (getView().getVisibility() == View.VISIBLE) && (getView().getAlpha() == 1);
    }

    protected abstract int getLayout();

    /**
     * 必须在主线程执行
     */
    @Override
    public void ShowRightNow() {
        getView().setVisibility(View.VISIBLE);
    }

    /**
     * 必须在主线程执行
     */
    @Override
    public void HideRightNow() {
        getView().setVisibility(View.GONE);
    }

}
