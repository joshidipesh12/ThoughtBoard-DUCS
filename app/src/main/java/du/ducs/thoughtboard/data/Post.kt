package du.ducs.thoughtboard.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "email")
    val userEmail: String,

    @ColumnInfo(name = "creation_date")
    val dateCreated: Date,

    val title: String,

    val message: String,
)
