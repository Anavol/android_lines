package com.anavol.lines

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class Extensions {
    fun Canvas.drawLine(line: Line, paint: Paint) =
            line.run {  drawLine(start.x,start.y,end.x,end.y, paint) }

    fun PointF.isInDistanceOf(x: Float, y: Float, maxDistance: Float): Boolean{
        val xDistance = this.x - x
        val yDistance = this.y - y
        val squaredDistance = xDistance*xDistance + yDistance*yDistance
        return squaredDistance <= maxDistance*maxDistance
    }
}