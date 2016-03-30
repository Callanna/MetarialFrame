package com.github.callanna.metarialframe.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.github.callanna.metarialframe.R;
import com.github.callanna.metarialframe.util.OSUtil;


/**
 * Description  框架基础Fragment
 * Created by chenqiao on 2015/7/16.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity context;
    private FrameLayout frameLayout;
    private View contentView;
    private FragmentManager fragmentManager;
    private boolean isRegisterEventBus = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = (FrameLayout) view.findViewById(R.id.rootlayout_basefragment);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = (BaseActivity) getActivity();
        fragmentManager = getFragmentManager();
        onBaseFragmentCreate(savedInstanceState);
    }

    /**
     * Fragment创建
     *
     * @param savedInstanceState
     * @author chenqiao
     */
    protected abstract void onBaseFragmentCreate(Bundle savedInstanceState);

    /**
     * 注册EventBus
     */
    protected final void registerEventBus() {
    }

    protected final void registerEventBusForSticky() {
    }

    /**
     * 重写onDestroy，如果注册了EventBus，则需要注销
     */
    @Override
    public void onDestroy() {

        super.onDestroy();
    }
    protected final FrameLayout getRootLayout() {
        return frameLayout;
    }

    /**
     * 获取Fragment显示的View
     *
     * @return
     */
    public final View getContentView() {
        return contentView;
    }

    /**
     * 设置内容
     *
     * @param resID
     */
    protected final void setMyContentView(int resID) {
        frameLayout.removeAllViews();
        contentView = LayoutInflater.from(context).inflate(resID, frameLayout);
    }

    protected final void setMyContentView(View view) {
        contentView = view;
        frameLayout.removeAllViews();
        frameLayout.addView(view);
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
            fragmentTransaction.replace(context.getRootFrameLayoutId(), fragment);
        } else {
            fragmentTransaction.replace(context.getRootFrameLayoutId(), fragment, isBackStack);
            fragmentTransaction.addToBackStack(isBackStack);
        }
        OSUtil.closeInputMethod(context);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 在{@link #setMyContentView(int) or #setMyContentView(View)}之后获取其中View
     *
     * @param resId
     * @return
     */
    protected final View findViewById(int resId) {
        return frameLayout.findViewById(resId);
    }

    protected final View findViewByTag(Object tag) {
        return frameLayout.findViewWithTag(tag);
    }

    /**
     * 结束当前fragment
     */
    protected final void finish() {
        /**
         * 如果当前fragment不是根fragment
         */
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        }
    }
}
