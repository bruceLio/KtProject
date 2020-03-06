package com.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel<T> extends ViewModel {
    MutableLiveData<T> data = new MutableLiveData<>();

    protected void setData(T t) {
        data.postValue(t);
    }

    protected T getData() {
        return data.getValue();
    }

}
