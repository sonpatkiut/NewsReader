package com.example.icsssd358.newsviewer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.icsssd358.newsviewer.BR;
import com.example.icsssd358.newsviewer.R;
import com.example.icsssd358.newsviewer.Utils.AppConstants;
import com.example.icsssd358.newsviewer.Utils.EventBus;
import com.example.icsssd358.newsviewer.Utils.NetworkUtils;
import com.example.icsssd358.newsviewer.adapters.NewsAdapter;
import com.example.icsssd358.newsviewer.databinding.ActivityNewsBinding;
import com.example.icsssd358.newsviewer.datahandler.DataBaseHelper;
import com.example.icsssd358.newsviewer.listeners.NewsHeadLineCallBack;
import com.example.icsssd358.newsviewer.model.NewsHeadlineResponse;
import com.example.icsssd358.newsviewer.viewmodel.NewsViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.flowables.ConnectableFlowable;

public class NewsActivity extends BaseActivity<ActivityNewsBinding, NewsViewModel> implements NewsHeadLineCallBack {

    @Inject
    NewsViewModel newsViewModel;

    @Inject
    NewsAdapter adapter;

    @Inject
    EventBus eventBus;

    ActivityNewsBinding mActivityNewsBinding;

    DataBaseHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNewsBinding = getViewDataBinding();
        helper = DataBaseHelper.getInstance(this, null);
        newsViewModel.setCallback(this);
        setAdapter();
        makeRequest();
    }

    public void setAdapter(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityNewsBinding.newsRecycler.setLayoutManager(mLayoutManager);
        mActivityNewsBinding.newsRecycler.setItemAnimator(new DefaultItemAnimator());
        mActivityNewsBinding.newsRecycler.setAdapter(adapter);
    }

    private void registerForEvents(){
        ConnectableFlowable<Object> eventEmitter = eventBus.asFlowable().publish();
        newsViewModel.getCompositeDisposable().add(eventEmitter.subscribe(
                event -> {
                    if(event instanceof Pair){
                        Pair<String, Object> pair = (Pair<String, Object>) event;
                        switch (pair.first){
                            case AppConstants.NAVIGATE_NEXT:
                                if(AppConstants.isEventFiredByClick){
                                    AppConstants.isEventFiredByClick = false;
                                    navigateToNextActivity(pair.second);
                                }
                                break;
                        }
                    }
                }
        ));
        newsViewModel.getCompositeDisposable().add(eventEmitter.connect());
    }

    @Override
    public NewsViewModel getViewModel() {
        return newsViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void makeRequest() {
        if(NetworkUtils.isNetworkConnected(this)){
            getViewModel().getHeadlines();
        }else{
            List<NewsHeadlineResponse> list1 = helper.getHeadLinesOffline();
            if(list1!=null && list1.size()>0){
                getViewModel().isDataAvailable.set(true);
                updateUI(list1);
                showSnackBar(this, "You are viewing in offline mode");
            }else{
                handleNetworkError();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeRequest();
    }

    @Override
    public void updateUIForFailedResponse(String msg) {

    }

    @Override
    public void handleError(Throwable throwable) {
        try{
            updateUIForFailedResponse(throwable.getMessage());
        }catch (Exception e){
            updateUIForFailedResponse("Something went wrong");
        }
    }

    @Override
    public void handleNetworkError() {
        updateUIForFailedResponse("No Internet Connection!");
    }

    @Override
    public void navigateToNextActivity(Object object) {
        NewsHeadlineResponse response = (NewsHeadlineResponse) object;
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(AppConstants.NEWS_URL, response.getUrl());
        startActivity(intent);
    }

    @Override
    public void updateUI(List<NewsHeadlineResponse> list) {
        if(list!=null && list.size()>0){
            adapter.addItems(list);
            helper.deleteAll();
            helper.insertDataBase(list);
        }
    }

    public void showSnackBar(Activity activity, String message){
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerForEvents();
    }
}
