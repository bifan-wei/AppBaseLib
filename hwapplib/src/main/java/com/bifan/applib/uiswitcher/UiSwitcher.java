package com.bifan.applib.uiswitcher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class UiSwitcher extends RelativeLayout implements IUiSwitcher {
    private IUiView mBgView;
    private IUiView mLoadingView;
    private IUiView mMessageView;

    public IUiView getLoadingView() {
        return mLoadingView;
    }

    public IUiView getMessageView() {
        return mMessageView;
    }

    public UiSwitcher(Context context) {
        super(context);
        init();
    }

    public UiSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UiSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    protected void init() {

    }

    private void CancelMessageViewAnimation() {
        mMessageView.CancelAnimation();
    }

    private void CancelLoadingViewAnimation() {
        mLoadingView.CancelAnimation();

    }

    @Override
    public void ShowLoadingViewRightNow() {

        showBgView();
        if (HasLoadingView()) {
            if (mLoadingView.IsTotallyVisible()) {//完全可见，不操作

            } else {
                if (mLoadingView.IsVisible()) {//说明正在执行可见动画

                } else {//说明不可见，取消动画，使完全可见
                    CancelLoadingViewAnimation();
                    mLoadingView.ShowRightNow();

                }
            }
        }


    }

    @Override
    public void ShowLoadingViewWithAnimation() {
        showBgView();

        if (HasLoadingView()) {
            if (mLoadingView.IsTotallyVisible()) {//完全可见，不操作

            } else {
                if (mLoadingView.IsVisible()) {//说明正在执行可见动画不操作

                } else {//说明不可见，执行动画
                    CancelLoadingViewAnimation();
                    mLoadingView.ShowWithAnimation();
                }
            }


        }


    }

    @Override
    public void ShowMessageViewRightNow() {
        showBgView();

        if (HasLoadingView()) {
            if (mLoadingView.IsTotallyVisible()) {//完全可见，不操作
                mMessageView.ShowRightNow();
                mLoadingView.HideRightNow();

            } else {
                if (mLoadingView.IsVisible()) {//说明正在执行可见动画
                    CancelLoadingViewAnimation();
                    mMessageView.ShowRightNow();
                    mLoadingView.HideRightNow();

                } else {//说明不可见，取消动画，使完全可见
                    CancelLoadingViewAnimation();
                    mMessageView.ShowRightNow();
                }
            }


        }


    }

    @Override
    public void ShowMessageViewWithAnimation() {
        showBgView();

        if (HasLoadingView()) {
            if (mLoadingView.IsTotallyVisible()) {//完全可见，不操作
                CancelMessageViewAnimation();
                mMessageView.ShowRightNow();
                mLoadingView.HideWithAnimation();

            } else {
                if (mLoadingView.IsVisible()) {//说明正在执行可见动画
                    CancelLoadingViewAnimation();
                    mMessageView.ShowRightNow();
                    mLoadingView.HideWithAnimation();
                } else {//说明不可见，取消动画，使完全可见
                    CancelLoadingViewAnimation();
                    mMessageView.ShowWithAnimation();
                }
            }


        }


    }


    @Override
    public void ShowDataViewRightNow() {
        hideBgView();
        CancelMessageViewAnimation();
        CancelLoadingViewAnimation();

        if (HasMessageView() && HasLoadingView()) {
            if (HasMessageView()) {
                mMessageView.HideRightNow();
                mLoadingView.HideRightNow();
            }
        }
    }

    @Override
    public void ShowDataViewWithAnimation() {
        hideBgView();

        if (HasMessageView() || HasLoadingView()) {

            if (mLoadingView.IsVisible()) {//
                CancelMessageViewAnimation();
                CancelLoadingViewAnimation();
                if (mMessageView.IsVisible()) {
                    mMessageView.HideRightNow();
                    mLoadingView.HideWithAnimation();
                } else {

                    mLoadingView.HideWithAnimation();
                }

            } else {

                CancelMessageViewAnimation();
                if (mMessageView.IsVisible()) {
                    mMessageView.HideWithAnimation();
                }
            }
        }
    }

    private void showBgView() {
        if (mBgView != null) {
            mBgView.ShowRightNow();
        }
    }

    private void hideBgView() {
        if (mBgView != null) {
            mBgView.HideRightNow();
        }
    }

    @Override
    public IUiSwitcher setLoadingView(IUiView loadingView) {
        mLoadingView = loadingView;
        return this;

    }

    @Override
    public IUiSwitcher setMessageView(IUiView messageView) {
        mMessageView = messageView;
        return this;
    }

    @Override
    public IUiSwitcher setBgView(IUiView messageView) {

        mBgView = messageView;
        return this;
    }

    @Override
    public void addViewDone() {
        if (mMessageView != null) {
            this.addView(mMessageView.getView());
        }
        if (mLoadingView != null) {
            this.addView(mLoadingView.getView());
        }

    }

    private Boolean HasMessageView() {
        return mMessageView != null;
    }

    private Boolean HasLoadingView() {
        return mLoadingView != null;
    }

    @Override
    public void cancelLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.CancelAnimation();
        }
    }

    @Override
    public void onDestroy() {
        if (mBgView != null) {
            mBgView.onDestroy();
        }

        if (mLoadingView != null) {
            mLoadingView.onDestroy();
        }

        if (mMessageView != null) {
            mMessageView.onDestroy();
        }

        mBgView = null;
        mLoadingView = null;
        mMessageView = null;
    }
}
