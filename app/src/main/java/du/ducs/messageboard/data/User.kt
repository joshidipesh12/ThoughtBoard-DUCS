package du.ducs.messageboard.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @NonNull
    val name: String,

    @PrimaryKey
    val email: String
)
