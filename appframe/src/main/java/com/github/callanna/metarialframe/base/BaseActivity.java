package com.github.callanna.metarialframe.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.callanna.metarialframe.R;
import com.github.callanna.metarialframe.util.OSUtil;

/**
 * Description  框架基础Activity类
 * Created by chenqiao on 2015/7/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity context;
    /**
     * 替代Actionbar的Toolbar
     */
    private Toolbar mToolbar;

    private TextView mTitleTv;

    private FragmentManager fragmentManager;
    /**
     * Toolbar之下的layout
     */
    private FrameLayout mContentLayout;

    /**
     * 是否注册了EventBus，true时会在onDestroy()中自动注销
     */

    private boolean isSupportActionbar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContentLayout = (FrameLayout) findViewById(R.id.rootlayout_baseactivity);
        context = this;
        ActivityTaskStack.add(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleTv = (TextView) findViewById(R.id.toolbarTitle);

        if (setToolbarAsActionbar()) {
            isSupportActionbar = true;
            setSupportActionBar(mToolbar);
        } else {
            isSupportActionbar = false;
        }

        setTitle("");
        fragmentManager = getSupportFragmentManager();
        onBaseActivityCreated(savedInstanceState);
    }

    /**
     * 设定是否将Toolbar作为Actionbar使用，将会
     * 影响Toolbar的菜单的使用方法。
     *
     * @return true则使用{@link #onCreateMyToolbarMenu()}进行菜单创建
     */
    protected abstract boolean setToolbarAsActionbar();
     public void uesCustomToolBar(){
         mToolbar.setVisibility(View.GONE);
     }
    /**
     * 设置Toolbar标题
     *
     * @param title    标题
     * @param isCenter 是否居中
     */
    public void setToolbarTitle(String title, boolean isCenter) {
        if (isSupportActionbar) {
            if (!isCenter) {
                getSupportActionBar().setTitle(title);
            } else {
                mTitleTv.setText(title);
            }
        } else {
            if (!isCenter) {
                mToolbar.setTitle(title);
            } else {
                mTitleTv.setText(title);
            }
        }
    }

    /**
     * 设置Toolbar标题
     *
     * @param resid    标题资源
     * @param isCenter 是否居中
     */
    public void setToolbarTitle(int resid, boolean isCenter) {
        if (isSupportActionbar) {
            if (!isCenter) {
                getSupportActionBar().setTitle(resid);
            } else {
                mTitleTv.setText(resid);
            }
        } else {
            if (!isCenter) {
                mToolbar.setTitle(resid);
            } else {
                mTitleTv.setText(resid);
            }
        }
    }

    /**
     * 设置居中标题的颜色
     *
     * @param color 颜色
     */
    public final void setCenterTitleColor(int color) {
        if (mTitleTv != null) {
            mTitleTv.setTextColor(color);
        }
    }

    /**
     * 设置居中标题的字体大小
     *
     * @param size 大小
     */
    public final void setCenterTitleSize(float size) {
        if (mTitleTv != null) {
            mTitleTv.setTextSize(size);
        }
    }

    /**
     * 代替onCreate的入口类
     *
     * @param savedInstanceState
     */
    protected abstract void onBaseActivityCreated(Bundle savedInstanceState);



    /**
     * 重写onDestroy，如果注册了EventBus，则需要注销
     */
    @Override
    protected void onDestroy() {

        ActivityTaskStack.remove(this);
        super.onDestroy();
    }

    /**
     * 获取内容布局id
     *
     * @return 根布局Id
     */
    public final int getRootFrameLayoutId() {
        return R.id.rootlayout_baseactivity;
    }

    /**
     * 获取Toolbar下的根布局
     *
     * @return 根布局
     */
    public final FrameLayout getRootFrameLayout() {
        return mContentLayout;
    }

    /**
     * 设置Activity的中心内容
     *
     * @param layoutResID 资源Id
     */
    protected final void setMyContentView(int layoutResID) {
        if (mContentLayout != null) {
            LayoutInflater.from(this).inflate(layoutResID, mContentLayout, true);
        }
    }

    protected final void setMyContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
    }

    protected final View findViewByTag(Object tag) {
        return mContentLayout.findViewWithTag(tag);
    }



    /**
     * 替换Activity的内容
     *
     * @param fragment
     * @param isBackStack
     */
    protected void replaceFragment(BaseFragment fragment, String isBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(isBackStack)) {
            fragmentTransaction.replace(R.id.rootlayout_baseactivity, fragment);
        } else {
            fragmentTransaction.replace(R.id.rootlayout_baseactivity, fragment, isBackStack);
            fragmentTransaction.addToBackStack(isBackStack);
        }
        OSUtil.closeInputMethod(context);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * Toolbar相关
     */
    public final Toolbar getToolbar() {
        if (mToolbar != null) {
            return mToolbar;
        } else {
            return null;
        }
    }

    public final void hideToolbar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    public final void showToolbar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

}