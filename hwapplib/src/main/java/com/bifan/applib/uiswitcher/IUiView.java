package com.bifan.applib.uiswitcher;

import android.view.View;


public interface IUiView {

	void ShowRightNow();

	void ShowWithAnimation();

	void HideRightNow();

	void HideWithAnimation();

	Boolean IsAnimating();

	void CancelAnimation();

	Boolean IsVisible();

	Boolean IsTotallyVisible();

	View getView();

	void onDestroy();
}
