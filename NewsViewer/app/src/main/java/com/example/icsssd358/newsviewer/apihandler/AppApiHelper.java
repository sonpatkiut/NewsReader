package com.example.icsssd358.newsviewer.apihandler;

import com.androidnetworking.common.Priority;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;

public class AppApiHelper implements ApiHelper{


    @Override
    public Observable<JSONObject> getHeadLines() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ROOT_URL + ApiEndPoint.API_KEY)
                .setPriority(Priority.LOW)
                .build()
                .getJSONObjectObservable();
    }

    public OkHttpClient getOkHttpClient(){
        try{
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);
            return builder.build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
