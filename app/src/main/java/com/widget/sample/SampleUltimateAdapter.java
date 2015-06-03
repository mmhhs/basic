package com.widget.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base.feima.baseproject.R;
import com.widget.ultimate.UltimateViewAdapter;

import java.util.List;


public class SampleUltimateAdapter extends UltimateViewAdapter {
    private List<String> stringList;

    public SampleUltimateAdapter(List<String> stringList) {
        this.stringList = stringList;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= stringList.size() : position < stringList.size()) && (customHeaderView != null ? position > 0 : true)) {

            ((ViewHolder) holder).textViewSample.setText(stringList.get(customHeaderView != null ? position - 1 : position));
            // ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
        }

    }

    @Override
    public int getAdapterItemCount() {
        return stringList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_ultimate_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(String string, int position) {
        insert(stringList, string, position);
    }

    public void remove(int position) {
        remove(stringList, position);
    }

    public void clear() {
        clear(stringList);
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


    public void swapPositions(int from, int to) {
        swapPositions(stringList, from, to);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSample;
        ImageView imageViewSample;
        ProgressBar progressBarSample;

        public ViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnTouchListener(new SwipeDismissTouchListener(itemView, null, new SwipeDismissTouchListener.DismissCallbacks() {
//                @Override
//                public boolean canDismiss(Object token) {
//                    Logs.d("can dismiss");
//                    return true;
//                }
//
//                @Override
//                public void onDismiss(View view, Object token) {
//                   // Logs.d("dismiss");
//                    remove(getPosition());
//
//                }
//            }));
            textViewSample = (TextView) itemView.findViewById(
                    R.id.textview);
            imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);
            progressBarSample = (ProgressBar) itemView.findViewById(R.id.progressbar);
            progressBarSample.setVisibility(View.GONE);
        }
    }
}
