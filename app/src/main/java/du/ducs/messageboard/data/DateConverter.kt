package du.ducs.messageboard.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun makeDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun makeTimestamp(date: Date?): Long? {
        return date?.time
    }
}