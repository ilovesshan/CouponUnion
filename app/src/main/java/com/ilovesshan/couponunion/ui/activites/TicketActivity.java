package com.ilovesshan.couponunion.ui.activites;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseActivity;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.entity.Ticket;
import com.ilovesshan.couponunion.interfaces.view.ITicketViewCallback;
import com.ilovesshan.couponunion.presenter.TicketPresenter;
import com.ilovesshan.couponunion.utils.AppUtil;
import com.ilovesshan.couponunion.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class TicketActivity extends BaseActivity implements ITicketViewCallback {

    private TicketPresenter ticketPresenter;
    private String cover;
    private String title;
    private String clickUrl;
    private Ticket ticket;
    private BaseFragment.ViewState viewState = BaseFragment.ViewState.NONE;
    private boolean installedTaoApp = false;

    @BindView(R.id.goods_cover)
    public ImageView goodsCover;

    @BindView(R.id.goods_ticket_code)
    public TextView goodsTicketCode;

    @BindView(R.id.goods_ticket_receive)
    public TextView goodsTicketReceive;

    @OnClick(R.id.nav_back)
    public void onNavBackClick(View view) {
        finish();
    }

    @OnClick(R.id.goods_ticket_receive)
    public void onGoodsTicketReceiveClick(View view) {
        // packageName=com.taobao.taobao, activityName=com.taobao.tao.welcome.Welcome, moduleName=, uid=10227
        // onEvent, packageName=com.taobao.taobao, activityName=com.taobao.tao.TBMainActivity, moduleName=, uid=10227

        //将口令 复制到剪贴板
        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData tb_ticket = ClipData.newPlainText("yf_tb_ticket", goodsTicketCode.getText().toString());
        clipboardManager.setPrimaryClip(tb_ticket);

        if (installedTaoApp) {
            AppUtil.jumpToApp(this, "com.taobao.taobao", "com.taobao.tao.TBMainActivity");
        } else {
            ToastUtil.show("复制成功，您也可以打开淘宝粘贴领券优惠券!!");
        }
    }

    @Override
    protected int getActivityResourcesId() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initViewAndBindEvent() {

        // 检查是否安装了他淘宝应用
        installedTaoApp = AppUtil.checkIsInstallApp(this, "com.taobao.taobao");

        final Intent intent = getIntent();
        title = intent.getStringExtra("title");
        clickUrl = intent.getStringExtra("clickUrl");
        cover = intent.getStringExtra("cover");

        ticketPresenter = new TicketPresenter();
        ticketPresenter.getTickCode(title, clickUrl);
        ticketPresenter.registerViewCallBack(this);

        // 商品图片
        Glide.with(this).load(cover).into(goodsCover);

        // 领券按钮名称
        goodsTicketReceive.setText(installedTaoApp ? "打开淘宝领券" : "复制淘口令");

    }


    @Override
    public void onTickCodeResult(Ticket ticket) {
        this.ticket = ticket;
        final String ticketCode = this.ticket.getData().getTbk_tpwd_create_response().getData().getModel();
        goodsTicketCode.setText(ticketCode);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticketPresenter.unRegisterViewCallBack(this);
    }
}