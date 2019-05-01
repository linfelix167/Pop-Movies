package com.felix.popmovies;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AutoFitGridLayoutManager extends GridLayoutManager {

    private int colWidth;
    private boolean colWidthChanged = true;

    public AutoFitGridLayoutManager(Context context, int colWidth) {
        super(context, 1);

        setColWidth(colWidth);
    }

    private void setColWidth(int newColWidth) {
        if (newColWidth > 0 && newColWidth != colWidth) {
            colWidth = newColWidth;
            colWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (colWidthChanged && colWidth > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / colWidth);
            setSpanCount(spanCount);
            colWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}
