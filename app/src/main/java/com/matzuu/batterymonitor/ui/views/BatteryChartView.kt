package com.matzuu.batterymonitor.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.matzuu.batterymonitor.ui.theme.BatteryMonitorTheme


private const val TAG = "BatteryChartView"


class BatteryChartView(
    context: Context,
    private val dataPoints: List<PointF>
) : View(context) {

    private val curvePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
    }
    private val dataPointPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 64f
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawFrame(canvas)

        drawCurve(canvas)
    }

    private fun drawFrame(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()

        // Draw x-axis
//        canvas.drawLine(0f, height / 2, width, height / 2, axesPaint)

        // Draw y-axis
//        canvas.drawLine(width / 2, 0f, width / 2, height, axesPaint)

        canvas.apply {
            // horizontal lines
            drawLine(0f, height, width, height, curvePaint)
            drawLine(0f, 0f, width, 0f, curvePaint)

            // vertical lines
            drawLine(0f, 0f, 0f, height, curvePaint)
            drawLine(width, 0f, width, height, curvePaint)
        }
    }

    private fun drawCurve(canvas: Canvas) {

        var prevPoint: PointF? = null
        for (point in dataPoints) {
            val x = (point.x * width)         // Scale to view width
            val y = ((1-point.y) * height)        // Scale to view height
            canvas.drawCircle(x, y, 10f, dataPointPaint)
            if (prevPoint == null) {
                prevPoint = PointF(x, y)
                continue
            }
            canvas.drawLine(prevPoint.x, prevPoint.y, x, y, curvePaint)

            prevPoint = PointF(x, y)

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BatteryChartViewPreview() {
    BatteryMonitorTheme {
        val points = listOf(
            PointF(0.01f, 0.02f),
            PointF(0.03f, 0.04f),
            PointF(0.05f, 0.06f),
            PointF(1f, 0.5f)
        )
        AndroidView(
            factory = { context ->
                BatteryChartView(context, points)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}