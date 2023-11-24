package com.se.dr.bo.seconddrawingboard.wight

import android.graphics.Canvas

interface MotionAction {
    fun actionDown(canvas: Canvas?, x: Float, y: Float)
    fun actionMove(canvas: Canvas?, x: Float, y: Float)
    fun actionUp(canvas: Canvas?, x: Float, y: Float)
}
