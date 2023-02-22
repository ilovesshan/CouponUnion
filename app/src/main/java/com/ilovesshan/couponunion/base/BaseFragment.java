package com.ilovesshan.couponunion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ilovesshan.couponunion.R;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return loadSubView(inflater, container, savedInstanceState);
    }

    private View loadSubView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        int resourcesId = getResourceId();
        return inflater.inflate(resourcesId, container, false);
    }

    public abstract int getResourceId();
}
