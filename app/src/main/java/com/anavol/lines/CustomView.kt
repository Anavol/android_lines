package com.anavol.lines

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random
import android.app.Activity


class  CustomView  constructor(context: Context, attributeSet: AttributeSet) : View(context)  {
    val linesArray: MutableList<Line> = ArrayList()
    val touchRadius = 30f
    val scale = context.resources.displayMetrics.density
    val touchRadiusPx = touchRadius * scale
    var numberOfVertices = 5
    var maxX = 1000
    var maxY = 1000
    var vertices = getVertices(numberOfVertices, maxX, maxY)
    var graph = initGraph(numberOfVertices)
    var lines = getLines(graph, vertices, numberOfVertices)
    val points = listOf(
        PointF(vertices[0][0].toFloat(), vertices[1][0].toFloat()),
        PointF(vertices[0][1].toFloat(), vertices[1][1].toFloat()),
        PointF(vertices[0][2].toFloat(), vertices[1][2].toFloat()),
        PointF(vertices[0][3].toFloat(), vertices[1][3].toFloat()),
        PointF(vertices[0][4].toFloat(), vertices[1][4].toFloat())
    )

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        strokeWidth = 10f
    }
    var selectedPoint: PointF? = null
    var collision = Line.intersectsMultiple(linesArray)
    init {
        val displayMetrics = DisplayMetrics()
        (getContext() as Activity).windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        maxX = displayMetrics.widthPixels
        maxY = displayMetrics.heightPixels
        /*do {
            linesArray.clear()
            vertices = getVertices(numberOfVertices, maxX, maxY)
            lines = getLines(graph, vertices, numberOfVertices)
            for (i in 0 until numberOfVertices )
                for (j in 0 until numberOfVertices) {
                    if (graph[i][j] == 1) linesArray.add(Line(points[i],points[j]))

                }
            collision = Line.intersectsMultiple(linesArray)
        } while (collision )
        */
        for (i in 0 until numberOfVertices )
            for (j in 0 until numberOfVertices) {
                if (graph[i][j] == 1) linesArray.add(Line(points[i],points[j]))

            }
        collision = Line.intersectsMultiple(linesArray)

         }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            if (collision)
                drawColor(Color.DKGRAY)
            else {
                drawColor(Color.GREEN)
            }
            linesArray.forEach {
                drawLine(it, paint)
            }
              points.forEach {
               drawCircle(it.x,it.y,20f,paint)
             }
        }
        }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when(action){
                MotionEvent.ACTION_DOWN -> {
                    Log.d("touch", "down at $x $y")
                    if ((x in 100f..maxX.toFloat())&&( y in 100f..maxY.toFloat())) {
                        selectedPoint = points.find { it.isInDistanceOf(x, y, touchRadiusPx) }
                    }
                }
                MotionEvent.ACTION_MOVE ->{
                    Log.d("touch", "move at $x $y")
                    if ((x in 100f..maxX.toFloat())&&( y in 100f..maxY.toFloat())){
                    selectedPoint?.let {
                        it.set(x, y)
                        collision = Line.intersectsMultiple(linesArray)
                        invalidate()
                    }
                    }
                }
            }
        }
        return true
    }

    fun setValues(n: Int){
        numberOfVertices = n
    }

    fun PointF.isInDistanceOf(x: Float, y: Float, maxDistance: Float): Boolean{
        val xDistance = this.x - x
        val yDistance = this.y - y
        val squaredDistance = xDistance*xDistance + yDistance*yDistance
        return squaredDistance <= maxDistance*maxDistance
    }

    fun Canvas.drawLine(line: Line, paint: Paint) =
        line.run {  drawLine(start.x,start.y,end.x,end.y, paint) }

    fun getVertices(numberOfVertices: Int, maxX: Int, maxY: Int): Array<IntArray> {
        var vertices = Array(2, { IntArray(numberOfVertices) })
        var difference = 0
        for (i in 0 until numberOfVertices) {
            while ((vertices[0][i] == 0) or (vertices[1][i] == 0)) {
                vertices[0][i] = Random.nextInt(100, maxX - 100)
                vertices[1][i] = Random.nextInt(100, maxY - 100)
                for (j in 0 until i) {
                    difference =
                        Math.abs(vertices[0][i] - vertices[0][j]) + Math.abs(vertices[1][i] - vertices[1][j])
                    if (difference < 25) {
                        vertices[0][i] = 0
                        vertices[1][i] = 0
                    }
                }
            }

        }
        return vertices
    }

    fun initGraph(numberOfVertices: Int): Array<IntArray> {
        var maxEdges = 3 //to avoid subgraph K5
        var graph = Array(numberOfVertices, { IntArray(numberOfVertices) })
        var tempEdges = 0
        var t = 0
        //if (numberOfVertices < 4) maxEdges = 3
        //else maxEdges = 2
        for (i in 0 until numberOfVertices) {
            tempEdges = 0
            for (k in 0 until numberOfVertices) if (graph[i][k] == 1) tempEdges++
            if (tempEdges >= maxEdges) break
            for (j in 0 until numberOfVertices) {
                if (graph[i][j] != 1) {
                    if (Random.nextBoolean()) {
                        graph[i][j] = 1
                        graph[j][i] = 1
                        tempEdges++
                    }
                    if (tempEdges >= maxEdges) break

                }
                if ((tempEdges == 0) and (j == numberOfVertices - 1)) {
                    t = i
                    while (true){
                        t = Random.nextInt(0, numberOfVertices - 1)
                    if(t != i){
                        graph[i][t] = 1
                        graph[t][i] = 1
                        tempEdges++
                        break
                    }
                    }
                }
            }
        }
        for (i in 0 until numberOfVertices )
            for (j in 0 until numberOfVertices) {
                if ((graph[i][j] == 1) and (graph[j][i] == 1))
                    graph[j][i] = 0
            }
        return graph
    }

    fun getLines(graph: Array<IntArray>, vertices: Array<IntArray>, numberOfVertices: Int): Array<IntArray> {
        var numberOfLines = 0
        var lineNumber = 0
        for (i in 0 until numberOfVertices )
            for (j in 0 until numberOfVertices) {
                if (graph[i][j] == 1) numberOfLines++
            }
        var lines = Array(numberOfLines, { IntArray(4) })
        for (i in 0 until numberOfVertices )
            for (j in 0 until numberOfVertices) {
                if (graph[i][j] == 1) {
                    lines[lineNumber][0] = vertices[0][i]
                    lines[lineNumber][1] = vertices[1][i]
                    lines[lineNumber][2] = vertices[0][j]
                    lines[lineNumber][3] = vertices[1][j]
                    lineNumber++
                }
            }
        return lines
    }

}