package com.zeba.base.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GridGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GridGroupItem> mShowData=new ArrayList<>();
    private Map<View,GridGroupItem> clickMap=new HashMap<>();

    public GridGroupAdapter() {

    }

    private GridLayoutManager manager;
    public void setRecyclerView(RecyclerView recyclerView){
        manager = new GridLayoutManager(recyclerView.getContext(),1, OrientationHelper.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getSpanSize(getItemViewType(position));
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
    }

    public void setNewData(List<GridGroupItem> list) {
        if(list==null||list.size()==0){
            return;
        }
        mShowData.clear();
        findChild(list,0,0);
        notifyDataSetChanged();
    }

    private void findChild(List<GridGroupItem> list,int level,int parentPosition){
        for(int i=0;i< list.size();i++){
            GridGroupItem item=list.get(i);
            item.setLevel(level);
            item.setParentPosition(parentPosition);
            item.setPosition(i);
            mShowData.add(item);
            findChild(item.getChildList(),level+1,i);
        }
    }

    public abstract int getSpanSize(int type);
    public abstract int getLayoutId(int type);
    public abstract void convert(GridViewHolder holder, GridGroupItem item);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        RecyclerView.ViewHolder holder = new GridViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GridViewHolder holder1 = (GridViewHolder) holder;
        convert(holder1,mShowData.get(position));
        holder.itemView.setOnClickListener(clickListener);
        clickMap.put(holder.itemView,mShowData.get(position));
    }

    @Override
    public int getItemCount() {
        return mShowData == null ? 0 : mShowData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mShowData.get(position).getType();
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GridGroupItem item=clickMap.get(v);
            if(item!=null&&itemListener!=null){
                itemListener.onItemClick(item);
            }
        }
    };

    private GridGroupItemListener itemListener;
    public void setGridGroupItemListener(GridGroupItemListener listener){
        itemListener=listener;
    }

    /**
     * 分组
     */
    public class GridViewHolder extends RecyclerView.ViewHolder {
        public GridViewHolder(View itemView) {
            super(itemView);
        }

    }

}

