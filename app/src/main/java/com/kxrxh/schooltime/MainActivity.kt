package com.kxrxh.schooltime

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.kxrxh.schooltime.adapter.ItemAdapter
import com.kxrxh.schooltime.model.LessonClass
import com.kxrxh.schooltime.model.Time
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private val mapOfTranslation = mapOf(
        "Monday" to "Понедельник",
        "Tuesday" to "Вторник",
        "Wednesday" to "Среда",
        "Thursday" to "Четверг",
        "Friday" to "Пятница",
        "Saturday" to "Суббота",
        "Sunday" to "Воскересенье"
    )
    private lateinit var recyclerView: RecyclerView
    private lateinit var currDayViewer: TextView
    private lateinit var currLessonView: TextView
    private lateinit var curActView: TextView
    private lateinit var timerView: TextView
    private lateinit var countView: TextView
    private var currentDay = ""
    private val dataSource: DataSource = DataSource()
    private var myDataset = dataSource.loadTimeTable(getCurrentDay())


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide() // Disable Top Action Bar
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) // Only night mode!
        delegate.applyDayNight()

        // Declaration of main_activity objects
        currDayViewer = findViewById(R.id.curDayView)
        recyclerView = findViewById(R.id.recycler_view)
        currLessonView = findViewById(R.id.curObj)
        curActView = findViewById(R.id.textView)
        timerView = findViewById(R.id.timeLeft)
        countView = findViewById(R.id.lessonsCount)

        val ctx = this

        val thread: Thread = object : Thread() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        runOnUiThread {
                            if (currentDay != getCurrentDay()) {
                                currentDay = getCurrentDay()
                                myDataset = dataSource.loadTimeTable(getCurrentDay())
                                updateCards(ctx, myDataset)
                            }
                            countView.text = "Сегодня ${myDataset.size} уроков"
                            currDayViewer.text = mapOfTranslation[getCurrentDay()]
                            if (!getCurrentLesson(myDataset)) {
                                currLessonView.text = "Что-то пошло не так :("
                                timerView.text = ""
                                curActView.text = "Упс!"
                            }
                        }
                        sleep(1000)
                    }
                } catch (e: InterruptedException) {
                    exitProcess(1)
                }
            }
        }

        thread.start()

    }

    private fun getCurrentDay(): String {
        val day = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis())
        } else {
            "Monday"
        }
        return day
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCurrentLesson(dataset: List<LessonClass>): Boolean {
        val calendar = Calendar.getInstance()
        val sdf: DateFormat = SimpleDateFormat("HH:mm")

        var date: Date = sdf.parse(dataset[dataset.size - 1].end)
        var lastLessonStart = Time(date.hours, date.minutes)

        date = sdf.parse(dataset[0].start)
        val firstLessonStart = Time(date.hours, date.minutes)

        var diff = getDiffOfTimes(
            Time(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
            lastLessonStart
        )
        val diffF = getDiffOfTimes(
            Time(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
            firstLessonStart
        )

        when {
            diff >= 0 -> {
                curActView.text = "Уроки закончились"
                currLessonView.text = "Теперь только за шаурмой"
                timerView.text = ""
                return true
            }
            diffF < 0 -> {
                curActView.text = "Уроки ещё не начались"
                currLessonView.text = "Первый урок в ${dataset[0].start}"
                timerView.text = "До урока\n${kotlin.math.abs(diffF)} мин."
                return true
            }
            else -> {
                for (i in dataset.indices) {
                    date = sdf.parse(dataset[i].start)
                    lastLessonStart = Time(date.hours, date.minutes)

                    diff = getDiffOfTimes(
                        Time(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
                        lastLessonStart
                    )
                    /* Checking is the lesson a @class-hour@ */
                    if (getCurrentDay() == "Monday" && i == 5) {
                        if (diff in 0..14) {
                            date = sdf.parse(dataset[i].end)
                            lastLessonStart = Time(date.hours, date.minutes)

                            diff = getDiffOfTimes(
                                Time(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
                                lastLessonStart
                            )
                            curActView.text = "Сейчас идёт ${i + 1} урок"
                            currLessonView.text = resources.getString(dataset[i].lessonId)
                            timerView.text = "Оставшееся время\n${kotlin.math.abs(diff)} мин."
                            return true
                        }
                    } else if (diff in 0..39) {
                        date = sdf.parse(dataset[i].end)
                        lastLessonStart = Time(date.hours, date.minutes)

                        diff = getDiffOfTimes(
                            Time(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
                            lastLessonStart
                        )
                        curActView.text = "Сейчас идёт ${i + 1} урок"
                        currLessonView.text = resources.getString(dataset[i].lessonId)
                        timerView.text = "Оставшееся время\n${kotlin.math.abs(diff)} мин."
                        return true
                    }
                }
            }
        }
        /* Checking last time of @brake@ */
        for (i in dataset.indices) {
            date = sdf.parse(dataset[i].end)
            val prevLessonStart = Time(date.hours, date.minutes)

            date = sdf.parse(dataset[i + 1].start)
            val nextLessonStart = Time(date.hours, date.minutes)

            val diffPrev = getDiffOfTimes(
                Time(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
                prevLessonStart
            )
            val diffNext = getDiffOfTimes(
                Time(calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE]),
                nextLessonStart
            )
            if (diffPrev >= 0 && diffNext <= 0) {
                curActView.text = "Сейчас идёт"
                currLessonView.text = "Перемена"
                timerView.text = "Оставшееся время\n${kotlin.math.abs(diffNext)} мин."
                return true
            }
        }
        return false
    }

    private fun getDiffOfTimes(start: Time, stop: Time): Int {
        return (start.minutes - stop.minutes) + (start.hours * 60 - stop.hours * 60)
    }

    private fun updateCards(context: Context, dataset: List<LessonClass>) {
        recyclerView.adapter = ItemAdapter(context, dataset)
    }
}
