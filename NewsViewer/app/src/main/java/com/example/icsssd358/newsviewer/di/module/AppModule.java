package com.example.icsssd358.newsviewer.di.module;

import android.app.Application;
import android.content.Context;

import com.example.icsssd358.newsviewer.Utils.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return new EventBus();
    }
}
