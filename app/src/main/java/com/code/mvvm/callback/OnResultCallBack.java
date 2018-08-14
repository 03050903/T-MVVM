package com.code.mvvm.callback;

/**
 * @author：zhangtianqiu on 18/7/31 12:33
 */
public interface OnResultCallBack<T> {

    void onNoNetWork();

    void onNext(T t);

    void onError(String e);
}
