package com.ilovesshan.couponunion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.ui.fragment.HomeFragment;
import com.ilovesshan.couponunion.ui.fragment.ProfileFragment;
import com.ilovesshan.couponunion.ui.fragment.RecommendFragment;
import com.ilovesshan.couponunion.ui.fragment.SearchFragment;
import com.ilovesshan.couponunion.ui.fragment.SpecialFragment;
import com.ilovesshan.couponunion.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    public FrameLayout mainContainer;

    @BindView(R.id.main_tab_bar)
    public BottomNavigationView mainTabBar;

    private BaseFragment homeFragment;
    private BaseFragment recommendFragment;
    private BaseFragment searchFragment;
    private BaseFragment specialFragment;
    private BaseFragment profileFragment;

    private FragmentManager fragmentManager;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initViewAndBindEvent();

    }

    private void initViewAndBindEvent() {
        homeFragment = new HomeFragment();
        recommendFragment = new RecommendFragment();
        searchFragment = new SearchFragment();
        specialFragment = new SpecialFragment();
        profileFragment = new ProfileFragment();

        fragmentManager = getSupportFragmentManager();

        // 默认展示首页
        fragmentManager.beginTransaction().add(R.id.main_container, homeFragment).commit();

        mainTabBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        switchFragment(homeFragment);
                        break;
                    case R.id.recommend:
                        switchFragment(recommendFragment);
                        break;
                    case R.id.search:
                        switchFragment(searchFragment);
                        break;
                    case R.id.special:
                        switchFragment(specialFragment);
                        break;
                    case R.id.profile:
                        switchFragment(profileFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void switchFragment(BaseFragment baseFragment) {
        fragmentManager.beginTransaction().replace(R.id.main_container, baseFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}