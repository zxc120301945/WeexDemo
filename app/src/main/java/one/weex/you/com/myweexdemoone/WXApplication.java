package one.weex.you.com.myweexdemoone;

import android.app.Application;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class WXApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化weex配置文件
        WXEnvironment.addCustomOptions("appName","TBSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        //设置图片adapter 如果不设置图片是显示不出来的，并且会报异常
                        .setImgAdapter(new ImageAdapter())
                        .setDebugAdapter(new PlayDebugAdapter())
                        .build()
        );

        try {
//            Fresco.initialize(this);
//            WXSDKEngine.registerComponent("richtext", RichText.class);
            //注册模块，用于和js互动，传参
            WXSDKEngine.registerModule("render", RenderModule.class);
            WXSDKEngine.registerModule("event", WXEventModule.class);

            WXSDKEngine.registerModule("myModule", MyModule.class);

        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
