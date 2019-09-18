package com.anavol.lines

import android.graphics.PointF

class Line(val start:PointF, val end: PointF) {

    fun intersects(other: Line): Boolean{
        var counterOfSameVertices = 0
        if (start == other.start) counterOfSameVertices++
        if (end == other.end) counterOfSameVertices++
        if (end == other.start) counterOfSameVertices++
        if (start == other.end) counterOfSameVertices++
        if(!(counterOfSameVertices != 1)) return false
        val x1 = start.x
        val y1 = start.y

        val x2 = end.x
        val y2 = end.y

        val x3 = other.start.x
        val y3 = other.start.y

        val x4 = other.end.x
        val y4 = other.end.y

        val ax = x2-x1
        val ay = y2-y1
        val bx = x3-x4
        val by = y3-y4
        val cx = x3-x1
        val cy = y3-y1

        val denominator = ax * by - bx * ay
        val numerator = cx * by - bx *cy
        val otherNumerator = ax * cy - cx * ay

            if((numerator == 0f) || (denominator == 0f)) {

           return false
        }
        val ut = numerator / denominator
        val uo = otherNumerator / denominator
        return ut in 0.0..1.0 && uo in 0.0..1.0
    }
    companion object {
    fun  intersectsMultiple(linesArray: MutableList<Line>): Boolean {
        var flag = false
        for(i in 0 until linesArray.size)
        for(j in 0 until linesArray.size){
            if (i!=j){
                flag = linesArray[i].intersects(linesArray[j])
                if (flag == true) return flag
        }
        }
        return flag
    }
    }
}