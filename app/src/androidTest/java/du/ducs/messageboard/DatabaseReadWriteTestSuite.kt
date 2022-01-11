package du.ducs.messageboard

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import du.ducs.messageboard.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class DatabaseReadWriteTestSuite {
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var postDao: PostDao

    @Before
    fun createInMemoryDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        userDao = database.userDao()
        postDao = database.postDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun testUserInsertion() {
        val user = User("ABC", "abc@cs.du.ac.in")
        runBlocking { userDao.insertUser(user) }

        val users = runBlocking {
            userDao.getUsers().first()
        }

        assertFalse(users.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun testUserDeletion() {
        val user = User("ABC", "abc@cs.du.ac.in")
        runBlocking { userDao.insertUser(user) }

        runBlocking { userDao.deleteUser(user) }

        val users = runBlocking {
            userDao.getUsers().first()
        }

        assertTrue(users.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun testUserRetrievalByEmail() {
        val users = listOf(
            User("ABC", "abc@cs.du.ac.in"),
            User("DEF", "def@cs.du.ac.in"),
            User("ABC", "abc2@cs.du.ac.in")
        )
        users.forEach {
            runBlocking { userDao.insertUser(it) }
        }

        users.forEach {
            val user = runBlocking {
                userDao.getUserByEmail(it.email).first()
            }
            assertEquals(it, user)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testUserModification() {
        val user = User("ABC", "abc@cs.du.ac.in")
        runBlocking { userDao.insertUser(user) }

        val user2 = user.copy(name = "ABC2")
        runBlocking { userDao.updateUser(user2) }

        val userFromDb = runBlocking {
            userDao.getUserByEmail(user.email).first()
        }

        assertEquals(user2, userFromDb)
    }

    @Test
    @Throws(Exception::class)
    fun testPostCreation() {
        val user = User("ABC", "abc@cs.du.ac.in")
        runBlocking { userDao.insertUser(user) }

        val post = Post(
            0, user.email, Date(),
            "Post1", "Message1"
        )
        runBlocking { postDao.insertPost(post) }

        val users = runBlocking {
            postDao.getPosts().first()
        }

        assertFalse(users.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun testPostDeletion() {
        val user = User("ABC", "abc@cs.du.ac.in")
        runBlocking { userDao.insertUser(user) }

        val post = Post(
            0, user.email, Date(),
            "Post1", "Message1"
        )
        val insertedId = runBlocking { postDao.insertPost(post) }
        val postRef = post.copy(id = insertedId.toInt())

        runBlocking { postDao.deletePost(postRef) }

        val users = runBlocking {
            postDao.getPosts().first()
        }

        assertTrue(users.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun testPostRetrievalByUser() {
        val users = listOf(
            User("ABC", "abc@cs.du.ac.in"),
            User("DEF", "def@cs.du.ac.in"),
            User("ABC", "abc2@cs.du.ac.in")
        )

        val postData = (1..3).map { listOf("Post$it", "Message$it") }

        users.forEach { user ->
            runBlocking { userDao.insertUser(user) }
            postData.forEach {
                val post = Post(0, user.email, Date(), it[0], it[1])
                runBlocking { postDao.insertPost(post) }
            }
        }

        val allPosts = runBlocking { postDao.getPosts().first() }
        assertEquals(allPosts.size, postData.size * users.size)

        users.forEach {
            val posts = runBlocking {
                postDao.getPostsByEmail(it.email).first()
            }
            assertEquals(postData.size, posts.size)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testPostRetrievalByDate() {
        val users = listOf(
            User("ABC", "abc@cs.du.ac.in"),
            User("DEF", "def@cs.du.ac.in"),
        )

        val formatter = SimpleDateFormat("dd/MM/yyyy")

        val postData = (1..3).map {
            object {
                val title = "Post$it"
                val message = "Message$it"
                val created = formatter.parse("${it+10}/01/2022")!!
            }
        }

        users.forEach { user ->
            runBlocking { userDao.insertUser(user) }
            postData.forEach {
                val post = Post(0, user.email, it.created, it.title, it.message)
                runBlocking { postDao.insertPost(post) }
            }
        }

        val allPosts = runBlocking { postDao.getPosts().first() }
        assertEquals(allPosts.size, postData.size * users.size)

        postData.forEach {
            val posts = runBlocking {
                postDao.getPostsByDate(it.created).first()
            }
            assertEquals(users.size, posts.size)
        }
    }
}