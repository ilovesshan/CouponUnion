package com.ilovesshan.couponunion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.ui.fragment.HomeFragment;
import com.ilovesshan.couponunion.ui.fragment.ProfileFragment;
import com.ilovesshan.couponunion.ui.fragment.RecommendFragment;
import com.ilovesshan.couponunion.ui.fragment.SearchFragment;
import com.ilovesshan.couponunion.ui.fragment.SpecialFragment;

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

    /**
     * 上一个显示的Fragment
     */
    private BaseFragment leastFragment;

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
        switchFragment(homeFragment);
        // fragmentManager.beginTransaction().add(R.id.main_container, homeFragment).commit();

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

    /**
     * 通过 hide 和 show 来控制fragment的显示和隐藏效果
     *
     * @param baseFragment baseFragment
     */
    private void switchFragment(BaseFragment baseFragment) {
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!baseFragment.isAdded()) {
            fragmentTransaction.add(R.id.main_container, baseFragment);
        } else {
            fragmentTransaction.show(baseFragment);
        }
        if (this.leastFragment != null) {
            fragmentTransaction.hide(this.leastFragment);
        }
        this.leastFragment = baseFragment;
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}