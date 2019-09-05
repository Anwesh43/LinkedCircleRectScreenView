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
import android.app.Activity
import android.content.Context

val colors : Array<String> = arrayOf("#e67e22", "#2980b9", "#2ecc71", "#e74c3c", "#1abc9c")
val scGap : Float = 0.01f
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
