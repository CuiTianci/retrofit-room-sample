package com.demo.retrofitroom

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemSpacingDecoration(private val context: Context, private val spacing: Int) :
    RecyclerView.ItemDecoration() {
    private fun dpToPx(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) < (parent.adapter?.itemCount ?: (0 - 1))) {
            outRect.bottom = dpToPx(spacing)
        }
    }
}
