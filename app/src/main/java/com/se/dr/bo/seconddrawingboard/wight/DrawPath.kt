package com.se.dr.bo.seconddrawingboard.wight

import android.graphics.Canvas
import android.graphics.Path

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
class DrawPath : Draw() {
    private var path: Path
    private var lastX = 0f
    private var lastY = 0f

    init {
        path = Path()
    }

    fun setPath(path: Path) {
        this.path = path
    }

    override fun actionDown(canvas: Canvas?, x: Float, y: Float) {
        super.actionDown(canvas, x, y)
        path.moveTo(x, y)
        lastX = x
        lastY = y
    }

    override fun actionMove(canvas: Canvas?, x: Float, y: Float) {
        super.actionMove(canvas, x, y)
        path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2)
        canvas?.drawPath(path, paint!!)
        lastX = x
        lastY = y
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawPath(path, paint!!)
    }
}
