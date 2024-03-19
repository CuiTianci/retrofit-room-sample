package com.demo.retrofitroom

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

fun String.dLog() {
    Log.d("rotroom", this)
}

fun RecyclerView.verticalInterval(dp: Int) {
    this.addItemDecoration(VerticalItemSpacingDecoration(this.context, dp))
}