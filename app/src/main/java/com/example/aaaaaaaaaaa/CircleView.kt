package com.example.aaaaaaaaaaa

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.math.hypot

class CircleGridView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val emotions = listOf(
        context.resources.getString(R.string.rage),
        context.resources.getString(R.string.envy),
        context.resources.getString(R.string.burnout),
        context.resources.getString(R.string.depression),
        context.resources.getString(R.string.tension),
        context.resources.getString(R.string.anxiety),
        context.resources.getString(R.string.fatigue),
        context.resources.getString(R.string.apathy),
        context.resources.getString(R.string.excitement),
        context.resources.getString(R.string.confidence),
        context.resources.getString(R.string.calmness),
        context.resources.getString(R.string.gratitude),
        context.resources.getString(R.string.delight),
        context.resources.getString(R.string.happiness),
        context.resources.getString(R.string.satisfaction),
        context.resources.getString(R.string.security)
    )

    private data class CircleData(val point: PointF, val text: String)
    private var color = 0xFFFFFFFF.toInt()
    private val circles = mutableListOf<CircleData>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private fun Context.toPx(dp: Int): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    )

    private val expandedTextSize = context.toPx(16)
    private val textSizeBasic = context.toPx(10)

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF000000.toInt()
        typeface = ResourcesCompat.getFont(context, R.font.gwen_trial_black)
        textSize = textSizeBasic
        textAlign = Paint.Align.CENTER
    }

    private var selectedCircleIndex = -1
    private val circleRadius = context.toPx(56)
    private val expandedRadius = context.toPx(76)
    private val spacing = context.toPx(115)
    private var xOffset = 0f
    private var yOffset = 0f
    private var isDragging = false
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var text = ""
    private var counter = 0

    init {
        for (j in 0 until 4) {
            for (i in 0 until 4) {
                text = emotions[counter]
                circles.add(CircleData(PointF(spacing * (j + 0.3f), spacing * (i + 2)), text))
                counter++
            }
        }
    }

    private fun init() {
        counter = 0
        circles.clear()
        for (j in 0 until 4) {
            for (i in 0 until 4) {
                text = emotions[counter]
                circles.add(
                    CircleData(
                        PointF(
                            spacing * (j + 0.3f) + xOffset,
                            spacing * (i + 2) + yOffset
                        ), text
                    )
                )
                counter++
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        circles.forEachIndexed { index, circleData ->
            var radius = 0f

            if (index <= 1 || index == 4 || index == 5) {
                paint.color = resources.getColor(R.color.red)
            } else if (index == 2 || index == 3 || index == 6 || index == 7) {
                paint.color = resources.getColor(R.color.blue)
            } else if (index == 8 || index == 9 || index == 12 || index == 13) {
                paint.color = resources.getColor(R.color.yellow)
            } else {
                paint.color = resources.getColor(R.color.green)
            }

            if (index == selectedCircleIndex) {
                radius = expandedRadius
                textPaint.textSize = expandedTextSize
                color = paint.color
            } else {
                radius = circleRadius
                textPaint.textSize = textSizeBasic
            }

            canvas.drawCircle(circleData.point.x, circleData.point.y, radius, paint)
            canvas.drawText(
                circleData.text,
                circleData.point.x,
                circleData.point.y + (textPaint.textSize / 3),
                textPaint
            )
            notifySelectedCircleChanged()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                var temp = selectedCircleIndex
                selectedCircleIndex = circles.indexOfFirst { circleData ->
                    hypot(
                        (event.x - circleData.point.x).toDouble(),
                        (event.y - circleData.point.y).toDouble()
                    ) <= circleRadius
                }
                if (selectedCircleIndex != -1) {
                    init()
                    moveCirclesOnAxes(selectedCircleIndex)
                } else {
                    selectedCircleIndex = temp
                    isDragging = true
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
                invalidate()
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY

                    xOffset += dx
                    yOffset += dy

                    circles.forEach { circleData ->
                        circleData.point.x += dx
                        circleData.point.y += dy
                    }

                    lastTouchX = event.x
                    lastTouchY = event.y
                    selectCircleAtCenter(context.display.width, context.display.height)
                    invalidate()
                }
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun moveCirclesOnAxes(selectedIndex: Int) {
        val selectedCircle = circles[selectedIndex]
        circles.forEachIndexed { index, circleData ->
            if (index != selectedIndex) {
                if (circleData.point.x == selectedCircle.point.x) {
                    if (circleData.point.y < selectedCircle.point.y) {
                        circleData.point.y -= spacing / 6
                    } else {
                        circleData.point.y += spacing / 6
                    }
                } else if (circleData.point.y == selectedCircle.point.y) {
                    if (circleData.point.x < selectedCircle.point.x) {
                        circleData.point.x -= spacing / 6
                    } else {
                        circleData.point.x += spacing / 6
                    }
                }
            }
        }
    }

    var onCircleSelectionChanged: (index: Int, title: String,color:Int) -> Unit = { _, _, _ -> }
    private fun notifySelectedCircleChanged() {
        if (selectedCircleIndex == -1)
        {
            onCircleSelectionChanged.invoke( -1, "",0)
            return
        }
        onCircleSelectionChanged.invoke(
            selectedCircleIndex,
            emotions[selectedCircleIndex],
            color)
    }

    private fun selectCircleAtCenter(viewWidth: Int, viewHeight: Int) {
        val centerX = viewWidth / 2f
        val centerY = viewHeight / 2f

        var minDistance = Float.MAX_VALUE
        var closestCircleIndex = -1

        circles.forEachIndexed { index, circleData ->
            val distance = hypot(
                (centerX - circleData.point.x).toDouble(),
                (centerY - circleData.point.y).toDouble()
            )
            if (distance < minDistance) {
                minDistance = distance.toFloat()
                closestCircleIndex = index
            }
        }

        if (closestCircleIndex != -1) {
            selectedCircleIndex = closestCircleIndex
            init()
            moveCirclesOnAxes(selectedCircleIndex)
            invalidate()
        }
    }
}