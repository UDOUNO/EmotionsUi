package com.example.aaaaaaaaaaa

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.min


data class ProgressItem(
    val amount: Int,
    val startColor: Int,
    val endColor: Int
)

class GradientProgressCircle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0f
    private var ringThickness = 90f
    private val diagramPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var progressItems: List<ProgressItem> = emptyList()
    private var diagramGoal = 2
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val loaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var loaderAngle = 0f
    private val loaderSweepAngle = 144f

    private val loaderColors = intArrayOf(
        Color.TRANSPARENT,
        context.getColor(R.color.gray6),
        context.getColor(R.color.gray6),
        Color.TRANSPARENT
    )

    init {
        diagramConfig(
            listOf(
//                ProgressItem(1, resources.getColor(R.color.yellow_grad_start),resources.getColor(R.color.yellow_grad_end)),
//                ProgressItem(1, resources.getColor(R.color.red_grad_start), resources.getColor(R.color.red_grad_end)),
//                ProgressItem(1, resources.getColor(R.color.blue_grad_start), resources.getColor(R.color.blue_grad_end)),
//                ProgressItem(1, resources.getColor(R.color.blue_grad_start), resources.getColor(R.color.green_grad_end))
            )
        )

        backgroundPaint.color = resources.getColor(R.color.back_gray)
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = ringThickness
        loaderPaint.style = Paint.Style.STROKE
        loaderPaint.strokeWidth = ringThickness
        loaderPaint.strokeCap = Paint.Cap.ROUND
        diagramPaint.style = Paint.Style.STROKE
        diagramPaint.strokeWidth = ringThickness
        diagramPaint.strokeCap = Paint.Cap.ROUND
    }

    private val LoaderAnimator: ValueAnimator by lazy {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 5000L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                loaderAngle = animation.animatedValue as Float
                invalidate()
            }
        }
    }

    fun diagramConfig(items: List<ProgressItem>, goal: Int = 2) {
        this.progressItems = items
        this.diagramGoal = goal
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        radius = (width.coerceAtMost(height) / 2f) - ringThickness / 2f

        if (progressItems.isEmpty()) {
            startLoaderAnimation()
            drawLoader(canvas)
        } else {
            stopLoaderAnimation()
            drawDiagram(canvas)
        }
    }

    private fun drawDiagram(canvas: Canvas) {
        canvas.drawArc(
            width / 2f - radius, height / 2f - radius,
            width / 2f + radius, height / 2f + radius,
            0f, 360f, false, backgroundPaint
        )

        val totalAmount = progressItems.sumOf { it.amount }

        val scaleFactor = if (totalAmount < diagramGoal) totalAmount.toFloat() / diagramGoal else 1f

        val colors = mutableListOf<Int>()
        val positions = mutableListOf<Float>()

        var currentAngle = 0f
        for (item in progressItems) {
            val sweepAngle = 360f * (item.amount / totalAmount.toFloat()) * scaleFactor

            colors.add(item.startColor)
            colors.add(item.endColor)

            positions.add(currentAngle / 360f)
            positions.add((currentAngle + sweepAngle) / 360f)

            currentAngle += sweepAngle
        }

        colors.add(colors[0])
        positions.add(1f)

        val gradient = rotatedSweepGradient(
            colors.toIntArray(), positions.toFloatArray(), -90f)

        diagramPaint.shader = gradient

        canvas.drawArc(
            width / 2f - radius, height / 2f - radius,
            width / 2f + radius, height / 2f + radius,
            0f - 90f, 360f * scaleFactor, false, diagramPaint
        )
    }

    private fun drawLoader(canvas: Canvas) {
        canvas.drawArc(
            width / 2f - radius, height / 2f - radius,
            width / 2f + radius, height / 2f + radius,
            0f, 360f, false, backgroundPaint
        )

        val gradient = rotatedSweepGradient(loaderColors, floatArrayOf(0f, 0.4f, 0.5f, 1f), loaderAngle)

        loaderPaint.shader = gradient

        canvas.drawArc(
            width / 2f - radius, height / 2f - radius,
            width / 2f + radius, height / 2f + radius,
            loaderAngle, loaderSweepAngle, false, loaderPaint
        )
    }


    private fun startLoaderAnimation() {
        if (!LoaderAnimator.isRunning) {
            LoaderAnimator.start()
        }

    }

    private fun stopLoaderAnimation() {
        if (LoaderAnimator.isRunning) {
            LoaderAnimator.cancel()
        }
    }

    private fun rotatedSweepGradient(colors: IntArray, positions: FloatArray, angle: Float = 0f): SweepGradient
    {
        val gradient = SweepGradient(
            0f, 0f,
            colors, positions
        )
        val matrix = Matrix()
        matrix.postRotate(angle)
        matrix.postTranslate(width / 2f, height / 2f)
        gradient.setLocalMatrix(matrix)

        return gradient
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        setMeasuredDimension(size, size)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLoaderAnimation()
    }
}