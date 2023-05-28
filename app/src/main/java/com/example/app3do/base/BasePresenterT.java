package com.example.app3do.base;

public abstract class BasePresenterT<T>{
    private T view;
    public BasePresenterT(T view) {
        this.view = view;
    }

    protected boolean isViewAttached(){
        return view != null;
    }

    protected T getView(){
        return this.view;
    }
}
