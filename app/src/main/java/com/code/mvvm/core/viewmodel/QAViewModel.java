package com.code.mvvm.core.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.code.mvvm.callback.OnResultCallBack;
import com.code.mvvm.config.Constants;
import com.code.mvvm.core.data.pojo.qa.QaListVo;
import com.code.mvvm.core.data.source.QARepository;

/**
 * @author：zhangtianqiu on 18/8/2 10:53
 */
public class QAViewModel extends BaseViewModel<QARepository> {

    private MutableLiveData<QaListVo> mQAData;

    public QAViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<QaListVo> getQAList() {
        if (mQAData == null) {
            mQAData = new MutableLiveData<>();
        }
        return mQAData;
    }

    public void getQAList(String lastId, String rn) {
        mRepository.loadQAList(lastId, rn, new OnResultCallBack<QaListVo>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(Constants.NET_WORK_STATE);
            }

            @Override
            public void onNext(QaListVo articleObject) {
                mQAData.postValue(articleObject);
                loadState.postValue(Constants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {
                loadState.postValue(Constants.ERROR_STATE);
            }
        });
    }
}
