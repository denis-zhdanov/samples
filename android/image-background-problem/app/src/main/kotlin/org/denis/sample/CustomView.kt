package org.denis.sample

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defaultStyle: Int = 0
) : View(context, attributes, defaultStyle) {

    private val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img)
    private val paint = Paint().apply {
        color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(paint)
        val left = (width - bitmap.width) / 2f
        val top = (height - bitmap.height) / 2f
        canvas.drawBitmap(bitmap, left, top, null)
    }
}