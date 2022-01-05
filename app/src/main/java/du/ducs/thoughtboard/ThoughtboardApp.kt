package du.ducs.thoughtboard

import android.app.Application
import du.ducs.thoughtboard.data.AppDatabase

class ThoughtboardApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}