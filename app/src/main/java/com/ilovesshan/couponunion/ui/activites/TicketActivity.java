package com.ilovesshan.couponunion.ui.activites;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseActivity;
import com.ilovesshan.couponunion.entity.Ticket;
import com.ilovesshan.couponunion.interfaces.view.ITicketViewCallback;
import com.ilovesshan.couponunion.presenter.TicketPresenter;
import com.ilovesshan.couponunion.utils.AppUtil;
import com.ilovesshan.couponunion.utils.PresenterManager;
import com.ilovesshan.couponunion.utils.ToastUtil;
import com.ilovesshan.couponunion.utils.UILoader;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class TicketActivity extends BaseActivity implements ITicketViewCallback {

    private TicketPresenter ticketPresenter;
    private String cover;
    private String title;
    private String clickUrl;
    private Ticket ticket;
    private boolean installedTaoApp = false;
    private UILoader uiLoader;

    @BindView(R.id.ticket_container)
    public FrameLayout ticketContainer;


    private ImageView goodsCover;
    private TextView goodsTicketCode;
    private TextView goodsTicketReceive;


    @OnClick(R.id.nav_back)
    public void onNavBackClick(View view) {
        finish();
    }


    @Override
    protected int getActivityResourcesId() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initViewAndBindEvent() {
        uiLoader = new UILoader(this) {
            @Override
            protected View createSuccessView() {
                final View view = LayoutInflater.from(TicketActivity.this).inflate(R.layout.activity_ticket_detail, ticketContainer, false);
                goodsCover = view.findViewById(R.id.goods_cover);
                goodsTicketCode = view.findViewById(R.id.goods_ticket_code);
                goodsTicketReceive = view.findViewById(R.id.goods_ticket_receive);

                goodsTicketReceive.setOnClickListener(v -> {
                    // packageName=com.taobao.taobao, activityName=com.taobao.tao.welcome.Welcome, moduleName=, uid=10227
                    // onEvent, packageName=com.taobao.taobao, activityName=com.taobao.tao.TBMainActivity, moduleName=, uid=10227

                    //????????? ??????????????????
                    final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    final ClipData tb_ticket = ClipData.newPlainText("yf_tb_ticket", goodsTicketCode.getText().toString());
                    clipboardManager.setPrimaryClip(tb_ticket);

                    if (installedTaoApp) {
                        AppUtil.jumpToApp(TicketActivity.this, "com.taobao.taobao", "com.taobao.tao.TBMainActivity");
                    } else {
                        ToastUtil.showMessage("????????????????????????????????????????????????????????????!!");
                    }
                });
                return view;
            }
        };

        // ??????????????????
        uiLoader.setOnRetryLoadClickListener(() -> {
            ticketPresenter.getTickCode(title, clickUrl);
        });

        // ??????uiLoader????????????
        ticketContainer.removeAllViews();
        ticketContainer.addView(uiLoader);

        // ?????????????????????????????????
        installedTaoApp = AppUtil.checkIsInstallApp(this, "com.taobao.taobao");

        final Intent intent = getIntent();
        title = intent.getStringExtra("title");
        clickUrl = intent.getStringExtra("clickUrl");
        cover = intent.getStringExtra("cover");

        ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.registerViewCallBack(this);

        // ?????????????????? ???????????????
        ticketPresenter.getTickCode(title, clickUrl);

        // ????????????
        Glide.with(this).load(cover).into(goodsCover);

        // ??????????????????
        goodsTicketReceive.setText(installedTaoApp ? "??????????????????" : "???????????????");

    }


    @Override
    public void onTickCodeResult(Ticket ticket) {
        this.ticket = ticket;
        final String ticketCode = this.ticket.getData().getTbk_tpwd_create_response().getData().getModel();
        goodsTicketCode.setText(ticketCode);
    }

    @Override
    public void onError() {
        if (uiLoader != null) {
            uiLoader.updateUILoaderState(UILoader.UILoaderState.ERROR);
        }
    }

    @Override
    public void onEmpty() {
        if (uiLoader != null) {
            uiLoader.updateUILoaderState(UILoader.UILoaderState.EMPTY);
        }
    }

    @Override
    public void onLoading() {
        if (uiLoader != null) {
            uiLoader.updateUILoaderState(UILoader.UILoaderState.LOADING);
        }
    }

    @Override
    public void onSuccess() {
        if (uiLoader != null) {
            uiLoader.updateUILoaderState(UILoader.UILoaderState.SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticketPresenter.unRegisterViewCallBack(this);
    }
}