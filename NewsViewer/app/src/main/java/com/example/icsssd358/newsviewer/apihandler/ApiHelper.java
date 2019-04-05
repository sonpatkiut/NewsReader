package com.example.icsssd358.newsviewer.apihandler;

import org.json.JSONObject;

import io.reactivex.Observable;

public interface ApiHelper {

    Observable<JSONObject> getHeadLines();
}
