package com.bifan.applib.uiswitcher;

public abstract class UiAnimationState {

	public Boolean Cancel = false;

	public abstract void StartAnimation();
	public abstract void DoCancelAnimation();
	public abstract Boolean  IsAnimating();

	public void CancelAnimation() {
		Cancel = true;
		DoCancelAnimation();
	}

	public Boolean IsCancel() {
		return Cancel;
	}
}
