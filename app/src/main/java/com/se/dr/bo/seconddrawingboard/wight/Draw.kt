package com.se.dr.bo.seconddrawingboard.wight

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
abstract class Draw : MotionAction {
    @JvmField
    var paint: Paint? = null
    override fun actionDown(canvas: Canvas?, x: Float, y: Float) {
        Log.d("TAG", String.format("actionDown: %f, %f", x, y))
    }

    override fun actionMove(canvas: Canvas?, x: Float, y: Float) {
        Log.d("TAG", String.format("actionMove: %f, %f", x, y))
    }

    override fun actionUp(canvas: Canvas?, x: Float, y: Float) {
        Log.d("TAG", String.format("actionUp: %f, %f", x, y))
    }

    abstract fun draw(canvas: Canvas?)
}
