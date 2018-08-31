package com.code.mvvm.core.view.qa;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.code.mvvm.base.BaseListFragment;
import com.code.mvvm.core.data.pojo.qa.QaListVo;
import com.code.mvvm.core.vm.QaViewModel;
import com.code.mvvm.util.AdapterPool;
import com.trecyclerview.multitype.MultiTypeAdapter;

/**
 * @author：tqzhang on 18/7/4 14:10
 */
public class QaListFragment extends BaseListFragment<QaViewModel> {

    public static QaListFragment newInstance() {
        return new QaListFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
    }

    @Override
    protected void dataObserver() {
        mViewModel.getQAList().observe(this, new Observer<QaListVo>() {
            @Override
            public void onChanged(@Nullable QaListVo qaListVo) {
                if (qaListVo == null) {
                    return;
                }
                lastId = qaListVo.data.get(qaListVo.data.size() - 1).newsid;
                setData(qaListVo.data);

            }
        });
    }


    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected MultiTypeAdapter createAdapter() {
        return AdapterPool.newInstance().getQaAdapter(getActivity());
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }

    public void getNetWorkData() {
        mViewModel.getQAList(lastId, "20");
    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mViewModel.getQAList(lastId, "20");
    }
}
