package com.alenbeyond.moon.model;

import com.alenbeyond.moon.base.presenter.BasePresenter;
import com.alenbeyond.moon.base.view.IBaseView;

import rx.Subscriber;

/**
 * Created by Allen on 2017/4/14.
 */

public abstract class MoonSubscribe<T> extends Subscriber<T> {

    private BasePresenter mPresenter;
    private IBaseView mView;

    public MoonSubscribe(BasePresenter presenter, IBaseView view) {
        this.mView = view;
        this.mPresenter = presenter;
    }

    @Override
    public void onStart() {
        mView.showProgressDialog(null, "");
    }

    @Override
    public void onCompleted() {
        mView.dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        mView.showMessage(e.getMessage());
        mView.dismissProgressDialog();
    }

    @Override
    public void onNext(T result) {
        mPresenter.mRefreshTime = System.currentTimeMillis();
        onJesNext(result);
    }

    public abstract void onJesNext(T t);

}
