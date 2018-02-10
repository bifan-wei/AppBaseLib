package com.bifan.applib.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	protected Activity activity;
	protected Boolean Destroyed = false;
	protected Toast mToast;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return getFragmentView(inflater);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findviewid(view);
	}

	protected abstract void findviewid(View view);

	protected abstract View getFragmentView(LayoutInflater inflater);

	protected void showToast(final String msg) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mToast != null) {
					mToast.cancel();
				}

				mToast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
				mToast.show();
			}
		});

	}

	@Override
	public void onDestroy() {
		Destroyed = true;
		super.onDestroy();
	}

}
