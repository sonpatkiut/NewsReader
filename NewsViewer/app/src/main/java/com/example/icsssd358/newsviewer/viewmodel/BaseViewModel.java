package com.example.icsssd358.newsviewer.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<N> extends ViewModel {

    private CompositeDisposable mCompositeDisposable;
    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private N mCallback;
    private final ObservableBoolean mChecked = new ObservableBoolean(false);


    public void onViewCreated() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public void onDestroyView() {
        mCompositeDisposable.dispose();
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public void setCallback(N mCallback) {
        this.mCallback = mCallback;
    }

    public N getCallback() {
        return mCallback;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable mCompositeDisposable){
        this.mCompositeDisposable = mCompositeDisposable;
    }

    public ObservableBoolean getChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked.set(checked);
    }
}
