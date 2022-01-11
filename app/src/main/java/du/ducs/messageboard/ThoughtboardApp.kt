package du.ducs.messageboard

import android.app.Application
import du.ducs.messageboard.data.AppDatabase

class ThoughtboardApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}