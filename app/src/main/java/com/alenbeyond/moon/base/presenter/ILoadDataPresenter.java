package com.alenbeyond.moon.base.presenter;


import com.alenbeyond.moon.base.view.IBaseView;

/**
 *
 * Created by allen on 2017/4/11.
 */

public interface ILoadDataPresenter<V extends IBaseView> extends IBasePresenter<V> {
    /**
     * 显示加载滚动条
     */
    void showLoading();

    /**
     * 隐藏加载滚动条
     */
    void hideLoading();

    /**
     * 显示加载重试
     */
    void showRetry();

    /**
     * 隐藏加载重试
     */
    void hideRetry();

    /**
     * 中途停止任务
     */
    void halfwayStop();
}
