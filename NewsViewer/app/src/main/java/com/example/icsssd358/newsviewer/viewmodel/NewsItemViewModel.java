package com.example.icsssd358.newsviewer.viewmodel;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import com.example.icsssd358.newsviewer.Utils.AppConstants;
import com.example.icsssd358.newsviewer.Utils.EventBus;
import com.example.icsssd358.newsviewer.model.NewsHeadlineResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsItemViewModel {

    EventBus eventBus;

    public ObservableField<String> text;

    public ObservableField<String> imageUrl;

    public ObservableField<String> imageBmp;

    NewsHeadlineResponse response;

    public NewsItemViewModel(NewsHeadlineResponse response, EventBus eventBus){
        this.eventBus = eventBus;
        text = new ObservableField<>(response.getTitle());
        this.response = response;
        imageUrl = new ObservableField<>(response.getUrlToImage());
        imageBmp = new ObservableField<>(response.getUrlToImage());
    }

    public void onItemClick(){
        AppConstants.isEventFiredByClick = true;
        eventBus.send(new Pair<>(AppConstants.NAVIGATE_NEXT, response));
    }

}
