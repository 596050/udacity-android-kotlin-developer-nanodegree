package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates


const val FONT_SIZE = 70.0f

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private var backColor = 0
    private var loadingColor = 0
    private var radius = 0.0f  // Radius of the inner circle
    private var textYPosition = 0f
    private var textStr = ""  // text to be displayed
    private var downloadPercentage = 0.0f  // how much was downloaded
    private var alexOvalPercentageCoords =
        RectF()  // how much was downloaded showed by a semicircle

    private val valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(
        ButtonState.Completed
    ) { p, old, new ->

        when (new) {
            ButtonState.Clicked -> {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show()
            }
            ButtonState.Loading -> {
                textStr = "Loading"
                valueAnimator.start()
                isClickable = false
            }
            ButtonState.Completed -> {
                textStr = "Download"
                downloadPercentage = 0.0f
                invalidate()
                isClickable = true

//                val factory: LayoutInflater = context.applicationContext.
//
//                val textEntryView: View = factory.inflate(R.layout.landmark_new_dialog, null)
//
//                landmarkEditNameView =
//                    textEntryView.findViewById<View>(R.id.landmark_name_dialog_edit) as EditText
//
//                findViewById<FrameLayout>(R.id.loading_button).findViewById<CircularProgressView>(R.id.progress_circle).setParentColor(Color.TRANSPARENT)
            }
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = FONT_SIZE
        typeface = Typeface.create("", Typeface.NORMAL)
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            backColor = getColor(R.styleable.LoadingButton_backColor, 0)
            loadingColor = getColor(R.styleable.LoadingButton_loadingColor, 0)
        }

        textStr = "Download"

        valueAnimator.duration = 4500
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.setFloatValues(0.0f, widthSize.toFloat())
        valueAnimator.addUpdateListener {
            if (ButtonState.Loading == buttonState) {
                downloadPercentage = it.animatedFraction as Float
                invalidate()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = height.toFloat() * 0.25f
//        alexOvalPercentageCoords.left = width.toFloat() * 0.75f
//        alexOvalPercentageCoords.top = height.toFloat() * 0.25f
//        alexOvalPercentageCoords.right = alexOvalPercentageCoords.left + (2 * radius)
//        alexOvalPercentageCoords.bottom = alexOvalPercentageCoords.top + (2 * radius)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = backColor
        canvas?.drawRect(0.0f, 0.0f, widthSize.toFloat(), heightSize.toFloat(), paint)

        paint.color = loadingColor
        canvas?.drawRect(
            0.0f, 0.0f, widthSize.toFloat() * downloadPercentage,
            heightSize.toFloat(), paint
        )

        paint.color = Color.WHITE
        if (canvas != null) {
            textYPosition = ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2))
        };
        canvas?.drawText(textStr, width.toFloat() / 2.0f, textYPosition, paint)
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
}

