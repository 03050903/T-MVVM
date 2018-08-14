package com.code.mvvm.core.view.material;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.basiclibrary.base.BaseFragment;
import com.code.mvvm.base.BaseViewPagerFragment;
import com.code.mvvm.core.data.pojo.material.MaterialTypeVo;
import com.code.mvvm.core.viewmodel.MaterialViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：zhangtianqiu on 18/7/2 14:24
 */
public class MaterialFragment extends BaseViewPagerFragment<MaterialViewModel> {
    private List<MaterialTypeVo.TypelistEntity> titleName;

    public static MaterialFragment newInstance() {
        return new MaterialFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        titleName = new ArrayList<>();
        getTabData();
    }

    @Override
    protected void dataObserver() {
        mViewModel.getMaterialType().observe(this, new Observer<MaterialTypeVo>() {
            @Override
            public void onChanged(@Nullable MaterialTypeVo materialTypeVo) {
                if (materialTypeVo != null) {
                    setData(materialTypeVo);
                }
            }
        });
    }

    @Override
    protected MaterialViewModel createViewModelProviders() {
        return VMProviders(this, MaterialViewModel.class);
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getTabData();
    }

    @Override
    protected String[] createPageTitle() {
        return mArrTitles;
    }

    @Override
    protected List<BaseFragment> createFragments() {
        return mFragments;
    }


    private void getTabData() {
        mViewModel.getMaterialTypeData();
    }


    public void setData(MaterialTypeVo lessonTypeObject) {
        mArrTitles = new String[lessonTypeObject.data.typelist.size() + 1];
        titleName.clear();
        MaterialTypeVo.TypelistEntity dataBean = new MaterialTypeVo.TypelistEntity();
        dataBean.name = "专题";
        mArrTitles[0] = "专题";
        titleName.add(dataBean);
        for (int j = 0; j < lessonTypeObject.data.typelist.size(); j++) {
            titleName.add(lessonTypeObject.data.typelist.get(j));
            mArrTitles[j + 1] = (lessonTypeObject.data.typelist.get(j).name);
        }
        initFragment();
        setAdapter();
    }

    private void initFragment() {
        for (int i = 0; i < titleName.size(); i++) {
            if (i == 0) {
                MaterialRecommendFragment materialRecommendFragment = MaterialRecommendFragment.newInstance();
                mFragments.add(materialRecommendFragment);
            } else {
                MaterialListFragment materialListFragment = MaterialListFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("mlevel", titleName.get(i).subid);
                materialListFragment.setArguments(bundle);
                mFragments.add(materialListFragment);
            }

        }
    }
}
