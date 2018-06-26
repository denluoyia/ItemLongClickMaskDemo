package com.itemlongclickmaskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.itemlongclickmaskdemo.widget.ScrollCallbackRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemListAdapter.OnItemClickCallback, ItemLongClickMaskHelper.ItemMaskClickListener, ScrollCallbackRecyclerView.ScrollCallbackListener {

    private ScrollCallbackRecyclerView mRecyclerView;
    private ItemListAdapter mAdapter;
    private List<String> mDataList;
    private ItemLongClickMaskHelper mMaskHelper;
    private int mItemLongClickPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setScrollCallbackListener(this); //滑动遮罩消失
        mMaskHelper = new ItemLongClickMaskHelper(this);
        mMaskHelper.setMaskItemListener(this);
        mAdapter = new ItemListAdapter();
        mAdapter.setOnItemClickCallback(this);
        initData();
        mAdapter.setData(mDataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        mDataList = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            mDataList.add("Item -- " + i);
        }
    }

    @Override
    public void itemLongClick(View view, int position) {
        mItemLongClickPosition = position;
        mMaskHelper.setRootFrameLayout((FrameLayout)view);
    }

    @Override
    public void itemClick(View view, int position) {
        mMaskHelper.dismissMaskLayout();
    }
    @Override
    public void scrollChanged() {
        mMaskHelper.dismissMaskLayout();
    }

    @Override
    public void findSame() { //长按找相似回调
        String item = mAdapter.getItem(mItemLongClickPosition);
        Toast.makeText(this, "你点击了-找相似--" + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void collect() {//长按收藏回调
        String item = mAdapter.getItem(mItemLongClickPosition);
        Toast.makeText(this, "你点击了-收藏--" + item, Toast.LENGTH_SHORT).show();
    }


}
