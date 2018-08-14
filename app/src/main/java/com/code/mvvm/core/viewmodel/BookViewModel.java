package com.code.mvvm.core.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.code.mvvm.callback.OnResultCallBack;
import com.code.mvvm.config.Constants;
import com.code.mvvm.core.data.pojo.book.BookListVo;
import com.code.mvvm.core.data.pojo.book.BookTypeVo;
import com.code.mvvm.core.data.source.BookRepository;

/**
 * @author：zhangtianqiu on 18/7/28 13:32
 */
public class BookViewModel extends BaseViewModel<BookRepository> {

    private MutableLiveData<BookListVo> mBookData = new MutableLiveData<>();
    private MutableLiveData<BookTypeVo> mBookType = new MutableLiveData<>();

    public BookViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BookListVo> getBookList() {
        if (mBookData == null) {
            mBookData = new MutableLiveData<>();
        }
        return mBookData;
    }

    public LiveData<BookTypeVo> getBookType() {
        if (mBookType == null) {
            mBookType = new MutableLiveData<>();
        }
        return mBookType;
    }

    public void getBookList(String f_catalog_id, String lastId, String rn) {
        mRepository.loadBookList(f_catalog_id, lastId, rn, new OnResultCallBack<BookListVo>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(Constants.NET_WORK_STATE);
            }

            @Override
            public void onNext(BookListVo bookListObject) {
                mBookData.postValue(bookListObject);
                loadState.postValue(Constants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {
                loadState.postValue(Constants.ERROR_STATE);
            }
        });
    }

    public void getBookTypeData() {
        mRepository.loadBookTypeData(new OnResultCallBack<BookTypeVo>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(Constants.NET_WORK_STATE);
            }

            @Override
            public void onNext(BookTypeVo bookClassObject) {
                mBookType.postValue(bookClassObject);
                loadState.postValue(Constants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {
                loadState.postValue(Constants.ERROR_STATE);
            }
        });
    }

}
