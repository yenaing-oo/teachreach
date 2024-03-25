package comp3350.teachreach.presentation.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration
{

    private final int     spanCount;
    private final int     spacing;
    private final boolean includeEdge;
    private final int     headerNum;

    public GridSpacingItemDecoration(int spanCount,
                                     int spacing,
                                     boolean includeEdge,
                                     int headerNum)
    {
        this.spanCount   = spanCount;
        this.spacing     = spacing;
        this.includeEdge = includeEdge;
        this.headerNum   = headerNum;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               RecyclerView parent,
                               @NonNull RecyclerView.State state)
    {
        int position = parent.getChildAdapterPosition(view) - headerNum;

        if (position < 0) {
            outRect.left   = 0;
            outRect.right  = 0;
            outRect.top    = 0;
            outRect.bottom = 0;
            return;
        }

        int column = position % spanCount;

        if (includeEdge) {
            outRect.left   = spacing - column * spacing / spanCount;
            outRect.right  = (column + 1) * spacing / spanCount;
            outRect.bottom = spacing;
            if (position < spanCount) {
                outRect.top = spacing;
            }
        } else {
            outRect.left  = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            if (position >= spanCount) {
                outRect.top = spacing;
            }
        }
    }
}
