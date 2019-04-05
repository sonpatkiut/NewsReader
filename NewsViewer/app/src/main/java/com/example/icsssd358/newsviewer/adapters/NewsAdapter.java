package com.example.icsssd358.newsviewer.adapters;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.databinding.library.baseAdapters.BR;

import com.example.icsssd358.newsviewer.Utils.EventBus;
import com.example.icsssd358.newsviewer.databinding.NewsListRowBinding;
import com.example.icsssd358.newsviewer.model.NewsHeadlineResponse;
import com.example.icsssd358.newsviewer.viewholders.BaseViewHolder;
import com.example.icsssd358.newsviewer.viewmodel.BaseViewModel;
import com.example.icsssd358.newsviewer.viewmodel.NewsItemViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    EventBus eventBus;
    private List<NewsHeadlineResponse> list = new ArrayList<>();

    @Inject
    public NewsAdapter(EventBus eventBus){
        this.eventBus = eventBus;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewDataBinding mViewDataBinding = NewsListRowBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new NewsViewHolder<>(mViewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<NewsHeadlineResponse> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class NewsViewHolder<T extends ViewDataBinding, V extends BaseViewModel> extends BaseViewHolder{
        private ViewDataBinding mBinding;
        NewsItemViewModel mNewsItemViewModel;

        public NewsViewHolder(T binding){
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mNewsItemViewModel = new NewsItemViewModel(list.get(position), eventBus);
            mBinding.setVariable(BR.viewModel, mNewsItemViewModel);
            mBinding.executePendingBindings();
        }
    }
}
