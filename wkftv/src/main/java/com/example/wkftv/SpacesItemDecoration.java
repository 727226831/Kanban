package com.example.wkftv;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;
    private Drawable mDivider;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = space;
    }



    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left, right, top, bottom;

            left = parent.getPaddingLeft ();
            right = parent.getWidth () + parent.getPaddingRight ();
            int count = parent.getChildCount ();
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt (i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams ();
                top = child.getBottom () + params.bottomMargin;
                bottom = top + space;
                mDivider=new ColorDrawable(Color.WHITE);
                mDivider.setBounds (left, top, right, bottom);
                mDivider.draw (c);
            }

    }
}
