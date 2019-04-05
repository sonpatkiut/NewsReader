package com.example.icsssd358.newsviewer.listeners;

import com.example.icsssd358.newsviewer.model.NewsHeadlineResponse;

import java.util.List;

public interface NewsHeadLineCallBack extends BaseCallback {

    void updateUI(List<NewsHeadlineResponse> list);
}
