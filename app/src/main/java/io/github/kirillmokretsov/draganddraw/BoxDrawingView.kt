package io.github.kirillmokretsov.draganddraw

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "BoxDrawingView"

class BoxDrawingView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> action = "ACTION_DOWN"
            MotionEvent.ACTION_MOVE -> action = "ACTION_MOVE"
            MotionEvent.ACTION_UP -> action = "ACTION_UP"
            MotionEvent.ACTION_CANCEL -> action = "ACTION_CANCEL"
        }

        Log.i(TAG, "$action at x=${current.x}, y = ${current.y}")

        return true
    }
}