package com.android.mb.movie.fragment;

import android.view.View;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.TagAdapter;
import com.android.mb.movie.base.BaseFragment;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cgy on 16/7/18.
 */
public class TagFragment extends BaseFragment  {
    private FlowTagLayout mTagHot;
    private TagAdapter<String> mHotAdapter;

    @Override
    protected int getLayoutId() {
        return  R.layout.frg_tag;
    }

    @Override
    protected void bindViews(View view) {
        mTagHot = (FlowTagLayout) mRootView.findViewById(R.id.tagLayout);
        mHotAdapter = new TagAdapter<>(getActivity());
        mTagHot.setAdapter(mHotAdapter);
    }

    @Override
    protected void processLogic() {
        initHotData();
    }

    @Override
    protected void setListener() {
    }

    private void initHotData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("红色");
        dataSource.add("黑色");
        dataSource.add("花边色");
        dataSource.add("深蓝色");
        dataSource.add("白色");
        dataSource.add("玫瑰红色");
        dataSource.add("紫黑紫兰色");
        dataSource.add("葡萄红色");
        dataSource.add("屎黄色");
        dataSource.add("绿色");
        dataSource.add("彩虹色");
        dataSource.add("牡丹色");
        mHotAdapter.onlyAddAll(dataSource);
    }

}
