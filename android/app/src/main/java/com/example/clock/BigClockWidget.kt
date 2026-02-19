package com.example.clock

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class BigClockWidget : AppWidgetProvider() {

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        startClock(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        stopClock()
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        updateWidget(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        updateWidget(context)
    }

    private fun startClock(context: Context) {
        runnable = object : Runnable {
            override fun run() {
                updateWidget(context)
                handler.postDelayed(this, 60_000)
            }
        }
        handler.post(runnable!!)
    }

    private fun stopClock() {
        runnable?.let { handler.removeCallbacks(it) }
    }

    private fun updateWidget(context: Context) {
        val views = RemoteViews(context.packageName, R.layout.widget_clock)

        val time = SimpleDateFormat("HHmm", Locale.getDefault()).format(Date())
        val top = time.substring(0, 2)
        val bottom = time.substring(2, 4)

        views.setTextViewText(R.id.timeTop, top)
        views.setTextViewText(R.id.timeBottom, bottom)

        val manager = AppWidgetManager.getInstance(context)
        val component = ComponentName(context, BigClockWidget::class.java)
        manager.updateAppWidget(component, views)
    }
}
