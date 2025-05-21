package com.example.aaaaaaaaaaa

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.graphics.*
import androidx.core.content.res.ResourcesCompat

class CircleGenerate @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var koeff = 1.0f
    private var maxRadius = 0f
    private var text = ""
    private val startRadius = context.toPx(260)
    @SuppressLint("ResourceType")
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private fun Context.toPx(dp: Int): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    )

    private val textSizeBasic = context.toPx(20)

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF000000.toInt()
        typeface = ResourcesCompat.getFont(context, R.font.velasans_bold)
        textSize = textSizeBasic
        textAlign = Paint.Align.CENTER
    }

    @SuppressLint("DrawAllocation", "ResourceType")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val circlesRadius = listOf(
            startRadius * 0.25f,
            startRadius * 0.25f,
            startRadius * 0.25f,
            startRadius * 0.25f
        )

        val shaderRed = LinearGradient(
            circlesRadius[0],
            0.0f,
            circlesRadius[0],
            circlesRadius[0]*2f,
            0XFFFF5533.toInt(),
            0XFFFF0000.toInt(),
            Shader.TileMode.MIRROR
        )

        val shaderYellow = LinearGradient(
            circlesRadius[1],
            0.0f,
            circlesRadius[1],
            circlesRadius[1]*2f,
            0XFFFFFF33.toInt(),
            0XFFFFAA00.toInt(),
            Shader.TileMode.MIRROR
        )

        val shaderBlue = LinearGradient(
            circlesRadius[2],
            height.toFloat(),
            circlesRadius[2],
            height - circlesRadius[2]*2f,
            0XFF00AAFF.toInt(),
            0XFF33DDFF.toInt(),
            Shader.TileMode.MIRROR
        )

        val shaderGreen = LinearGradient(
            width - circlesRadius[3],
            height.toFloat(),
            width - circlesRadius[3],
            height - circlesRadius[3]*2f,
            0XFF00FF55.toInt(),
            0XFF33FFBB.toInt(),
            Shader.TileMode.MIRROR
        )

        var circles = listOf(
            Circle(
                PointF(circlesRadius[0], circlesRadius[0]), shaderRed, circlesRadius[0]
            ),
            Circle(
                PointF(width - circlesRadius[1], circlesRadius[1]), shaderYellow, circlesRadius[1]
            ),
            Circle(
                PointF(circlesRadius[2], height - circlesRadius[2]), shaderBlue, circlesRadius[2]
            ),
            Circle(
                PointF(width - circlesRadius[3], height - circlesRadius[3]), shaderGreen, circlesRadius[3]
            )
        )

        for(i in 0..3){
            if(maxRadius<circles[i].radius){
                maxRadius = circles[i].radius
            }
            if(circles[i].radius * 2 > width){
                koeff = circles[i].radius*2/width
                circles[0].point = PointF(circles[0].radius/koeff, circles[0].radius/koeff)
                circles[1].point = PointF(width - circles[1].radius/koeff, circles[1].radius/koeff)
                circles[2].point = PointF(circles[2].radius/koeff, height - circles[2].radius/koeff)
                circles[3].point = PointF(width - circles[3].radius/koeff, height - circles[3].radius/koeff)
            }
        }

        if(maxRadius <= startRadius * 0.4f){
            koeff = 0.95f
            circles[0].point = PointF(circles[0].radius/koeff, circles[0].radius/koeff)
            circles[1].point = PointF(width - circles[1].radius/koeff, circles[1].radius/koeff)
            circles[2].point = PointF(circles[2].radius/koeff, height - circles[2].radius/koeff)
            circles[3].point = PointF(width - circles[3].radius/koeff, height - circles[3].radius/koeff)
        }

        if(maxRadius <= startRadius * 0.3f){
            koeff = 0.75f
            circles[0].point = PointF(circles[0].radius/koeff, circles[0].radius/koeff)
            circles[1].point = PointF(width - circles[1].radius/koeff, circles[1].radius/koeff)
            circles[2].point = PointF(circles[2].radius/koeff, height - circles[2].radius/koeff)
            circles[3].point = PointF(width - circles[3].radius/koeff, height - circles[3].radius/koeff)
        }

        if(maxRadius == startRadius * 0.25f){
            koeff = 0.65f
            circles[0].point = PointF(circles[0].radius/koeff, circles[0].radius/koeff)
            circles[1].point = PointF(width - circles[1].radius/koeff, circles[1].radius/koeff)
            circles[2].point = PointF(circles[2].radius/koeff, height - circles[2].radius/koeff)
            circles[3].point = PointF(width - circles[3].radius/koeff, height - circles[3].radius/koeff)
        }

        circles = circles.sortedBy { it.radius }



        for (i in 0..3) {
            if(circles[i].radius != 0f){
                paint.shader = circles[i].shader
                canvas.drawCircle(circles[i].point.x, circles[i].point.y, circles[i].radius/koeff, paint)
                text = ((circles[i].radius/startRadius)*100).toInt().toString() + "%"
                canvas.drawText(
                    text,
                    circles[i].point.x,
                    circles[i].point.y + (textPaint.textSize / 3),
                    textPaint
                )
            }
        }

    }

    data class Circle(var point: PointF, var shader: LinearGradient, var radius: Float)
}