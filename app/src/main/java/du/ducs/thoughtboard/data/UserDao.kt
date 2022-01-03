package du.ducs.thoughtboard.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUser(email: String): Flow<User>

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
}