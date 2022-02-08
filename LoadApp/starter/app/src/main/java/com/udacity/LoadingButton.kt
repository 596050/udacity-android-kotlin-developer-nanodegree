package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import kotlin.properties.Delegates

const val FONT_SIZE = 70.0f

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private val valueAnimator = ValueAnimator()
    private val textAttributes = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.textSize = FONT_SIZE
        this.color = Color.WHITE
        this.textAlign = Paint.Align.CENTER
        this.style = Paint.Style.FILL
    }
    private var loadingPercentage = 0.0f
    var text = context.getString(R.string.button_name)
    var textPositionYPosition = 0.0f


    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Clicked -> {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show()
            }
            ButtonState.Loading -> {
                text = context.getString(R.string.button_loading)
//                valueAnimator.start()
                isClickable = false
            }
            ButtonState.Completed -> {
                text = context.getString(R.string.button_name)
                loadingPercentage = 0.0f
                invalidate()
                isClickable = true
            }
        }
    }


    init {

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rect = Rect()
        textAttributes.getTextBounds(this.text, 0, this.text.length, rect)
        canvas?.drawText(
            this.text,
            measuredWidth.toFloat() / 2,
            measuredHeight.toFloat() / 2 - rect.centerY(),
            this.textAttributes
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    fun setState(state: ButtonState) {
        buttonState = state
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        textPositionYPosition = (height.toFloat() + FONT_SIZE) / 2.0f
    }

}