package one.weex.you.com.myweexdemoone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class ListActivity extends AppCompatActivity {

    private List<Position> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);
        mDatas = new ArrayList<Position>();
        initData(mDatas);
        ListAdapter adapter = new ListAdapter(mDatas);
        adapter.setContext(this);
        RecyclerView list = (RecyclerView) this.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData(List<Position> datas) {
        for (int i = 0; i < 5; i++) {
            if(i == 4){
                Position pos = new Position();
                pos.setTYPE(2);
                pos.setName("我的姓名是"+"  "+2);
                datas.add(pos);
            }else{
                Position pos = new Position();
                pos.setTYPE(i);
                pos.setName("我的姓名是"+"  "+i);
                datas.add(pos);
            }
        }
    }

    public class ListAdapter extends GridLayoutAdapter<Position> {

        private Context mCtx;

        public ListAdapter(List<Position> list) {
            super(list);
        }

        public void setContext(Context context) {
            this.mCtx = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            if (viewType == 2) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.activity_main, parent, false);
                AViewHolder viewHolder = new AViewHolder(view);
                return viewHolder;
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item, parent, false);
                ListViewHolder viewHolder = new ListViewHolder(view);
                return viewHolder;
            }
//            View view = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.list_item, parent, false);
//            ListViewHolder viewHolder = new ListViewHolder(view);
//            return viewHolder;
        }

        @Override
        protected void onBindHeaderView(View headerView) {

        }

        @Override
        protected void onBindItemView(RecyclerView.ViewHolder holder, Position item, int position) {
            if (holder == null) {
                return;
            }
            if (holder instanceof ListViewHolder) {
                ListViewHolder viewHolder = (ListViewHolder) holder;
                viewHolder.setView(item);
            } else if (holder instanceof AViewHolder) {
                AViewHolder aViewHolder = (AViewHolder) holder;
                aViewHolder.setView(item, mCtx);
            }

        }

        public class ListViewHolder extends RecyclerView.ViewHolder {

            private TextView mItem;

            public ListViewHolder(View itemView) {
                super(itemView);
                mItem = (TextView) itemView.findViewById(R.id.text_view);
            }

            public void setView(Position item) {
                if (item == null) {
                    return;
                }
                mItem.setText(item.getName());
            }
        }

        public class AViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout mViewGroup;

            private String DEFAULT_IP = "your_current_IP";
            private String CURRENT_IP = DEFAULT_IP; // your_current_IP
            private String WEEX_INDEX_URL = "http://" + CURRENT_IP + ":12580/examples/build/index.js";

            public AViewHolder(View itemView) {
                super(itemView);
                mViewGroup = (LinearLayout) itemView.findViewById(R.id.viewGroup);
            }

            public void setView(Position item, Context context) {
                String mPackageName = ListActivity.this.getPackageName();
                WXSDKInstance mInstance = new WXSDKInstance(context);
                mInstance.registerRenderListener(mListener);
                renderPage(context,mInstance, mPackageName, WXFileUtils.loadFileContent("index.js", context), WEEX_INDEX_URL, null);
            }

            public IWXRenderListener mListener = new IWXRenderListener() {

                @Override
                public void onViewCreated(WXSDKInstance instance, View view) {
                    if(view == null){
                        return;
                    }
                    mViewGroup.addView(view);
                }

                @Override
                public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

                }

                @Override
                public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

                }

                @Override
                public void onException(WXSDKInstance instance, String errCode, String msg) {

                }
            };

            protected void renderPage(Context context,WXSDKInstance mInstance, String packageName, String template, String source, String jsonInitData) {
                Map<String, Object> options = new HashMap<>();
                options.put(WXSDKInstance.BUNDLE_URL, source);
                mInstance.render(
                        packageName,
                        template,
                        options,
                        jsonInitData,
                        WXViewUtils.getScreenWidth(context),
                        WXViewUtils.getScreenHeight(context),
                        WXRenderStrategy.APPEND_ASYNC);
            }
        }
    }

}
