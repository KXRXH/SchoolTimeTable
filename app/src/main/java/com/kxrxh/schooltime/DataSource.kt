package com.kxrxh.schooltime

import com.kxrxh.schooltime.R.string
import com.kxrxh.schooltime.model.LessonClass
import kotlin.system.exitProcess

class DataSource {
    fun loadTimeTable(day: String): List<LessonClass> {
        val table: List<LessonClass>
        when (day) {
            "Monday" -> table = listOf(
                LessonClass(string.Math, "8:00", "8:40"),
                LessonClass(string.Math, "8:50", "9:30"),
                LessonClass(string.Russian, "9:40", "10:20"),
                LessonClass(string.History, "10:40", "11:20"),
                LessonClass(string.History, "11:40", "12:20"),
                LessonClass(string.HourOfClass, "12:40", "12:55"),
                LessonClass(string.English, "13:00", "13:40")
            )
            "Tuesday" -> table = listOf(
                LessonClass(string.Biology, "8:00", "8:40"),
                LessonClass(string.Math, "8:50", "9:30"),
                LessonClass(string.Math, "9:40", "10:20"),
                LessonClass(string.Physic, "10:40", "11:20"),
                LessonClass(string.English, "11:40", "12:20"),
                LessonClass(string.Literature, "12:40", "13:20"),
                LessonClass(string.Literature, "13:30", "14:10")
            )
            "Wednesday" -> table = listOf(
                LessonClass(string.PE, "8:00", "8:40"),
                LessonClass(string.Geography, "8:50", "9:30"),
                LessonClass(string.Physic, "9:40", "10:20"),
                LessonClass(string.MyRussian, "10:40", "11:20"),
                LessonClass(string.ElPhysic, "11:40", "12:20"),
                LessonClass(string.ElRussian, "12:40", "13:20")
            )
            "Thursday" -> table = listOf(
                LessonClass(string.English, "8:50", "9:30"),
                LessonClass(string.SS, "9:40", "10:20"),
                LessonClass(string.SS, "10:40", "11:20"),
                LessonClass(string.CS, "11:40", "12:20"),
                LessonClass(string.CS, "12:40", "13:20"),
                LessonClass(string.PE, "13:30", "14:10"),
                LessonClass(string.PE, "14:20", "15:00")
            )
            "Friday" -> table = listOf(
                LessonClass(string.OBJ, "8:00", "8:40"),
                LessonClass(string.Math, "8:50", "9:30"),
                LessonClass(string.Math, "9:40", "10:20"),
                LessonClass(string.Chemistry, "10:40", "11:20"),
                LessonClass(string.ElPhysic, "11:40", "12:20"),
                LessonClass(string.Astronomy, "12:40", "13:20")
            )
            "Saturday" -> table = listOf(
                LessonClass(string.ElMath, "8:00", "8:40"),
                LessonClass(string.ElMath, "8:50", "9:30"),
                LessonClass(string.CS, "9:40", "10:20"),
                LessonClass(string.CS, "10:40", "11:20"),
                LessonClass(string.Literature, "11:40", "12:20"),
                LessonClass(string.ElRussian, "12:40", "13:20")
            )
            else -> exitProcess(0)
        }
        return table
    }

}