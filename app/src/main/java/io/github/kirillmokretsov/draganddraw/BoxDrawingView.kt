package io.github.kirillmokretsov.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.gson.Gson

private const val TAG = "BoxDrawingView"
private const val BUNDLE_PARENT = "bundleOfParent"
private const val BUNDLE_BOXEN = "bundleOfBoxen"

class BoxDrawingView(context: Context, attributeSet: AttributeSet? = null) :
    View(context, attributeSet) {

    private var currentBox: Box? = null
    private var boxen = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color = 0x22ff00000.toInt()
    }
    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // Reset drawing state
                currentBox = Box(current).also {
                    boxen.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }

        Log.v(TAG, "$action at x=${current.x}, y = ${current.y}")

        return true
    }

    override fun onSaveInstanceState(): Bundle =
        Bundle().apply {
            Log.d(TAG, "onSaveInstanceState()")
            putParcelable(BUNDLE_PARENT, super.onSaveInstanceState())
            putSerializable(BUNDLE_BOXEN, Gson().toJson(boxen))
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.d(TAG, "onRestoreInstanceState()")
        if (state is Bundle) {
            val parentState: Parcelable? = state.getParcelable(BUNDLE_PARENT)
            super.onRestoreInstanceState(parentState)

            val rawGson = state.getSerializable(BUNDLE_BOXEN) as String
            Log.d(TAG, rawGson)
            // TODO: parse rawGson to boxen
        }
    }


    override fun onDraw(canvas: Canvas) {
        // Fill the background
        canvas.drawPaint(backgroundPaint)

        boxen.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }

}