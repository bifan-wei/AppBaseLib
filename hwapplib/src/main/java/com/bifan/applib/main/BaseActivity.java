package com.bifan.applib.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * create by bifan-wei
 * on 2017/8/28
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Toast mToast = null;
    protected Boolean IsActivityDestroyed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        IsActivityDestroyed = true;
        super.onDestroy();
        AppActivityManager.getInstance().removeActivity(this);
    }

    public Boolean IsActivityDestroied() {
        return IsActivityDestroyed;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.commit();
    }


    public void switchFragment(int containerViewId, Fragment from, Fragment to) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        if (from != to) {
            if (!to.isAdded()) {
                mTransaction.hide(from).add(containerViewId, to);
            } else
                mTransaction.hide(from).show(to);

            mTransaction.commit();
        }

    }

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msg != null) {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });

    }

    protected static void Gone(View... views) {
        for (View v : views) {
            if(v!=null) {
                v.setVisibility(View.GONE);
            }
        }

    }
    protected static void Show(View... views) {
        for (View v : views) {
            if(v!=null) {
                v.setVisibility(View.VISIBLE);
            }
        }

    }

    protected static void Hide(View... views) {
        for (View v : views) {
            if(v!=null) {
                v.setVisibility(View.INVISIBLE);
            }
        }

    }


}
