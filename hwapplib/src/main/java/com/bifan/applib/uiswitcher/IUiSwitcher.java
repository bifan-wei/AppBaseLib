package com.bifan.applib.uiswitcher;

public interface IUiSwitcher {

	IUiSwitcher setLoadingView(IUiView loadingView);

	IUiSwitcher setMessageView(IUiView messageView);
	
	IUiSwitcher setBgView(IUiView messageView);

	void addViewDone();

	void ShowLoadingViewRightNow();

	void ShowLoadingViewWithAnimation();

	void ShowMessageViewRightNow();

	void ShowMessageViewWithAnimation();

	void ShowDataViewRightNow();

	void ShowDataViewWithAnimation();

	void cancelLoadingAnimation();

	void onDestroy();

}
