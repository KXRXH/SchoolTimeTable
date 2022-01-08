package com.kxrxh.schooltime

import com.kxrxh.schooltime.model.LessonClass
import kotlin.system.exitProcess

class DataSource {
    fun loadTimeTable(day: String): List<LessonClass> {
        var table: List<LessonClass> = listOf(LessonClass(1, "00:00", "00:00"))
        when (day) {
            "Monday" -> table = listOf(
                LessonClass(R.string.Math, "8:00", "8:40"),
                LessonClass(R.string.Math, "8:50", "9:30"),
                LessonClass(R.string.Russian, "9:40", "10:20"),
                LessonClass(R.string.History, "10:40", "11:20"),
                LessonClass(R.string.History, "11:40", "12:20"),
                LessonClass(R.string.HourOfClass, "12:40", "12:55"),
                LessonClass(R.string.English, "13:00", "13:40")
            )
            "Tuesday" -> table = listOf(
                LessonClass(R.string.Biology, "8:00", "8:40"),
                LessonClass(R.string.Math, "8:50", "9:30"),
                LessonClass(R.string.Math, "9:40", "10:20"),
                LessonClass(R.string.Physic, "10:40", "11:20"),
                LessonClass(R.string.English, "11:40", "12:20"),
                LessonClass(R.string.Literature, "12:40", "13:20"),
                LessonClass(R.string.Literature, "13:30", "14:10")
            )
            "Wednesday" -> table = listOf(
                LessonClass(R.string.PE, "8:00", "8:40"),
                LessonClass(R.string.Geography, "8:50", "9:30"),
                LessonClass(R.string.Physic, "9:40", "10:20"),
                LessonClass(R.string.MyRussian, "10:40", "11:20"),
                LessonClass(R.string.ElPhysic, "11:40", "12:20"),
                LessonClass(R.string.ElRussian, "12:40", "13:20")
            )
            "Thursday" -> table = listOf(
                LessonClass(R.string.English, "8:50", "9:30"),
                LessonClass(R.string.SS, "9:40", "10:20"),
                LessonClass(R.string.SS, "10:40", "11:20"),
                LessonClass(R.string.CS, "11:40", "12:20"),
                LessonClass(R.string.CS, "12:40", "13:20"),
                LessonClass(R.string.PE, "13:30", "14:10"),
                LessonClass(R.string.PE, "14:20", "15:00")
            )
            "Friday" -> table = listOf(
                LessonClass(R.string.OBJ, "8:00", "8:40"),
                LessonClass(R.string.Math, "8:50", "9:30"),
                LessonClass(R.string.Math, "9:40", "10:20"),
                LessonClass(R.string.Chemistry, "10:40", "11:20"),
                LessonClass(R.string.ElPhysic, "11:40", "12:20"),
                LessonClass(R.string.Astronomy, "12:40", "13:20")
            )
            "Saturday" -> table = listOf(
                LessonClass(R.string.ElMath, "8:00", "8:40"),
                LessonClass(R.string.ElMath, "8:50", "9:30"),
                LessonClass(R.string.CS, "9:40", "10:20"),
                LessonClass(R.string.CS, "10:40", "11:20"),
                LessonClass(R.string.Literature, "11:40", "12:20"),
                LessonClass(R.string.ElRussian, "12:40", "13:20")
            )
            "Sunday" -> exitProcess(1)
        }
        return table
    }

}