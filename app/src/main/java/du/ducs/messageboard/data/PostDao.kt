package du.ducs.messageboard.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: Post) : Long

    @Update
    suspend fun updatePost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("SELECT * FROM posts ORDER BY creation_date ASC")
    fun getPosts() : Flow<List<Post>>

    @Query("SELECT * FROM posts WHERE creation_date = :date")
    fun getPostsByDate(date: Date) : Flow<List<Post>>

    @Query("SELECT * FROM posts WHERE email = :email ORDER BY creation_date ASC")
    fun getPostsByEmail(email: String) : Flow<List<Post>>
}