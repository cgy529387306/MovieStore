package com.android.mb.movie.fragment;

import android.view.View;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.TagAdapter;
import com.android.mb.movie.base.BaseMvpFragment;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.presenter.TagPresenter;
import com.android.mb.movie.view.interfaces.ITagView;
import com.android.mb.movie.widget.taglayout.FlowTagLayout;

import java.util.List;


/**
 * Created by cgy on 16/7/18.
 */
public class TagFragment extends BaseMvpFragment<TagPresenter,ITagView> implements ITagView {
    private FlowTagLayout mTagHot;
    private TagAdapter<Tag> mHotAdapter;

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
        mPresenter.getTags();
    }

    @Override
    protected void setListener() {
    }


    @Override
    protected TagPresenter createPresenter() {
        return new TagPresenter();
    }

    @Override
    public void getSuccess(List<Tag> result) {
        mHotAdapter.onlyAddAll(result);
    }


}
