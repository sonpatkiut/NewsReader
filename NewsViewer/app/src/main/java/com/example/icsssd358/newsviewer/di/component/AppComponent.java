package com.example.icsssd358.newsviewer.di.component;

import android.app.Application;

import com.example.icsssd358.newsviewer.application.NewsApplication;
import com.example.icsssd358.newsviewer.di.builder.ActivityBuilder;
import com.example.icsssd358.newsviewer.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(NewsApplication application);
}
