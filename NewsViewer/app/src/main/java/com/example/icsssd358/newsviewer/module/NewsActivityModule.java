package com.example.icsssd358.newsviewer.module;

import com.example.icsssd358.newsviewer.Utils.EventBus;
import com.example.icsssd358.newsviewer.adapters.NewsAdapter;
import com.example.icsssd358.newsviewer.viewmodel.NewsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsActivityModule {

    @Provides
    NewsViewModel provideNewsViewModel(EventBus eventBus){
        return new NewsViewModel(eventBus);
    }

    @Provides
    NewsAdapter provideNewsAdapter(EventBus eventBus){
        return new NewsAdapter(eventBus);
    }
}
