package one.weex.you.com.myweexdemoone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.view.WXFrameLayout;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXViewUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用weex框架动态加载js
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout mViewGroup;
    private WXSDKInstance mInstance;
    private String mPackageName;

    private static final String DEFAULT_IP = "your_current_IP";
    private static String CURRENT_IP = DEFAULT_IP; // your_current_IP
    private static final String WEEX_INDEX_URL = "http://" + CURRENT_IP + ":12580/examples/build/index.js";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        mPackageName = this.getPackageName();
        mInstance = new WXSDKInstance(this);//初始化weex SDK
        mInstance.registerRenderListener(mListener);//注册加载监听器
        //加载本地js
        renderPage(mInstance, mPackageName, WXFileUtils.loadFileContent("index.js", this), WEEX_INDEX_URL, null);
    }

    /**
     * 加载js监听器
     */
    public IWXRenderListener mListener = new IWXRenderListener() {

        /**
         * 初始化view
         * @param instance
         * @param view
         * 暂时只发现了这个view是一个wxFrameLayout,是否会出现别的View，暂时没发现
         */
        @Override
        public void onViewCreated(WXSDKInstance instance, View view) {
            //添加这个view到布局中
            mViewGroup.addView(view);
        }

        /**
         * 加载成功
         * @param instance
         * @param width
         * @param height
         */
        @Override
        public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

        }

        /**
         * 刷新成功
         * @param instance
         * @param width
         * @param height
         */
        @Override
        public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

        }

        /**
         * 加载失败
         * @param instance
         * @param errCode
         * 异常code码
         * @param msg
         * 异常信息
         */
        @Override
        public void onException(WXSDKInstance instance, String errCode, String msg) {

        }
    };

    /**
     * 加载js页面
     * @param mInstance
     * @param packageName
     * @param template
     * @param source
     * @param jsonInitData
     */
    protected void renderPage(WXSDKInstance mInstance, String packageName, String template, String source, String jsonInitData) {
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, source);
        mInstance.render(
                packageName,
                template,
                options,
                jsonInitData,
                WXViewUtils.getScreenWidth(this),
                WXViewUtils.getScreenHeight(this),
                WXRenderStrategy.APPEND_ASYNC);
    }
}
