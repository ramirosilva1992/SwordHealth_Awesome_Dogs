package com.swordhealth.awesomedogs.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecoration(private val context: Context, @DrawableRes id: Int) : RecyclerView.ItemDecoration() {

    private lateinit var drawable: Drawable

    init {
        ContextCompat.getDrawable(context, id)?.apply { drawable = this }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val start = parent.paddingStart
        val end = parent.width - parent.paddingEnd

        val childCount = parent.adapter?.itemCount ?: 1

        for (i in 0 until childCount - 1) {
            parent.getChildAt(i)?.let { child ->
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                drawable.apply { setBounds(start, top, end, top + intrinsicHeight) }
                drawable.draw(c)
            }

        }
    }
}