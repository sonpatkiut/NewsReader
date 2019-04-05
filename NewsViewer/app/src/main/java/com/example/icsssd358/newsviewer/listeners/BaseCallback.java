package com.example.icsssd358.newsviewer.listeners;

public interface BaseCallback {
    void makeRequest();
    void updateUIForFailedResponse(String msg);
    void handleError(Throwable throwable);
    void handleNetworkError();
    void navigateToNextActivity(Object object);
}
