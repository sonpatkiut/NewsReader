package com.example.icsssd358.newsviewer.module;


import com.example.icsssd358.newsviewer.Utils.EventBus;
import com.example.icsssd358.newsviewer.viewmodel.WebViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class WebActivityModule {

    @Provides
    WebViewModel provideWebViewModel(EventBus eventBus){
        return  new WebViewModel(eventBus);

    }
}
