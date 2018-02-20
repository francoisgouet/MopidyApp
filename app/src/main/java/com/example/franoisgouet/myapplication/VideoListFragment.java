package com.example.franoisgouet.myapplication;

import android.content.ClipData;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author françois GOUET
 */

public class VideoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    public MyAdapter mAdapter;
    // data objet hen want to retain
    private List<ItemData> mDataSet = new ArrayList<>();
    /*private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }*/

    // This method is only called Once for this framgent
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the view for recycleView
        View rootView = inflater.inflate(R.layout.container, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.re);

        // Set Linear Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(llm);

        // After the back-return to a newly VideoListFragment
        if (savedInstanceState == null) {
            //Set custom adapter as the recycler view
            mAdapter = new MyAdapter(mDataSet);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            Log.i("fragment", mDataSet.toString());
            //mDataSet
            if (mDataSet != null) {
                Log.i("fragment", mDataSet.toString());
            }

            Log.i("fragment", "savedInstanceNull");
        }
        // rotation changes for examples
        else{
            //Set custom adapter as the recycler view
            mRecyclerView.setAdapter(mAdapter);
            Log.i("fragment", "savedInstanceNotNull");
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // save RecyclerView state
        //savedInstanceState.putParcelable("data", (Parcelable) mDataSet);
        //Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        // save int
        savedInstanceState.putInt("bleu", 1);
        //savedInstanceState.putSerializable("list",(Serializable)mDataSet);
        Log.i("fragment", "onSaveInstance");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        //savedInstanceState.putInt("bleu", 1);
        //mDataSet = (List<ItemData>) savedInstanceState.getSerializable("data");
        //mAdapter = new MyAdapter(mDataSet);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mRecyclerView.
        /*// Save currently selected layout manager.
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        // restore RecyclerView state
        /*if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }*/
    }

    public List<ItemData> getmDataSet() {
        return this.mDataSet;
    }

    public void setmDataSet(List<ItemData>data){
        this.mDataSet = data;
    }

    public void add(ItemData data) {
        this.mDataSet.add(data);
        this.mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
        Log.d("list", mDataSet.toString());
    }

    //Call beetween onStop and onDetach
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}