package com.fx.secondbar.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.application.FxApplication;
import com.fx.secondbar.util.ShareUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * function:微信回调结果
 * author: frj
 * modify date: 2018/9/27
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler
{

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, ShareUtils.WECHAT_APPID, false);
        api.registerApp(ShareUtils.WECHAT_APPID);
        //等待微信回调
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq)
    {

    }

    @Override
    public void onResp(BaseResp baseResp)
    {
        Bundle bundle = getIntent().getExtras();
        SendAuth.Resp resp = new SendAuth.Resp(bundle);//这个是登陆必须用到的，要用来获取code值的。
        // 而区分分享回调返回的结果和登陆回调返回的结果
        // 是用baseResp.getType()== ConstantsAPI.COMMAND_SENDAUTH
        // 和baseResp.getType()==ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX
        // 注意哈 这里的baseResp和resp是不同的哈。resp这个是登陆用到的
        // 获取到code之后，需要调用接口获取到access_token
        if (baseResp.errCode == BaseResp.ErrCode.ERR_OK)
        {
            //第三方登录
            if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH)
            {
//                String code = resp.token;
//                getAsync("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9433d9bf10c7a032&secret=183112ea358e6c84c5a37f11c03c2478&code=" + code + "&grant_type=authorization_code");
            } else if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX)
            {
                //发送分享成功广播
                FxApplication.shareSuccessBroadCast();
                ShowToast.showToast("分享成功");
                finish();
            }
        } else
        {
            WXEntryActivity.this.finish();
        }
    }
}
