package com.example.customview_animation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator

class Animaion @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val heartPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.RED }
    private var heartScale = 1f
    private val heartPath = Path()

    init {
        setOnClickListener { animateHeart() }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec).coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
        setMeasuredDimension(size, size + 200) // Увеличиваем высоту для сердца
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawHeart(canvas)
    }

    private fun drawHeart(canvas: Canvas) {
        val cx = width / 2f
        val cy = height - 500f
        val size = 150f * heartScale

        heartPath.reset()
        heartPath.moveTo(cx, cy)
        heartPath.cubicTo(cx - size, cy - size, cx - size * 2, cy + size, cx, cy + size * 2)
        heartPath.cubicTo(cx + size * 2, cy + size, cx + size, cy - size, cx, cy)
        canvas.drawPath(heartPath, heartPaint)
    }

    private fun animateHeart() {
        val animator = ValueAnimator.ofFloat(1f, 1.5f, 1f).apply {
            duration = 500
            interpolator = OvershootInterpolator()
            addUpdateListener {
                heartScale = it.animatedValue as Float
                invalidate()
            }
        }
        animator.start()
    }
}