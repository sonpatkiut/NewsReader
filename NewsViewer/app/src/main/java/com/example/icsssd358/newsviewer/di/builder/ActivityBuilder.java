package com.example.icsssd358.newsviewer.di.builder;

import com.example.icsssd358.newsviewer.activities.NewsActivity;
import com.example.icsssd358.newsviewer.activities.WebActivity;
import com.example.icsssd358.newsviewer.module.NewsActivityModule;
import com.example.icsssd358.newsviewer.module.WebActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder  {

    @ContributesAndroidInjector(modules = {NewsActivityModule.class})
    abstract NewsActivity bindNewsActivity();

    @ContributesAndroidInjector(modules = {WebActivityModule.class})
    abstract WebActivity bindWebActivity();
}
