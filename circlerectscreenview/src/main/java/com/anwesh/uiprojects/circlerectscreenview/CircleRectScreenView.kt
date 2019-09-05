package com.anwesh.uiprojects.circlerectscreenview

/**
 * Created by anweshmishra on 05/09/19.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.app.Activity
import android.content.Context

val colors : Array<String> = arrayOf("#e67e22", "#2980b9", "#2ecc71", "#e74c3c", "#1abc9c")
val scGap : Float = 0.01f
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20
val sizeFactor : Float = 2.5f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n

fun Canvas.drawRectScreen(x : Float, y : Float, size : Float, sc1 : Float, sc2 : Float, paint : Paint) {
    var w : Float = size
    if (sc2 > 0f) {
        w = size * sc2
    }
    drawRect(RectF(x + size * sc1, y - size / 2, x + size, y + size / 2), paint)
}
fun Canvas.drawCircleRect(size : Float, sc1 : Float, sc2 : Float, paint : Paint) {
    val path : Path = Path()
    path.addCircle(size / 2, 0f, size / 2, Path.Direction.CW);
    clipPath(path)
    drawRectScreen(0f, 0f, size, sc1, sc2, paint)
}

fun Canvas.drawCircleRectScreen(size : Float, sc1 : Float, sc2 : Float, paint : Paint) {
    drawRectScreen(-size, 0f, size, sc1.divideScale(0, 2), sc2.divideScale(0, 2), paint)
    drawCircleRect(size, sc1, sc2, paint)
}

fun Canvas.drawCRSNode(i : Int, scale : Float, sc : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val size : Float = Math.min(w, h) / sizeFactor
    paint.color = Color.parseColor(colors[i])
    save()
    translate(w / 2, h / 2)
    drawCircleRectScreen(size, scale, sc, paint)
    restore()
}

class CircleRectScreenView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += dir * scGap
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}
