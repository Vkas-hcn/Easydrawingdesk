package com.se.dr.bo.seconddrawingboard.wight

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap

@SuppressLint("ClickableViewAccessibility")
class ColorPickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private var colorListener: ((Int) -> Unit)? = null
    private var touchX: Float? = null
    private var touchY: Float? = null
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 5f
    }

    init {
        this.isDrawingCacheEnabled = true
        this.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                val bitmap = (v as ImageView).drawable.toBitmap()
                val x = event.x.toInt().coerceIn(0, bitmap.width - 1)
                val y = event.y.toInt().coerceIn(0, bitmap.height - 1)
                val pixel = bitmap.getPixel(x, y)
                val redValue = Color.red(pixel)
                val blueValue = Color.blue(pixel)
                val greenValue = Color.green(pixel)
                val alphaValue = Color.alpha(pixel)
                touchX = event.x
                touchY = event.y
                colorListener?.invoke(Color.argb(alphaValue, redValue, greenValue, blueValue))
            }
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                touchX = null
                touchY = null
            }
            invalidate()

            true
        }
    }

    fun setColorListener(listener: (Int) -> Unit) {
        colorListener = listener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the ring at the touch coordinates
        touchX?.let { x ->
            touchY?.let { y ->
                canvas.drawCircle(x, y, 20f, paint)
            }
        }
    }

}
