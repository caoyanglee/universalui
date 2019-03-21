package com.weimu.app.universalview.module.customview.vml;

import android.content.Context;
import android.database.Observable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AdaptableViewGroup extends ConfigurableViewGroup {
    private Adapter mAdapter;
    private List<ViewHolder> views = new ArrayList<>();
    private DataObserver mDataObserver;

    public AdaptableViewGroup(Context context) {
        this(context, null);
    }

    public AdaptableViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdaptableViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setAdapter(Adapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataObserver);
        }
        if (adapter == null) {
            return;
        }
        mAdapter = adapter;
        mDataObserver = new DataObserver();
        mAdapter.registerDataSetObserver(mDataObserver);
        mAdapter.notifyDataSetChanged();
    }


    public static abstract class Adapter<VH extends ViewHolder> {
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public abstract VH onCreateHolder(ViewGroup container, int viewType);

        public abstract void onBindViewHolder(VH holder, int position);

        public int getItemViewType(int position) {
            return 0;
        }

        public abstract int getItemCount();


        public void registerDataSetObserver(AdapterDataObserver observer) {
            mObservable.registerObserver(observer);
        }

        public void unregisterDataSetObserver(AdapterDataObserver observer) {
            mObservable.unregisterObserver(observer);
        }

        public final void notifyDataSetChanged() {
            mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int position) {
            mObservable.notifyItemRangeChanged(position, 1);
        }

        public final void notifyItemRangeChanged(int positionStart, int itemCount) {
            mObservable.notifyItemRangeChanged(positionStart, itemCount);
        }

        public final void notifyItemInserted(int position) {
            mObservable.notifyItemRangeInserted(position, 1);
        }

        public final void notifyItemRangeInserted(int positionStart, int itemCount) {
            mObservable.notifyItemRangeInserted(positionStart, itemCount);
        }

        public final void notifyItemRemoved(int position) {
            mObservable.notifyItemRangeRemoved(position, 1);
        }

        public final void notifyItemRangeRemoved(int positionStart, int itemCount) {
            mObservable.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    public class AdapterDataObserver {

        public void onChanged() {
            // Do nothing
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            // do nothing
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            // do nothing
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            // do nothing
        }
    }

    public static class AdapterDataObservable extends Observable<AdapterDataObserver> {

        public void notifyChanged() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }

        public void notifyItemRangeChanged(int positionStart, int itemCount) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeChanged(positionStart, itemCount);
            }
        }

        public void notifyItemRangeInserted(int positionStart, int itemCount) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeInserted(positionStart, itemCount);
            }
        }

        public void notifyItemRangeRemoved(int positionStart, int itemCount) {

            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeRemoved(positionStart, itemCount);
            }
        }

    }

    public static class ViewHolder {
        public View itemView;
        int mViewType = 0;
        AdaptableViewGroup viewManageLayout;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public int getAdapterPosition() {
            return viewManageLayout.views.indexOf(this);
        }
    }

    class DataObserver extends AdapterDataObserver {

        @SuppressWarnings("unchecked")
        @Override
        public void onChanged() {
            removeAllViews();
            invalidate();
            views.clear();
            if (mAdapter == null || mAdapter.getItemCount() == 0) {
                return;
            }
            onItemRangeInserted(0, mAdapter.getItemCount());
        }


        @SuppressWarnings("unchecked")
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            onItemRangeRemoved(positionStart, views.size() - positionStart + 1);
            onItemRangeInserted(positionStart, mAdapter.getItemCount() - positionStart + 1);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            for (int index = positionStart; index < positionStart + itemCount; index++) {
                ViewHolder viewHolder = mAdapter.onCreateHolder(AdaptableViewGroup.this, mAdapter.getItemViewType(index));
                if (viewHolder != null) {
                    viewHolder.viewManageLayout = AdaptableViewGroup.this;
                    views.add(index, viewHolder);
                    mAdapter.onBindViewHolder(viewHolder, index);
                    addView(viewHolder.itemView, index);
                }
            }

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            views.subList(positionStart, positionStart + itemCount).clear();
            removeViews(positionStart, itemCount);
            requestLayout();
        }

    }



}
