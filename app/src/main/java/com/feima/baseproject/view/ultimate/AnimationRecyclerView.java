package com.feima.baseproject.view.ultimate;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feima.baseproject.R;
import com.feima.baseproject.listener.IOnRefreshListener;
import com.feima.baseproject.util.tool.LogUtil;
import com.feima.baseproject.view.widget.pullview.AnimationRefreshLayout;
import com.feima.baseproject.view.widget.spin.TransProgressWheel;


public class AnimationRecyclerView extends FrameLayout{
    public RecyclerView mRecyclerView;
    public AnimationRefreshLayout refreshLayout;
    private ImageView arrowImg;
    private IOnRefreshListener onRefreshListener;
    private int lastVisibleItemPosition;
    protected RecyclerView.OnScrollListener mOnScrollListener;
    private boolean isLoadingMore = false;
    private boolean isRefreshing = false;

    private int currentScrollState = 0;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected boolean mClipToPadding;
    private UltimateViewAdapter mAdapter;

    private boolean enableLoadingMore = false;
    private boolean endLoadMore = false;
    private boolean showArrow = true;
    private TransProgressWheel progressWheel;
    private LinearLayout endLayout;
    private int lastVisibleItemPositionLimit = 17;

    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    public AnimationRecyclerView(Context context) {
        super(context);
        initViews();
    }

    public AnimationRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initViews();
    }

    public AnimationRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initViews();
    }

    public static enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    private void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.base_ultimate_recycler_view_layout, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_ultimate_view_layout_recyclerview);
        arrowImg = (ImageView)view.findViewById(R.id.base_ultimate_view_layout_arrow);
        refreshLayout = (AnimationRefreshLayout) view.findViewById(R.id.base_ultimate_view_layout_refreshlayout);
        if (mRecyclerView != null) {
            mRecyclerView.setClipToPadding(mClipToPadding);
            if (mPadding != -0.0f) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
        }
        arrowImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mRecyclerView.smoothScrollToPosition(0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        setDefaultScrollListener();
    }

    void setDefaultScrollListener() {
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if(lastVisibleItemPosition>lastVisibleItemPositionLimit&&showArrow){
                        LogUtil.e("showArrow= " + showArrow);
                        showArrowImage();
                    }else {
                        hideArrowImage();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        mRecyclerView.setOnScrollListener(mOnScrollListener);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UltimateRecyclerview);
        try {
            mPadding = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPadding, -0.0f);
            mPaddingTop = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingTop, 0.0f);
            mPaddingBottom = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingBottom, 0.0f);
            mPaddingLeft = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingLeft, 0.0f);
            mPaddingRight = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingRight, 0.0f);
            mClipToPadding = typedArray.getBoolean(R.styleable.UltimateRecyclerview_recyclerviewClipToPadding, false);
        } finally {
            typedArray.recycle();
        }
    }

    public void enableLoadmore(boolean enable) {
        enableLoadingMore = enable;
        if (mAdapter!=null&&mAdapter.getCustomLoadMoreView() == null){
            View loadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.base_ultimate_load_more, null);
            progressWheel = (TransProgressWheel)loadMoreView.findViewById(R.id.base_ultimate_load_more_progress_wheel);
            endLayout = (LinearLayout)loadMoreView.findViewById(R.id.base_ultimate_load_more_end);
            endLayout.setVisibility(View.GONE);
            progressWheel.setVisibility(VISIBLE);
            mAdapter.setCustomLoadMoreView(loadMoreView);
        }

        if (enable){
            mOnScrollListener = new RecyclerView.OnScrollListener() {
                private int[] lastPositions;
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                    if (layoutManagerType == null) {
                        if (layoutManager instanceof LinearLayoutManager) {
                            layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                        } else if (layoutManager instanceof GridLayoutManager) {
                            layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                        } else {
                            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                        }
                    }

                    switch (layoutManagerType) {
                        case LINEAR:
                            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                            break;
                        case GRID:
                            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                            break;
                        case STAGGERED_GRID:
                            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                            if (lastPositions == null)
                                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];

                            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                            lastVisibleItemPosition = findMax(lastPositions);
                            break;
                    }

                    if(lastVisibleItemPosition>lastVisibleItemPositionLimit&&showArrow){
                        showArrowImage();
                    }else {
                        hideArrowImage();
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    currentScrollState = newState;
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                            (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
                        if (onRefreshListener != null) {
                            if (enableLoadingMore&&canRefresh()){
                                isLoadingMore = true;
                                mAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
                                onRefreshListener.onLoadMore();
                            }
                        }
                    }
                }
            };
            mRecyclerView.setOnScrollListener(mOnScrollListener);
        }else {
            setDefaultScrollListener();

        }
        if (mAdapter!=null)
             mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
    }

    public void enableRefresh(boolean canRefresh) {
        refreshLayout.setCanRefresh(canRefresh);
        refreshLayout.setCanLoad(false);
    }

    public void setOnRefreshListener(IOnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        refreshLayout.setOnRefreshListener(iOnRefreshListener);
    }

    private IOnRefreshListener iOnRefreshListener = new IOnRefreshListener() {
        @Override
        public void onRefresh() {
            if (onRefreshListener!=null){
                isRefreshing = true;
                onRefreshListener.onRefresh();
            }
        }

        @Override
        public void onLoadMore() {
            if (onRefreshListener!=null){
                isLoadingMore = true;
                onRefreshListener.onLoadMore();
            }
        }
    };

    /**
     * Set the layout manager to the recycler
     *
     * @param manager
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener customOnScrollListener) {
        mRecyclerView.setOnScrollListener(customOnScrollListener);
    }

    public void addItemDividerDecoration(Context context) {
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecyclerView.addItemDecoration(itemDecoration, index);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
    }

    /**
     * load sync finish need to use this method
     */
    public void refreshFinish(){
        try {
            if (isLoadingMore){
                if (mAdapter!=null&&mAdapter.getCustomLoadMoreView()!=null){
                    mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                }
            }
            if (isRefreshing){
                refreshLayout.refreshFinish(AnimationRefreshLayout.SUCCEED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        LogUtil.e("isRefreshing= "+isRefreshing);
        isRefreshing = false;
        isLoadingMore = false;
    }


    /**
     * swiperefresh start need to use this method
     */
    public boolean refreshStart(){
        if (canRefresh()){
            mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
            isRefreshing = true;
            isLoadingMore = false;
            return true;
        }else{
            refreshFinish();
            isRefreshing = false;
            return false;
        }

    }

    public  boolean canRefresh(){
        boolean refresh = false;
        if (isRefreshing||isLoadingMore){
            refresh = false;
        }else {
            refresh = true;
        }
        return refresh;
    }

    public void endFinish(boolean isEnd){
        try {
            endLoadMore = isEnd;
            if (isEnd){
                enableLoadingMore = false;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
                        if (endLayout!=null&&progressWheel!=null){
                            endLayout.setVisibility(View.VISIBLE);
                            progressWheel.setVisibility(View.GONE);
                        }
                    }
                }, 500);
            }else{
                enableLoadingMore = true;
                mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                if (endLayout!=null&&progressWheel!=null){
                    endLayout.setVisibility(View.GONE);
                    progressWheel.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void setAdapter(UltimateViewAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                update();
            }

            @Override
            public void onChanged() {
                super.onChanged();
                update();
            }

            private void update() {
                isLoadingMore = false;
//
            }

        });
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                update();
            }

            @Override
            public void onChanged() {
                super.onChanged();
                update();
            }

            private void update() {
                isLoadingMore = false;
//
            }

        });
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    public void setHeadView(View headView){
        if (mAdapter!=null){
            mAdapter.setCustomHeaderView(headView);
        }
    }

    public void setFootView(View footView){
        if (mAdapter!=null){
            mAdapter.setCustomLoadMoreView(footView);
        }
    }

    public void showArrowImage(){
        arrowImg.setVisibility(VISIBLE);
    }

    public void hideArrowImage(){
        arrowImg.setVisibility(GONE);
    }

    public void setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
    }
}
