package com.ilovesshan.couponunion.ui.fragment;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseFragment;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public class SearchFragment extends BaseFragment {

    @Override
    public int getResourceId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViewAndBindEvent() {
        updateViewState(ViewState.SUCCESS);
    }
}