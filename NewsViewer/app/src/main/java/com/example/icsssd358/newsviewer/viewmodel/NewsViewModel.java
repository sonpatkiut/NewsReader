package com.example.icsssd358.newsviewer.viewmodel;

import android.databinding.ObservableBoolean;

import com.example.icsssd358.newsviewer.Utils.AppConstants;
import com.example.icsssd358.newsviewer.Utils.EventBus;
import com.example.icsssd358.newsviewer.apihandler.AppApiHelper;
import com.example.icsssd358.newsviewer.listeners.BaseCallback;
import com.example.icsssd358.newsviewer.listeners.NewsHeadLineCallBack;
import com.example.icsssd358.newsviewer.model.NewsHeadlineResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsViewModel extends BaseViewModel<NewsHeadLineCallBack> {

    public ObservableBoolean isDataAvailable;
    AppApiHelper helper;
    public NewsViewModel(EventBus eventBus) {
        super();
        isDataAvailable = new ObservableBoolean(false);
    }

    public void retry(){
        getCallback().makeRequest();
    }

    public void getHeadlines(){
        setIsLoading(true);
        helper = new AppApiHelper();
        getCompositeDisposable().add(
                helper.getHeadLines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    String status = jsonObject.getString("status");
                    if(status.equalsIgnoreCase(AppConstants.STATUS_OK)){
                        int totalRecords = jsonObject.getInt(AppConstants.TOTAL_RESULTS);
                        if(totalRecords > 0){
                            JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.ARTICLES);
                            if(jsonArray.length() > 0){
                                isDataAvailable.set(true);
                                Type type = new TypeToken<List<NewsHeadlineResponse>>(){
                                }.getType();
                                List<NewsHeadlineResponse> list = new Gson().fromJson(jsonArray.toString(), type);
                                setIsLoading(false);
                                getCallback().updateUI(list);
                            }else{
                                isDataAvailable.set(false);
                                setIsLoading(false);
                                getCallback().updateUIForFailedResponse("No news available");
                            }
                        }else{
                            setIsLoading(false);
                            isDataAvailable.set(false);
                            getCallback().updateUIForFailedResponse("No news available");
                        }
                    }
                }, throwable -> {
                    getCallback().handleError(throwable);
                    isDataAvailable.set(false);
                    setIsLoading(false);
                })
        );
    }
}
