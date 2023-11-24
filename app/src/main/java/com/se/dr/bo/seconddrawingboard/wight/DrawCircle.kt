package com.se.dr.bo.seconddrawingboard.wight

import android.graphics.Canvas
import android.graphics.RectF

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
class DrawCircle : Draw() {
    private var rect: RectF

    init {
        rect = RectF()
    }

    fun setRect(rect: RectF) {
        this.rect = rect
    }

    override fun actionDown(canvas: Canvas?, x: Float, y: Float) {
        super.actionDown(canvas, x, y)
        rect.left = x
        rect.top = y
    }

    override fun actionMove(canvas: Canvas?, x: Float, y: Float) {
        super.actionMove(canvas, x, y)
        rect.right = x
        rect.bottom = y
        canvas!!.drawCircle(
            rect.centerX(),
            rect.centerY(),
            Math.min(rect.width(), rect.height()),
            paint!!
        )
    }



    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(
            rect.centerX(),
            rect.centerY(),
            Math.min(rect.width(), rect.height()),
            paint!!
        )
    }
}
