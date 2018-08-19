package com.code.mvvm.core.view.followdraw;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.code.mvvm.base.BaseListFragment;
import com.code.mvvm.core.data.pojo.followdraw.FollowDrawRecommendVo;
import com.code.mvvm.core.viewmodel.FollowDrawViewModel;
import com.code.mvvm.util.AdapterPool;
import com.trecyclerview.multitype.MultiTypeAdapter;

/**
 * @author：zhangtianqiu on 18/7/2 14:40
 */
public class FollowDrawListFragment extends BaseListFragment<FollowDrawViewModel> {
    private String typeId;

    public static FollowDrawListFragment newInstance() {
        return new FollowDrawListFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (getArguments()!=null) {
            typeId = getArguments().getString("type_id");
        }
    }

    @Override
    protected void dataObserver() {
        mViewModel.geFollowDrawList().observe(this, new Observer<FollowDrawRecommendVo>() {
            @Override
            public void onChanged(@Nullable FollowDrawRecommendVo followDrawRecommendObject) {
                if (followDrawRecommendObject == null) {
                    return;
                }
                lastId = followDrawRecommendObject.
                        data.get(followDrawRecommendObject.data.size() - 1).lessonid;
                setData(followDrawRecommendObject.data);
            }
        });
    }


    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected MultiTypeAdapter createAdapter() {
        return AdapterPool.newInstance().getFollowAdapter(getActivity());
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getRemoteData();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getRemoteData();
    }

    @Override
    protected void onRefreshAction() {
        super.onRefreshAction();
        getRemoteData();
    }

    @Override
    protected void getRemoteData() {
        mViewModel.getFollowDrawList(typeId, lastId, "20");
    }
}
