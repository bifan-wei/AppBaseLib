package com.bifan.applib.uiswitcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.LayoutParams;

public abstract class UiBgView  implements IUiView {
	private View RootView;
	private Context context;

	public UiBgView(Context context) {
		this.context = context;
	}

	@Override
	public View getView() {
		if (RootView == null) {
			RootView = LayoutInflater.from(context).inflate(getLayout(), null);
			LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			RootView.setLayoutParams(p);
			RootView.setVisibility(View.GONE);
		}
		return RootView;
	}

	@Override
	public Boolean IsVisible() {
		return (getView().getVisibility() == View.VISIBLE) && (getView().getAlpha() > 0);
	}

	@Override
	public Boolean IsTotallyVisible() {

		return (getView().getVisibility() == View.VISIBLE) && (getView().getAlpha() == 0);
	}

	protected abstract int getLayout();
	/**
	 *
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
