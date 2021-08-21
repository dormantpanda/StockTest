package com.example.stocktest.app.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.stocktest.R
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min


class Chart : View {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var points = listOf<Float>()

    private val paintChart = Paint()
    private val paintGrid = Paint()
    private val paintText = Paint()
    private val pathChart = Path()
    private val pathGrid = Path()

    private var maxValue = 0f
    private var minValue = 0f
    private var topGridLine = 0
    private var bottomGridLine = 0

    fun setPoints(points: List<Float>) {
        this.points = points
        maxValue = points.maxOrNull() ?: 1f
        topGridLine = ceil(this.maxValue).toInt()
        minValue = points.minOrNull() ?: 0f
        bottomGridLine = floor(this.minValue).toInt()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        pathChart.moveTo(getXPos(0f), getYPos(points[0]))
        paintsConfig()

        val labelMargin = resources.getDimensionPixelSize(R.dimen.margin_8)
        //draw grid
        for (i in bottomGridLine..topGridLine) {
            canvas.drawText(
                i.toString(),
                labelMargin.toFloat(),
                getYPos(i.toFloat()) - labelMargin.toFloat(),
                paintText
            )
            pathGrid.moveTo(getXPos(0f), getYPos(i.toFloat()))
            pathGrid.lineTo(getXPos(points.size.toFloat()), getYPos(i.toFloat()))
        }

        canvas.drawPath(pathGrid, paintGrid)

        //draw chart
        points.forEachIndexed { ind, yValue ->
            val thisPointX = getXPos(ind.toFloat())
            val thisPointY = getYPos(yValue)
            val nextPointX = getXPos(ind.toFloat() + 1)
            val nextPointY = getYPos(points[min(ind + 1, points.lastIndex)])

            val startdiffX = nextPointX - getXPos(max(ind - 1, 0).toFloat())
            val startdiffY = nextPointY - getYPos(points[max(ind - 1, 0)])
            val endDiffX = getXPos(min(ind + 2, points.lastIndex).toFloat()) - thisPointX
            val endDiffY = getYPos(points[min(ind + 2, points.lastIndex)]) - thisPointY

            val firstControlX: Float = thisPointX + GRAPH_SMOOTHNES * startdiffX
            val firstControlY: Float = thisPointY + GRAPH_SMOOTHNES * startdiffY
            val secondControlX: Float = nextPointX - GRAPH_SMOOTHNES * endDiffX
            val secondControlY: Float = nextPointY - GRAPH_SMOOTHNES * endDiffY

            pathChart.cubicTo(
                firstControlX, firstControlY, secondControlX, secondControlY, nextPointX,
                nextPointY
            )
        }

        canvas.drawPath(pathChart, paintChart)
    }

    fun paintsConfig() {
        paintChart.style = Paint.Style.STROKE
        paintChart.color = ResourcesCompat.getColor(resources, R.color.black, context.theme)
        paintChart.strokeWidth = 8f
        paintChart.isAntiAlias = true

        paintGrid.style = Paint.Style.STROKE
        paintGrid.color = ResourcesCompat.getColor(resources, R.color.light_grey, context.theme)
        paintGrid.strokeWidth = 2f
        paintGrid.isAntiAlias = true

        paintText.style = Paint.Style.STROKE
        paintText.color = ResourcesCompat.getColor(resources, R.color.black, context.theme)
        paintText.strokeWidth = 2f
        paintText.isAntiAlias = true
        paintText.textSize = 32f

    }

    //Scaling and inversion
    private fun getYPos(value: Float): Float {
        var newValue = value
        val height = (height - paddingTop - paddingBottom)

        newValue = (newValue - bottomGridLine) / (topGridLine - bottomGridLine) * height
        newValue = height - newValue

        newValue += paddingTop
        return newValue
    }

    private fun getXPos(value: Float): Float {
        var newValue = value
        val width = (width - paddingLeft - paddingRight)
        val maxValue = points.size - 1

        newValue = newValue / maxValue * width
        newValue += paddingLeft

        return newValue
    }

    companion object {
        const val GRAPH_SMOOTHNES = 0.2f
    }
}