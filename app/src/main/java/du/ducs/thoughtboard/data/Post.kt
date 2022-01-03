package du.ducs.thoughtboard.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "posts", foreignKeys = [ ForeignKey(
    entity = User::class,
    parentColumns = [ "email" ],
    childColumns = [ "email" ],
    onDelete = ForeignKey.CASCADE
) ])
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
